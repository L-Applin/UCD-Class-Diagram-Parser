package parsing;

import syntxTree.UmlContext;
import syntxTree.entries.DeclarationEntry;
import syntxTree.entries.IdentifierEntry;
import syntxTree.entries.RoleEntry;
import syntxTree.exceptions.*;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parsing.Delims.CUSTOM_LIST_SEP;
import static parsing.Delims.LIST_SEPERATOR;
import static parsing.Delims.SPACE;

/**
 * Helper classe that provides specific method for handling .ucd file.
 */
public class UcdParser implements ExceptionCheckProvider {

    /**
     * The string content that the parser will use to get, extract, convert, split or remove content from.
     */
    private String txt;

    public UcdParser(String txt){
        this.txt = txt;
    }
    public String getTxt() { return txt; }
    public UcdParser setTxt(String txt) { this.txt = txt; return this; }

    /**
     * find the fisrt group of text that is between two identifier (tokens).
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
     * expected value does not matches the actuall value
     *
     * @param expectedTag the expected value of the tag
     * @throws IncompatibleTagException when the actuall tag and the expected value does not matche.
     * @return
     */
    public IdentifierEntry convertIdEntry(String expectedTag){
        checkTagEqual(txt, expectedTag); // from ExceptionCheckProvider interface

        String[] tag_idPlusContent = txt.split(" ", 2);
        String[] id_content = tag_idPlusContent[1].split(Delims.NEW_LINE_TOKEN, 2);

        return new IdentifierEntry(id_content);

    }

    /**
     * Splits the tag and content
     * @return
     */
    public DeclarationEntry convertDeclarationEntry(){

        if (txt.contains(GrammarModel.Decs.AGGREGATION)){
            String[] splits = txt.split(Delims.NEW_LINE_TOKEN, 2);
            return new DeclarationEntry(splits[0], GrammarModel.Decs.AGGREGATION, splits[1]);
        } else {
            String[] tag_idPlusContent = txt.split(" ", 2);
            String[] id_content = tag_idPlusContent[1].split(Delims.NEW_LINE_TOKEN, 2);
            return new DeclarationEntry(tag_idPlusContent[0], id_content[0], id_content[1]);
        }

    }

    /**
     *
     * @param associationId
     * @return
     */
    public RoleEntry convertRolesEntry(String associationId){
        String[] entries = txt.split(SPACE);
        if (entries.length != 3){
            throw new MalformedDeclarationException("Malformed role \'" + txt + "\' in association \'" + associationId + "\'");
        }
        if (!entries[0].equals(GrammarModel.Decs.CLASS)){
            throw new MalformedDeclarationException("Malformed role \'" + txt + "\' in association \'" + associationId+"\'. " +
                    "Must begin with \'"+GrammarModel.Decs.CLASS+"\' tag");
        }
        return new RoleEntry(entries);
    }



