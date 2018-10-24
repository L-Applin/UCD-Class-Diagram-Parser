package parsing;

import token.UmlContext;
import syntaxTree.entries.DeclarationEntry;
import syntaxTree.entries.IdentifierEntry;
import syntaxTree.entries.RoleEntry;
import syntaxTree.exceptions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parsing.Delims.CUSTOM_LIST_SEP;
import static parsing.Delims.LIST_SEPERATOR;
import static parsing.Delims.SPACE;

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
     * Splits text based on {@link Delims#LIST_SEPERATOR} separator and ignores tokens in between parenthesis.<br></br>
     * ex. : \tnombre_saisons() : Integer, change_statut(st : String, i : int) : void <br></br>
     * will only split in two parts :  <ul><li>nombre_saisons() : Integer</li><li>change_statut(st : String, i : int) : void</li></ul>
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
                // replace original string with custom separator string
                // TODO : find a way to make it work with square brackets [] for array type
                final Matcher sepratedListMatcher = Pattern.compile(matcher.group(1)).matcher(txt);
                txt = sepratedListMatcher.replaceAll(customSeparatedList);

            }
        }
        String[] splits = txt.split(",");
        return new ArrayList<>(Arrays.asList(splits));
    }


    /**
     * Extract the string representing the Attribute list from a class content string.
     * Used by the {@link syntaxTree.expressions.ClassContent#tokenize(UmlContext, String)}
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
     * Used by the {@link syntaxTree.expressions.ClassContent#tokenize(UmlContext, String)}
     * to get the classes methods.
     *
     * @return the String with
     */
    public String extractOperations(){

        // checks <OPERATIONS> tag is there from ExceptionCheckProvider interface
        checkTagPresent(txt, GrammarModel.ClassContent.OPERATIONS);

        // check there is only one <OPERATIONS> tag from ExceptionCheckProvider interface
        checkNoDuplicateTag(txt, GrammarModel.ClassContent.OPERATIONS);

        int index = txt.indexOf(GrammarModel.ClassContent.OPERATIONS);
        return txt.substring(index + GrammarModel.ClassContent.OPERATIONS.length(), txt.length());

    }


    public String extractArgList(String methodId, String classId){
        final Matcher matcher = Pattern.compile("\\((.*?)\\)").matcher(txt);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new MalformedFileException("Could not find argument list of method '" + methodId +"'");
        }
    }


    public String extractGeneralizationClasses(){
        txt = txt.trim();
        txt = removeNewLines(txt);
        checkValidSubclasses(txt);
        String[] id_subClass = txt.split(" ", 2);
        return removeSpaces(id_subClass[1]);
    }

    /**
     *
     * @return
     */
    public String extractType(){
        String type = txt.substring(txt.lastIndexOf("):") + 2, txt.length());
        checkValidType(type);
        return type;
    }


    public String extractcParts(){
        // todo : chack valid form if not already ?
        int index = txt.indexOf(GrammarModel.PARTS_TAG);
        String result =  txt.substring(index + GrammarModel.PARTS_TAG.length(), txt.length());
        return removeNewLines(result);
    }




    public String[] splitDataItem(){
        checkValidDataItem(txt);

        return txt.split(":", 2);
    }


    public String[] splitTwoRoles(){
        checkValidRole(txt);

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

    public static String replaceNewLines(String txt){
        String regEx = "("+Delims.NEW_LINE_TOKEN+")+";
        return Pattern.compile(regEx).matcher(txt).replaceAll("\n");
    }



    public static String removeSpaces(String txt){
        String regEx = " ";
        return Pattern.compile(regEx).matcher(txt).replaceAll("");
    }



    public String getOperationId(){
        removeSpaces();
        checkValidOperation(txt);
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
