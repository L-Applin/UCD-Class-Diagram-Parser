package token;

import app.AppController;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static token.UmlMetric.MetricType.*;



public class MetricCalculatorTest {

    // change for your system (I need to understand java "ressource"/"project file" management ...)
    private static String path_to_small_test_file = "/Users/Applin/Documents/dev/parser-tp1/res/testFiles/test_small.ucd";
    private static String path_to_medium_test_file = "/Users/Applin/Documents/dev/parser-tp1/res/testFiles/test_medium.ucd";


    private static UmlContext ctxSmall, ctxMedium, ctxLarge;
    private static UmlClass sLast, sOther, sParent, sChild;
    private static UmlClass mOther, mParent, mChild1, mChild2, mChild3, mGrandChild1, mGrandChild2;



    @BeforeClass
    public static void init() throws IOException, IllegalAccessException {

        AppController app = new AppController();

        String smallFile = app.openUcdFile(path_to_small_test_file);
        ctxSmall = app.parseUcdFile(smallFile);

        sLast = ctxSmall.getUmlClass("Last");
        sParent = ctxSmall.getUmlClass("Parent");
        sOther = ctxSmall.getUmlClass("Other");
        sChild = ctxSmall.getUmlClass("Child");

        ctxSmall.calculateMetrics();

        String mediumFile = app.openUcdFile(path_to_medium_test_file);
        ctxMedium = app.parseUcdFile(mediumFile);
        ctxMedium.calculateMetrics();
        // ctxMedium.logMetrics();
    }

    @Test
    public void calculateANA() {
        assertEquals(1.0, sParent.getMetricValue(ANA), Double.MIN_VALUE);
        assertEquals(1.0, sChild.getMetricValue(ANA), Double.MIN_VALUE);
        assertEquals(0.0, sOther.getMetricValue(ANA), Double.MIN_VALUE);
        assertEquals(0.5, sLast.getMetricValue(ANA), Double.MIN_VALUE);
    }

    @Test
    public void calculateNOM() {
        assertEquals(3.0, sParent.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(4.0, sChild.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(0.0, sOther.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(2.0, sLast.getMetricValue(NOM), Double.MIN_VALUE);
    }

    @Test
    public void calculateNOA() {
        assertEquals(1.0, sParent.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(2.0, sChild.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(2.0, sOther.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(0.0, sLast.getMetricValue(NOA), Double.MIN_VALUE);
    }

    @Test
    public void calculateITC() {
        //todo
        assertEquals(0, sParent.getMetricValue(ITC), Double.MIN_VALUE);
        assertEquals(0, sChild.getMetricValue(ITC), Double.MIN_VALUE);
        assertEquals(0, sOther.getMetricValue(ITC), Double.MIN_VALUE);
        assertEquals(0, sLast.getMetricValue(ITC), Double.MIN_VALUE);

    }

    @Test
    public void calculateETC() {
        //todo
       assertEquals(0, sParent.getMetricValue(ETC), Double.MIN_VALUE);
       assertEquals(0, sChild.getMetricValue(ETC), Double.MIN_VALUE);
       assertEquals(0, sOther.getMetricValue(ETC), Double.MIN_VALUE);
       assertEquals(0, sLast.getMetricValue(ETC), Double.MIN_VALUE);

    }

    @Test
    public void calculateCAC() {
        //todo
        assertEquals(0, sParent.getMetricValue(CAC), Double.MIN_VALUE);
        assertEquals(0, sChild.getMetricValue(CAC), Double.MIN_VALUE);
        assertEquals(0, sOther.getMetricValue(CAC), Double.MIN_VALUE);
        assertEquals(0, sLast.getMetricValue(CAC), Double.MIN_VALUE);

    }

    @Test
    public void calculateDIT() {
        //todo
        assertEquals(0, sParent.getMetricValue(DIT), Double.MIN_VALUE);
        assertEquals(1, sChild.getMetricValue(DIT), Double.MIN_VALUE);
        assertEquals(0, sOther.getMetricValue(DIT), Double.MIN_VALUE);
        assertEquals(0, sLast.getMetricValue(DIT), Double.MIN_VALUE);

    }

    @Test
    public void calculateCLD() {
        //todo
        assertEquals(1, sParent.getMetricValue(CLD), Double.MIN_VALUE);
        assertEquals(0, sChild.getMetricValue(CLD), Double.MIN_VALUE);
        assertEquals(0, sOther.getMetricValue(CLD), Double.MIN_VALUE);
        assertEquals(0, sLast.getMetricValue(CLD), Double.MIN_VALUE);

    }

    @Test
    public void calculateNOC() {
        //todo
        assertEquals(1, sParent.getMetricValue(NOC), Double.MIN_VALUE);
        assertEquals(0, sChild.getMetricValue(NOC), Double.MIN_VALUE);
        assertEquals(0, sOther.getMetricValue(NOC), Double.MIN_VALUE);
        assertEquals(0, sLast.getMetricValue(NOC), Double.MIN_VALUE);

    }

    @Test
    public void calculateNOD() {
        //todo
        assertEquals(1, sParent.getMetricValue(NOD), Double.MIN_VALUE);
        assertEquals(0, sChild.getMetricValue(NOD), Double.MIN_VALUE);
        assertEquals(0, sOther.getMetricValue(NOD), Double.MIN_VALUE);
        assertEquals(0, sLast.getMetricValue(NOD), Double.MIN_VALUE);

    }

}