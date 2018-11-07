package parsing;

import parsing.syntaxTree.entries.DeclarationEntry;
import parsing.syntaxTree.entries.IdentifierEntry;
import parsing.syntaxTree.entries.RoleEntry;
import parsing.syntaxTree.exceptions.ExceptionCheckProvider;
import parsing.syntaxTree.exceptions.MalformedFileException;
import token.UmlContext;
import app.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parsing.GrammarModel.*;
import static parsing.GrammarModel.ClassContent.ATTRIBUTES;
import static parsing.GrammarModel.ClassContent.OPERATIONS;
import static parsing.GrammarModel.Decs.AGGREGATION;
import static parsing.GrammarModel.Decs.ASSOCIATION;
import static parsing.GrammarModel.Decs.GENERALIZATION;


/**
 * Helper class that provides specific method for handling .ucd file.
 */
public class UcdParser implements ExceptionCheckProvider {

    /**
     * The string content that the parser will use to get, extract, convert, split or remove content from.
     */
    private String txt;

    public UcdParser(String txt){
    	this.txt = txt.trim();
    }
    public String getTxt() { return txt; }
    public UcdParser setTxt(String txt) { this.txt = txt; return this; }


    /**
     * find the first group of text that is between two identifier (tokens).
     *
     * @param beginToken left tag
     * @param endToken right tag
     * @return the string that is between the two tags.
     */
    public String extractBetween(String beginToken, String endToken){

        final Matcher match = Pattern.compile(beginToken + "(.+?)" + endToken).matcher(txt);

        if (match.find()){
            return match.group(1);
        } else {
            return "";
        }
    }


    /**
     * Divides a section of the .ucd file into it's tag and it's content. Throws an exception if the
     * expected value does not match the actual value
     *
     * @param expectedTag the expected value of the tag
     * @throws MalformedFileException when the actual tag and the expected value does not match.
     * @return
     */
    public IdentifierEntry convertIdEntry(String expectedTag){
        checkTagEqual(txt, expectedTag); // from ExceptionCheckProvider interface

        String[] tag_idPlusContent = txt.split(" ", 2);
        String[] id_content = tag_idPlusContent[1].split(NEW_LINE_TOKEN, 2);

        return new IdentifierEntry(id_content);

    }


    /**
     * Splits the tag and content of a declaration.
     * @return
     */
    public DeclarationEntry convertDeclarationEntry(){

        if (txt.contains(GrammarModel.Decs.AGGREGATION)){
            String[] splits = txt.split(NEW_LINE_TOKEN, 2);
            return new DeclarationEntry(splits[0], GrammarModel.Decs.AGGREGATION, splits[1]);
        } else {
            String[] tag_idPlusContent = txt.split(" ", 2);
            String[] id_content = tag_idPlusContent[1].split(NEW_LINE_TOKEN, 2);
            return new DeclarationEntry(tag_idPlusContent[0], id_content[0], id_content[1]);
        }

    }


    /**
     * This converts the String representation of an association and extract all relevant information
     * @param associationId The String representation of the Association
     * @return A structure containing all relevant infos of the Association.
     */
    public RoleEntry convertRolesEntry(String associationId){
    	String[] entries = txt.split(SPACE);
        if (entries.length != 3){
            throw new MalformedFileException("Malformed role \'" + txt + "\' in \'" + associationId + "\'");
        }
        if (!entries[0].equals(GrammarModel.Decs.CLASS)){
            throw new MalformedFileException("Malformed role \'" + txt + "\' in \'" + associationId+"\'. " +
                    "Must begin with \'"+GrammarModel.Decs.CLASS+"\' tag");
        }
        return new RoleEntry(entries);
    }



    /**
     * Splits declaration definition within a MODEL tag
     * @return all declaration separated and clean-up
     */
    public List<String> splitDeclarations(){
        List<String> result = new ArrayList<>();
        String[] decs = txt.split(DECLARATION_SEPERATOR);

        // clean up : remove new lines from beginning
        final Pattern pattern = Pattern.compile("^("+NEW_LINE_TOKEN+")+");
        Matcher match;
        for (String dec : decs){
            match = pattern.matcher(dec);
            if (match.find()){
                dec = match.replaceAll("");
            }
            if (!dec.equals("")) {
                result.add(dec);
            }

        }
        return result;
    }


    /**
     * Splits the attributes list in respect to keeping Type within parameterizec types coeherent. As such, it
     * will not split on the comma in <pre>Map<String, String></></pre>.
     * @return A list of all attributes
     */
    public List<String> splitAttributeList(){
        String regExCommaNotInTag = ",\\s*(?![^<>]*\\>)";
        String[] splits = Pattern.compile(regExCommaNotInTag).matcher(txt).replaceAll("~").split("~");
        return new ArrayList<>(Arrays.asList(splits));

    }


