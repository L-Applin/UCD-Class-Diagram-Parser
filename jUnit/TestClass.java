import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass {

    private String hello = "Hello World !";

    @Test
    public void testMethod(){

        final Pattern pattern = Pattern.compile("CLASS(.+?);");
        final Matcher matcher = pattern.matcher("CLASS String I want to extract ;");
        if(matcher.find()){
            System.out.println(matcher.group(1)); // Prints String I want to extract
        }


    }

}
