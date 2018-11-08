package app;

import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Use static runtime analysis of all classes of the project to create a .ucd file representationof the Java project.
 * The main entry point for this operation in {@link JavaAnalyzer#toUcdFile()}. For now, it only support class
 * definition but in the future could be use to generate GENERELIZATION, AGGREGATION and ASSOCIATION.
 */
public final class JavaAnalyzer {

    /**
     * Private constructor that throws an error to prevent class instanciation. This class only provdes package
     * private access to static operations.
     */
    private JavaAnalyzer() { throw new UnsupportedOperationException("Cannot instanciate static class JavaAnalyzer"); }


    /**
     * Wheter the app should create of not the ucd file at launch.
     * WARNING ! IF THIS IS SET TO TRUE, it will use {@link JavaAnalyzer#basic_file_path} as the root
     * of the project and starts analyzing all java files within the directory and subdirectories. As such,
     * it needs to be changed manually in the code to reflect the starting point for the analysis.
     *
     * WOULD NOT WORK IN RELEASE ! ONLY USE DURING DEVELOPPEMENT !
     *
     */
    static final boolean CREATE_UCD_FILE_AT_LAUNCH = false;


    /**
     * Path of the file to analyze, needs to be changed depending on the computer of the user.
     */
    // todo : test if automatic path generation based on the user system works on every computer (it works on Oli's mac)
    private static final String basic_file_path = "file:" + Main.USER_PATH + "/src/";


    /**
     * Immutable list of all class file paths of the project
     */
    public static final List<Path> ALL_PATHS;

    static {
        try {
            List<Path> tmp_list = Files.walk(Paths.get("")).collect(Collectors.toList());
            ALL_PATHS = Collections.unmodifiableList(tmp_list);
        } catch (IOException ioe){
            throw new RuntimeException("Could not load filepaths");
        }
    }

    private static final String class_model = "\n\nCLASS %s\nATTRIBUTES\n%sOPERATIONS\n%s;\n";
    private static final String attr_model = "%s : %s";
    private static final String method_model = "%s(%s) : %s";


    /**
     * Creates a String that represent all classes of the current javaproject in a String representation
     * that respect the grammar definition.<p></p>
     * This method is the main entry point for operating on the path tree and creating the classes as String.
     * If the path provided by {@link JavaAnalyzer#basic_file_path} is wrong, no file will be created. It uses
     * {@link System#getProperty(String)} with "user.dir" as an argument to try and find all directory and java file.
     * <p>
     * Also, the method assumes that all files are contained within a "src" folder.
     * </p>
     * @return String of all classes
     * @throws IOException if something unexpected happens or if file does not exist
     */
    static String toUcdFile() throws IOException  {
        Stream<Path> p = Files.walk(Paths.get(""));
        List<Path> allFiles = p
                .filter(path -> path.toFile().getName().endsWith(".java") && !path.toString().contains("test"))
                .collect(Collectors.toList());
        
        // get all Class<?> object from Java reflection package
        List<Class<?>> allClass = allFiles
            .stream()
            .map(path -> {
                try {
                    // change path to package name (ie src/app/Main.java to src.app.Main)
                    String classUrl = path.toUri().toURL().toString();
                    String className = classUrl.substring(basic_file_path.length())
                            .replaceAll("/", ".").replaceAll(".java", "");
                    if (!(classUrl.contains("/test/") || classUrl.contains("package-info"))) {
                        return Class.forName(className);
                    }
                } catch (Exception e) {
                    // e.printStackTrace();
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        StringBuilder ucdContent = new StringBuilder();
        allClass.forEach(clazz -> {
            String className = removePackageName(clazz.getName());
            String attr = createAttributes(clazz.getDeclaredFields());
            String methods = createMethods(clazz.getDeclaredMethods());
            ucdContent.append(defineUcdClass(className, attr, methods));
        });

        return ucdContent.toString();
    }


    /**
     * Used to create a .ucd complient String representation of some {@link Method} from the Java reflection
     * package
     * @param declaredMethods the moethods to be converted
     * @return the String representation for a .ucd file
     */
    private static String createMethods(Method[] declaredMethods) {
        StringBuilder sb = new StringBuilder();
        for (Method m : declaredMethods){

            String methodName = m.getName();

            StringBuilder params = new StringBuilder();
            for (Parameter p : m.getParameters()){
                String paramType = p.getParameterizedType().getTypeName();
                String sParamsTmp = String.format(attr_model, p.getName(), removePackageName(paramType));
                params.append(sParamsTmp).append(", ");
            }
            String formatedParams;
            if (params.length() != 0){
                formatedParams = params.substring(0, params.length() - 2);
            } else {
                formatedParams = params.toString();
            }
            String returnType = removePackageName(m.getGenericReturnType().getTypeName());

            if (!methodName.contains("lambda$")) {
                sb.append("  ").append(String.format(method_model,
                        methodName,
                        formatedParams.replaceAll("\\$", "."),
                        returnType.replaceAll("\\$", "."))).append(",\n");
            }
        }

        // remove last useless return
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) + "\n" : sb.toString();
    }


    /**
     * Used to combine info about a class into a String representation of a ucd class
     * @param className The name (id) of the class
     * @param attributes all its attributes. Must already be ucd complient
     * @param operations all its operations. Must be ucd complient
     * @return A string representing a ucd complient class
     */
    private static String defineUcdClass(String className, String attributes, String operations){
        return String.format(class_model,
                className.replaceAll(";", ""),
                attributes.replaceAll(";", ""),
                operations.replaceAll(";", ""));
    }


    /**
     * Use to create a String representation of the attributes of a class for a ucd file
     * based on some fields object from the java.reflection package
     * @param fields the feilds to be converted to ucd attributes
     * @return the string that represents a ucd complient list of attribute for a class
     */
    private static String createAttributes(Field[] fields){
        StringBuilder sb = new StringBuilder();
        for (Field f : fields){
            String fieldType = f.getGenericType().getTypeName();
            String formatedType = removePackageName(fieldType);
            sb.append("  ").append(String.format(attr_model, f.getName(), formatedType.replaceAll("\\$", "."))).append(",\n");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) + "\n" : sb.toString();
    }

    /**
     * This method removes the package name of a type definition and leave only the type (class) assoicated to it.
     * Ex : <pre>parsing.syntaxTree.expressions.Aggregation</pre> wil become just <pre>Aggregation</pre>.
     * Some thought is made to keep thing coherent with generic type definition. For example :
     * <pre>java.utils.List<parsing.syntaxTree.entries.RoleEntry></pre> wil be adequatly converted to : List<RoleEntry>
     * @param classPath the class path containing the package definition and the class, ie java.utils.List
     * @return the correct type without the package name as a String
     */
    public static String removePackageName(String classPath){

        // does the type definition contain generic ?
        Matcher match = Pattern.compile("<(.*?)>").matcher(classPath);

        if (!match.find()) {
            return lastDot(classPath);
        } else {

            // if it contains genereic, we need to deal with it and remove full path from both
            // the type part, and the genric type part independently
            String beforeBracket = classPath.substring(0, classPath.indexOf("<"));
            beforeBracket = lastDot(beforeBracket);

            String within = match.group();

            if (within.contains(",")){
                // we have multiple arguments within the brackets
                String[] args = within.split(",");
                StringBuilder combinedArg = new StringBuilder("<");
                for (String arg : args){
                    arg = arg.replaceAll("\\$", ".");
                    arg = lastDot(arg);
                    combinedArg.append(arg).append(", ");
                }
                within = combinedArg.substring(0, combinedArg.length() - 2);

            } else {
                within = lastDot(within);
            }

            if (!within.startsWith("<")) within = "<" + within;
            within = within.replaceAll("\\$", ".");

            // Utils.Log.test(beforeBracket + within);
            return beforeBracket + within;

        }
    }


    /**
     * removes everything before the last dot of a string.<p></p> Ex : java.utils.List => List
      * @param str the string on which to operate
     * @return the resulting string
     */
    private static String lastDot(String str){
        if (!str.contains(".")) return str;
        return str.substring(str.lastIndexOf(".")+1);
    }
}