    /**
     * Splits text based on {@link GrammarModel#LIST_SEPERATOR} separator and ignores tokens in between parenthesis.<br></br>
     * ex. : \tnombre_saisons() : Integer, change_statut(st : String, i : int) : void <br></br>
     * will only split in two parts :  <ul><li>nombre_saisons() : Integer</li><li>change_statut(st : String, i : int) : void</li></ul>
     * @return the list of all split elements
     */
    public List<String> splitList(){

        if (txt.trim().equals("")) return new ArrayList<>();

        /* DOES NOT KEEP SPACES AFTER COMMA IN PARAMETERIZED TYPE */

        String regEx_commaNotInParentheses = ",\\s*(?![^()]*\\))";
        String regEx_tidleWithinAngleBrackets = "~(?=[^<>]*\\>)";

        // only split if comma not in parenthesis !
        Matcher commeInParent = Pattern.compile(regEx_commaNotInParentheses).matcher(txt);
        String tmpSeperator = commeInParent.replaceAll("~");

        // need to replace ~ within <> by commas to split correctly
        String readyToSplit = Pattern.compile(regEx_tidleWithinAngleBrackets).matcher(tmpSeperator).replaceAll(",");
        String[] splits = readyToSplit.split("~");

        return new ArrayList<>(Arrays.asList(splits));
    }


    /**
     * Extract the string representing the Attribute list from a class content string.
     * Used by the {@link parsing.syntaxTree.expressions.ClassContent#tokenize(UmlContext, String)}
     * to get the classes attributes.
     *
     * @return the String with
     * @throws MalformedFileException if the Attribute tag is there more than once in the body
     */
    public String extractAttributes(){

        // checks <attributes> tag is there from ExceptionCheckProvider interface
        checkTagPresent(txt, GrammarModel.ClassContent.ATTRIBUTES);

        // check there is only one <attributes> tag from ExceptionCheckProvider interface
        checkNoDuplicateTag(txt, GrammarModel.ClassContent.ATTRIBUTES);

        return extractBetween(GrammarModel.ClassContent.ATTRIBUTES, GrammarModel.ClassContent.OPERATIONS);

    }


    /**
     * Extract the string representing the Operation list from a class content string.
     * Used by the {@link parsing.syntaxTree.expressions.ClassContent#tokenize(UmlContext, String)}
     * to get the classes methods.
     *
     * @return the String with all operation
     */
    public String extractOperations(){

        // checks <OPERATIONS> tag is there from ExceptionCheckProvider interface
        checkTagPresent(txt, GrammarModel.ClassContent.OPERATIONS);

        // check there is only one <OPERATIONS> tag from ExceptionCheckProvider interface
        checkNoDuplicateTag(txt, GrammarModel.ClassContent.OPERATIONS);

        int index = txt.indexOf(GrammarModel.ClassContent.OPERATIONS);
        return txt.substring(index + GrammarModel.ClassContent.OPERATIONS.length(), txt.length()).trim();

    }


    /**
     * Extract a String representing all argument of a method.
     * @param methodId the name of the method
     * @return a String containing only all arguments
     */
    public String extractArgList(String methodId){
        final Matcher matcher = Pattern.compile("\\((.*?)\\)").matcher(txt);
        if (matcher.find()) {
            return matcher.group(1).trim();
        } else {
            throw new MalformedFileException("Could not find argument list of method '" + methodId +"'");
        }
    }


    /**
     * Extracts the class from a String representation of a GENERELIZATION section of the
     * .ucd file.
     * @return the class name of the GENERELIZATION
     */
    public String extractGeneralizationSubclasses(){
        txt = txt.trim();
        txt = removeNewLines(txt);
        checkValidSubclasses(txt);
        String[] id_subClass = txt.split(" ", 2);

        if (id_subClass.length < 2){
            malformed();
        }

        if (!id_subClass[0].equals(GrammarModel.SUBCLASSES_TAG)){
            malformed();
        }

        return removeSpaces(id_subClass[1]);
    }


    /**
     * Extract the return type of an operation
     * @return the String representation of the return type
     */
    public String extractType(){
        removeSpaces();
        if(!txt.contains("):")){
            malformed();
        }
        String type = txt.substring(txt.lastIndexOf("):") + 2);
        checkValidType(type);
        return type.trim();
    }

    /**
     * Extract parts from an aggregation string representation
     * @return the extracted parts
     */
    public String extractcParts(){
        // todo : chack valid form if not already ?
        int index = txt.indexOf(GrammarModel.PARTS_TAG);
        String result =  txt.substring(index + GrammarModel.PARTS_TAG.length(), txt.length());
        return removeNewLines(result);
    }


    /**
     * From a DataItem representation, splits it into its data item form
     * @return the data items
     */
    public String[] splitDataItem(){
        checkValidDataItem(txt);

        return txt.split(":", 2);
    }


