package parsing.syntaxTree.exceptions;

import parsing.GrammarModel;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parsing.GrammarModel.LIST_SEPERATOR;
import static parsing.GrammarModel.NEW_LINE_TOKEN;
import static parsing.GrammarModel.TYPE_SEPARATOR;


/**
 * Provides default methods for checking different types of possible error while parsing the ucd file.
 */
public interface ExceptionCheckProvider {

    /**
     * Verify that a specified tag is actually at the beginning of the text.
     * @param txt
     * @param delim
     */
    default void checkTagEqual(String txt, String delim){

        String[] splits = txt.split(" ", 2);

        if (splits.length < 2) {
            malformed();
        }

        if (!splits[0].equals(delim)){
            malformed();
        }
    }


    /**
     * Makes sure that class declaration contains OPERATION and ATTRIBUTES
     * @param classContent
     */
    default void checkClassContent(String classContent){

        if (!classContent.contains(GrammarModel.ClassContent.OPERATIONS)){
            malformed();
        }

        if (!classContent.contains(GrammarModel.ClassContent.ATTRIBUTES)){
            malformed();
        }

    }

    /**
     * Makes sure the text does not contains illegal characters
     * @param txt the text to verify
     */
    default void checkIllegalChar(String txt){
        for (String illegal : GrammarModel.illegalChar){
            if (txt.contains(illegal)){
                malformed();
            }
        }
    }


    /**
     * Makes sure that a specified text is within another text.
     * Basically a wrapper around {@link String#contains(CharSequence)} that can throw an exception
     * if it is not contained
     * @param txt the text to analyze
     * @param tag the text to check if it is contained or not
     */
    default void checkTagPresent(String txt, String tag) {
        if (!txt.contains(tag)) {
            malformed();
        }
    }

    /**
    *
    * @param txt
    * @param tag
    */
    default void checkNoDuplicateTag(String txt, String tag){
        // check there is only one <attributes> tag
        if (txt.indexOf(tag) != txt.lastIndexOf(tag)){
            malformed();
        }

    }


    default void checkValidOperation(String txt){
        // Check only if has method parameter.
        // Param validation is done in checkValidDataItem later on during parsing
        String paramsRegEx = "\\((.*?)\\)|\\(\\)";
        Matcher matcher = Pattern.compile(paramsRegEx).matcher(txt);
        if (!matcher.find()){
            malformed();
        }

        //checks for method return type
        if (!txt.contains("):")){    // ok because spaces were remove
            malformed();
        }

        String validationType = txt.substring(txt.lastIndexOf("):") +2, txt.length());
        checkValidType(validationType);

    }


    default void checkValidDataItem(String txt){
        // must contains at least one ':'
        if (!txt.contains(TYPE_SEPARATOR)){
            malformed();
        }

        // must not contains more than 1 ':'
        if (txt.lastIndexOf(TYPE_SEPARATOR) != txt.indexOf(TYPE_SEPARATOR)){
            malformed();
        }

    }


    default void checkValidRole(String txt){

        if (txt.indexOf(GrammarModel.ROLES_TAG) != 0){
            malformed();
        }

        String roles = txt.split(NEW_LINE_TOKEN)[1];

        // only one LIST_SEPERATOR => exaclty two roles
        if (roles.indexOf(LIST_SEPERATOR) != roles.lastIndexOf(LIST_SEPERATOR)){
            malformed();
        }

    }



    default void checkValidType(String content) {
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
            malformed();

        }

    }

    default void checkValidSubclasses(String txt){
        //must start with SUBCLASSES
        if (txt.indexOf(GrammarModel.SUBCLASSES_TAG) != 0){
            malformed();
        }

    }

    default void checkValidAggregations(String txt){
    	checkTagPresent(txt, GrammarModel.PARTS_TAG);
        checkTagPresent(txt, GrammarModel.CONTAINER_TAG);
    }


    private void malformed(){
        throw new MalformedFileException();
    }

}