    /**
     * Splits declration definition within a MODEL tag
     * @return all declaration separated and clean-up
     */
    public List<String> splitDeclarations(){
        List<String> result = new ArrayList<>();
        String[] decs = txt.split(Delims.DECLARATION_SEPERATOR);

        // clean up : remove new lines from beginning
        final Pattern pattern = Pattern.compile("^("+Delims.NEW_LINE_TOKEN+")+");
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
     * Splits text base on {@link Delims#LIST_SEPERATOR} separator and ignores tokens in bwtweene parenthesis.<br></br>
     * ex. : \tnombre_saisons() : Integer, change_statut(st : String, i : int) : void <br></br>
     * will only split in two part :  <ul><li>nombre_saisons() : Integer</li><li>change_statut(st : String, i : int) : void</li></ul>
     * @return the list of all split elements
     */
    public List<String> splitList(){

        // only split if not in parenthesis !

        // removeSpaces();

        String regExCommaInParent = "\\((.*?)\\)";
        final Matcher matcher = Pattern.compile(regExCommaInParent).matcher(txt);
        while (matcher.find()){
            if (matcher.group(1).contains(",")){
                final Matcher commaMatcher = Pattern.compile(LIST_SEPERATOR).matcher(matcher.group(1));
                String customSeparatedList = commaMatcher.replaceAll(CUSTOM_LIST_SEP);
                // Log.test(customSeparatedList);

                // replace orignal string with cutom seperator string
                final Matcher sepratedListMatcher = Pattern.compile(matcher.group(1)).matcher(txt);
                txt = sepratedListMatcher.replaceAll(customSeparatedList);

            }
        }
        String[] splits = txt.split(",");
        return new ArrayList<>(Arrays.asList(splits));
    }


    /**
     * Extract the string representing the Attribute lis from a class content string.
     * Used by the {@link syntxTree.expressions.ClassContent#tokenize(UmlContext, String)}
     * to get the classes attributes.
     *
     * @param classId name of the class
     * @param content it's content
     * @return the String with
     * @throws MissingClassTagException if the Attribute tag is missing from the class body
     * @throws MalformedClassException if the Attribute tag is there more than once in the body
     */
    public String extractAttributes(String classId, String content){

        // checks <attributes> tag is there from ExceptionCheckProvider interface
        checkTagPresent(txt, GrammarModel.ClassContent.ATTRIBUTES, classId, content);

        // check there is only one <attributes> tag from ExceptionCheckProvider interface
        checkNoDuplicateTag(txt, GrammarModel.ClassContent.ATTRIBUTES, classId, content);

        return extractBetween(GrammarModel.ClassContent.ATTRIBUTES, GrammarModel.ClassContent.OPERATIONS);

    }

    /**
     * Extract the string representing the Operation list from a class content string.
     * Used by the {@link syntxTree.expressions.ClassContent#tokenize(UmlContext, String)}
     * to get the classes methods.
     *
     * @param classId name of the class
     * @param content it's content
     * @return the String with
     * @throws MissingClassTagException if the Operation tag is missing from the class body
     * @throws MalformedClassException if the Operation tag is there more than once in the body
     */
    public String extractOperations(String classId, String content){

        // checks <OPERATIONS> tag is there from ExceptionCheckProvider interface
        checkTagPresent(txt, GrammarModel.ClassContent.OPERATIONS, classId, content);

        // check there is only one <OPERATIONS> tag from ExceptionCheckProvider interface
        checkNoDuplicateTag(txt, GrammarModel.ClassContent.OPERATIONS, classId, content);

        int index = txt.indexOf(GrammarModel.ClassContent.OPERATIONS);
        return txt.substring(index + GrammarModel.ClassContent.OPERATIONS.length(), txt.length());

    }


    public String extractArgList(String methodId, String classId){
        final Matcher matcher = Pattern.compile("\\((.*?)\\)").matcher(txt);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new MalformedOperationException("Could not find argument list of method \'" +methodId +"\'", classId, txt);
        }
    }


    public String extractGeneralizationClasses(String genId){
        txt = txt.trim();
        txt = removeNewLines(txt);
        checkValidSubclasses(txt, genId);
        String[] id_subClass = txt.split(" ", 2);
        return removeSpaces(id_subClass[1]);
    }

    /**
     *
     * @return
     */
    public String extractType(String parentId){
        String type = txt.substring(txt.lastIndexOf("):") + 2, txt.length());
        checkValidType(type, parentId);
        return type;
    }


    public String extractcParts(){
        // todo : chack valid form if not already ?
        int index = txt.indexOf(GrammarModel.PARTS_TAG);
        String result =  txt.substring(index + GrammarModel.PARTS_TAG.length(), txt.length());
        return removeNewLines(result);
    }




    public String[] splitDataItem(String parentId){
        checkValidDataItem(txt, parentId);

        return txt.split(":", 2);
    }


    public String[] splitTwoRoles(String association){
        checkValidRole(txt, association);

        String roles = txt.split(Delims.NEW_LINE_TOKEN)[1];
        String[] twoRoles = roles.split(LIST_SEPERATOR);
        return new String[]{twoRoles[0].trim(), twoRoles[1].trim()};

    }


    public List<String> splitArgs(){
        String[] args = txt.split("~");
        return new ArrayList<>(Arrays.asList(args));
    }




    public static String removeNewLines(String txt){
        String regEx = "("+Delims.NEW_LINE_TOKEN+")+";
        return Pattern.compile(regEx).matcher(txt).replaceAll("");
    }


    public static String removeSpaces(String txt){
        String regEx = " ";
        return Pattern.compile(regEx).matcher(txt).replaceAll("");
    }



    public String getOperationId(String classId){
        removeSpaces();
        checkValidOperation(txt, classId);

        return txt.substring(0, txt.indexOf("("));
    }


    public String replaceNewLineToken(){
        return Pattern.compile(Delims.NEW_LINE_TOKEN).matcher(txt).replaceAll("\n");
    }

    public String replaceCustomListSeperator(){
        return Pattern.compile(Delims.CUSTOM_LIST_SEP).matcher(txt).replaceAll(", ");
    }


    /**
     * WARNING : Modifies the {@link UcdParser#txt} attribute by removing all {@link Delims#SPACE} tag from it.
     * Use with caution .
     */
    private void removeSpaces(){
        txt = Pattern.compile(SPACE).matcher(txt).replaceAll("");
    }

}