    /**
     * splits the two role definition into two seperate string
     * @return the array containing both Roles
     */
    public String[] splitTwoRoles(){
        String[] res = new String[2];
        try {
            checkValidRole(txt);
            String roles = txt.split(NEW_LINE_TOKEN, 2)[1];
            String[] tmp = roles.split(LIST_SEPERATOR);
            res[0] = UcdParser.removeNewLines(tmp[0]).trim();
            res[1] = UcdParser.removeNewLines(tmp[1]).trim();
        } catch (ArrayIndexOutOfBoundsException ex){
            malformed();
        }
        return res;

    }


    /**
     * Simply splits on comma
     * @return a ArrayList of String split on commas
     */
    public List<String> splitArgs(){
        String[] args = txt.split(",");
        return new ArrayList<>(Arrays.asList(args));
    }


    /**
     * removes all {@link GrammarModel#NEW_LINE_TOKEN} from a String.
     * @param txt the String to operate on
     * @return a clean text without any new line token
     */
    public static String removeNewLines(String txt){
        String regEx = "("+NEW_LINE_TOKEN+")+";
        return Pattern.compile(regEx).matcher(txt).replaceAll("");
    }

    /**
     * Replaces custom {@link GrammarModel#NEW_LINE_TOKEN} with regule "\\n"
     * @param txt the String to operate on
     * @return a clean String where new line tokens have been replace with standard "backslash n"
     */
    public static String replaceNewLines(String txt){
        String regEx = "("+NEW_LINE_TOKEN+")+";
        return Pattern.compile(regEx).matcher(txt).replaceAll("\n");
    }


    /**
     * Remove all blank spaces from a a String
     * @param txt the String to operateon
     * @return a clean text without any blank spaces
     */
    public static String removeSpaces(String txt){
        String regEx = " ";
        return Pattern.compile(regEx).matcher(txt).replaceAll("");
    }


    /**
     * From a correctly formatted String, extract the text before the first opening parentheses.
     * This is usually used to extract the name of a method.
     * @return The extracted String
     */
    public String getOperationId(){
        removeSpaces();
        checkValidOperation(txt);
        return txt.substring(0, txt.indexOf("("));
    }


    /**
     * The method is used to replace the {@link GrammarModel#NEW_LINE_TOKEN}
     * by the defaut new line char
     * @return the string with the new line char replaced
     * @return
     */
    public String replaceNewLineToken(){
        return Pattern.compile(NEW_LINE_TOKEN).matcher(txt).replaceAll("\n");
    }

    /**
     * The method is used to replace the {@link GrammarModel#CUSTOM_LIST_SEP}
     * by the defaut comma seperator
     * @return the string with the seperator replaced
     */
    public String replaceCustomListSeperator(){
        return Pattern.compile(CUSTOM_LIST_SEP).matcher(txt).replaceAll(", ");
    }


    /**
     * WARNING : Modifies the {@link UcdParser#txt} attribute by removing all {@link GrammarModel#SPACE} tag from it.
     * Use with caution !
     */
    private void removeSpaces(){
        txt = Pattern.compile(SPACE).matcher(txt).replaceAll("");
    }


    /**
     * Formats the String content of the token for display purpose.
     * @return the String ready to be displayed,
     */
    public String formatContent() {
        UcdParser parser = new UcdParser(txt);
        String tmp = parser.replaceNewLineToken();

        BufferedReader bufferedReader = new BufferedReader(new StringReader(tmp));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {

                parser.setTxt(line);
                parser.splitList().forEach(elem -> {

                    // remove custom line breaks
                    String cleaned = parser.setTxt(elem).replaceCustomListSeperator();

                    // add indent for cleaner display
                    if (!Utils.containsAny(cleaned,
                            GENERALIZATION, ASSOCIATION, AGGREGATION, ATTRIBUTES, OPERATIONS, MODEL_TAG,
                            SUBCLASSES_TAG, CONTAINER_TAG, ROLES_TAG, PARTS_TAG)
                            && Utils.containsAny(txt,
                            GENERALIZATION, ASSOCIATION, AGGREGATION, ATTRIBUTES, OPERATIONS, MODEL_TAG,
                            SUBCLASSES_TAG, CONTAINER_TAG, ROLES_TAG, PARTS_TAG)) {
                        sb.append("\t").append(cleaned).append("\n");
                    } else {
                        sb.append(elem).append("\n");
                    }
                });
            }
            return formatTypeSeperator(sb.toString());
        } catch (IOException ioe) {
            return formatTypeSeperator(tmp);
        }

    }

    /**
     * replaces custom type separator with regular separator and space
     * @param txt the text to convert
     * @return the text with its custom separator removed
     */
    private String formatTypeSeperator(String txt){
        Matcher matcher = Pattern.compile(TYPE_SEPARATOR).matcher(txt);
        return matcher.replaceAll(SPACE + TYPE_SEPARATOR + SPACE);
    }

}
