package parsing;

import syntxTree.IdentifierEntry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UmlParser {

    private String txt;

    public UmlParser(){
        this.txt = txt;
    }

    public UmlParser(String txt){
        this.txt = txt;
    }


    /**
     * find the fisrt group of text that is between two identifier (tokens).
     * @param text the text to be analyzed
     * @param beginToken left tag
     * @param endToken right tag
     * @return the string that is between the two tags.
     */
    public String extractBetween(String text, String beginToken, String endToken){

        String regEx = beginToken + "(.+?)" + endToken;

        final Pattern pattern = Pattern.compile(regEx);
        final Matcher match = pattern.matcher(text);
        return match.group(1);

    }


    public String extractBetween(String beginToken, String endToken){
        return extractBetween(this.txt, beginToken, endToken);
    }


    public IdentifierEntry splitIdContent(){

        String[] result = new String[2];
        // todo : the actuall split
        return new IdentifierEntry(result[0], result[1]);

    }

}
