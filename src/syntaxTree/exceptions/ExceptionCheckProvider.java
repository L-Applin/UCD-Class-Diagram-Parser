package syntaxTree.exceptions;

import parsing.Delims;
import parsing.GrammarModel;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides default methods for checking different types of possible error while parsing the ucd file.
 */
public interface ExceptionCheckProvider {

    /**
     * Verify that a specified tag is actually at the beginning of the text.
     * @param txt
     * @param delim
     * @throws IncompatibleTagException when the actual tag and the expected value does not match.
     */
    default void checkTagEqual(String txt, String delim){

        String[] splits = txt.split(" ", 2);

        if (!splits[0].equals(delim)){
            throw new IncompatibleTagException(splits[0], delim, splits[1]);
        }
    }


    /**
     * Makes sure that class declaration contains OPERATION and ATTRIBUTES
     * @param classContent
     */
    default void checkClassContent(String clazz, String classContent){

        if (!classContent.contains(GrammarModel.ClassContent.OPERATIONS)){
            throw new MissingClassTagException(clazz, classContent, GrammarModel.ClassContent.OPERATIONS);
        }

        if (!classContent.contains(GrammarModel.ClassContent.ATTRIBUTES)){
            throw new MissingClassTagException(clazz, classContent, GrammarModel.ClassContent.ATTRIBUTES);
        }

    }

    /**
     * Makes sure the text does not contains illegal characters
     * @param txt the text to verify
     */
    default void checkIllegalChar(String txt){
        for (String illegal : GrammarModel.illegalChar){
            if (txt.contains(illegal)){
                throw new IllegalCharacterException(txt, illegal);
            }
        }
    }

    default void checkIllegalChar(String txt, String filePath){
        for (String illegal : GrammarModel.illegalChar){
            if (txt.contains(illegal)){
                throw new IllegalCharacterException(txt, illegal, filePath);
            }
        }
    }


    default void checkTagPresent(String txt, String tag, String classId, String content) {
        if (!txt.contains(tag)) {
            throw new MissingClassTagException(classId, content, GrammarModel.ClassContent.OPERATIONS);
        }
    }

    default void checkNoDuplicateTag(String txt, String tag, String classId, String content){
        // check there is only one <attributes> tag
        if (txt.indexOf(tag) != txt.lastIndexOf(tag)){
            MalformedClassException mce = new MalformedClassException("Class \'" + classId + "\' cannot contain two sets of attributes.");
            mce.setClazz(classId);
            mce.setContent(content);
            throw mce;
        }

    }


    default void checkValidOperation(String txt, String classId){
        // Check only if has method parameter.
        // Param validation is done in checkValidDataItem later on during parsing
        String paramsRegEx = "\\((.*?)\\)|\\(\\)";
        Matcher matcher = Pattern.compile(paramsRegEx).matcher(txt);
        if (!matcher.find()){
            throw new MalformedOperationException("Method \'" + txt + "\' must have a parameter declaration.", classId, txt);
        }

        //checks for method return type
        if (!txt.contains("):")){    // ok because spaces were remove
            throw new MalformedOperationException("Method \'" + txt + "\' must have a return type.", classId, txt);
        }

        String validationType = txt.substring(txt.lastIndexOf("):") +2, txt.length());
        checkValidType(validationType, txt);

    }


    default void checkValidDataItem(String txt, String parentId){
        // must contains at least one ':'
        if (!txt.contains(Delims.TYPE_SEPARATOR)){
            throw new TypeNotFoundException(txt, parentId);
        }

        // must not contains more than 1 ':'
        if (txt.lastIndexOf(Delims.TYPE_SEPARATOR) != txt.indexOf(Delims.TYPE_SEPARATOR)){
            throw new MalformedTypeException(txt, parentId);
        }

    }


    default void checkValidRole(String txt, String association){

        if (txt.indexOf(GrammarModel.ROLES_TAG) != 0){
            throw new MalformedDeclarationException("Malformed 'ROLES' tag in '" + association + "'");
        }

        String roles = txt.split(Delims.NEW_LINE_TOKEN)[1];

        // only one LIST_SEPERATOR => exaclty two roles
        if (roles.indexOf(Delims.LIST_SEPERATOR) != roles.lastIndexOf(Delims.LIST_SEPERATOR)){
            throw new MalformedDeclarationException("'"+association+"' association must contain exactly two roles");
        }

    }



    default void checkValidType(String content, String parentId) {
        if (Utils.containsAny(content, GrammarModel.illegalTypeChar)) {
            // find illegal char
            List<String> illegalChar = new ArrayList<>();
            for (String c : GrammarModel.illegalTypeChar) {
                if (content.contains(c)) {
                    illegalChar.add(c);
                }
            }
            String allIllegalChars = "";
            for (String ill : illegalChar){
                allIllegalChars = allIllegalChars.concat(ill);
            }
            throw new MalformedTypeException("Type " + content + " cannot contain illegal " +
                    (allIllegalChars.length()>1?"characters":"character")+
                    " \'" + allIllegalChars + "\' in "+ parentId + ":" + content,
                    content, parentId);

        }

    }

    default void checkValidSubclasses(String txt, String genId){
        //must  start with SUBCLASSES
        if (txt.indexOf(GrammarModel.SUBCLASSES_TAG) != 0){
            String error = txt.split(" ")[0];
            throw new MalformedDeclarationException(
                    String.format("Cannot parse generalization %s, must have %s tag, but found %s",
                            genId, GrammarModel.SUBCLASSES_TAG, error==null?"null":error));
        }

    }

    default void checkValidAggregations(String txt){
        // todo


    }

}
