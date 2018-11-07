package app;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * testing simple features of the JavaAnalyzer class
 */
public class JavaAnalyzerTest {

    private String test1    = "java.lang.util.List";
    private String test2    = "ArrayList";
    private String test3    = "java.lang.reflectiom.super.long.test.with.many.many.many.dot.FinalClass";
    private String generic1 = "ArrayList<String>";
    private String generic2 = "java.util.ArrayList<token.UmlToken>";
    private String generic3 = "Map<src.token.UmlToken>";
    private String generic4 = "java.util.Map<UmlToken>";

    /**
     * See method description
     */
    @Test
    public void removePackageName() {

        Assert.assertEquals(
                "testing : java.lang.util.List",
                "List",
                JavaAnalyzer.removePackageName(test1));

        Assert.assertEquals(
                "testing : ArrayList",
                "ArrayList",
                JavaAnalyzer.removePackageName(test2));

        Assert.assertEquals(
                "testing : java.lang.reflectiom.super.long.test.with.many.many.many.dot.FinalClass",
                "FinalClass",
                JavaAnalyzer.removePackageName(test3));

        Assert.assertEquals(
                "testing : ArrayList<String>",
                "ArrayList<String>",
                JavaAnalyzer.removePackageName(generic1));

        Assert.assertEquals(
                "testing : java.util.ArrayList<token.UmlToken>",
                "ArrayList<UmlToken>",
                JavaAnalyzer.removePackageName(generic2));

        Assert.assertEquals(
                "testing : Map<src.token.UmlToken>",
                "Map<UmlToken>",
                JavaAnalyzer.removePackageName(generic3));

        Assert.assertEquals(
                "testing : java.util.Map<UmlToken>",
                "Map<UmlToken>",
                JavaAnalyzer.removePackageName(generic4));


    }
}