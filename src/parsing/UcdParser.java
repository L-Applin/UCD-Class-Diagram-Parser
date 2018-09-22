package parsing;

import syntxTree.DeclarationEntry;
import syntxTree.IdentifierEntry;
import syntxTree.exceptions.ExceptionCheckProvider;
import syntxTree.exceptions.IncompatibleTagException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UcdParser implements ExceptionCheckProvider {

    private String txt;

    public UcdParser(String txt){
        this.txt = txt;
    }


    /**
     * find the fisrt group of text that is between two identifier (tokens).
     *
     * @param beginToken left tag
     * @param endToken right tag
     * @return the string that is between the two tags.
     */
    public String extractBetween(String beginToken, String endToken){
        String regEx = beginToken + "(.+?)" + endToken;

        final Pattern pattern = Pattern.compile(regEx);
        final Matcher match = pattern.matcher(txt);
        return match.group(1);
    }

    /**
     * Divides a section of the .ucd file into it's tag and it's content. Throws an exception if the
     * expected value does not matches the actuall value
     *
     * @param tag the expected value of the tag
     * @throws IncompatibleTagException when the actuall tag and the expected value does not matche.
     * @return
     */
    public IdentifierEntry splitIdContent(String tag){
        checkTagEqual(txt, tag);

        String[] tag_idPlusContent = txt.split(" ", 2);
        String[] id_content = tag_idPlusContent[1].split(Delims.NEW_LINE_TOKEN, 2);

        return new IdentifierEntry(id_content[0], id_content[1]);

    }

    /**
     * Splits the tag and content
     * @return
     */
    public DeclarationEntry splitDeclarationEntry(){

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
                result.add(match.replaceAll(""));
            }
        }
        return result;
    }

}
