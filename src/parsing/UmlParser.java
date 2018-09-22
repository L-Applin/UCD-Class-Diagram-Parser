package parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UmlParser {

    private String txt;

    public UmlParser(String txt){

        this.txt = txt;

    }


    public String extractBetween(String text, String beginToken, String endToken){

        String regEx = beginToken + "(.+?)" + endToken;

        final Pattern pattern = Pattern.compile(regEx);
        final Matcher match = pattern.matcher(text);
        return match.group(1);

    }

}
