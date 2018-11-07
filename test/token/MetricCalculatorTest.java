package token;

import app.AppController;
import app.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static token.UmlMetric.MetricType.*;




public class MetricCalculatorTest {

    /**
     * POUR S'ASSURER DE ROULER LES TESTS SANS ERREURS:
     * MODIFIER CETTE LIGNE POUR INCLURE LE CHEMIN VERS L'ENDROIT OÙ LE FICHIER
     * CONENANT LE DOSSIER 'parser-tp1' SE TROUVE !!!!
     *
     * Présentement, on utilise le chemin absolu avec {@link System#getProperties()}
     * pour trouver le chemin vers le fichier. Fiable, mais c'est dangereux sur plusieurs systèmes
     * différent. Ce n'est testé que sur quelques systèmes différents (MAC et PC tout de même).
     */
    public static String path_to_res = System.getProperty("user.dir");

    public static String path_to_small_test_file = path_to_res + "/res/testFiles/test_small.ucd";
    public static String path_to_medium_test_file = path_to_res + "/res/testFiles/test_medium.ucd";

    private static String ana_path, nom_path,noa_path,itc_path, cac_path, dit_path;

    private static UmlContext ctxSmall, ctxMedium, ctxLarge;
    private static UmlClass sLast, sOther, sParent, sChild;



    @BeforeClass
    public static void init() throws IOException, IllegalAccessException {

        ana_path = path_to_res + "/res/testFiles/ana_test.ucd";
        nom_path = path_to_res + "/res/testFiles/nom_test.ucd";
        noa_path = path_to_res + "/res/testFiles/noa_test.ucd";
        itc_path = path_to_res + "/res/testFiles/itc_test.ucd";
        cac_path = path_to_res + "/res/testFiles/cac_test.ucd";
        dit_path = path_to_res + "/res/testFiles/dit_test.ucd";


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

    }

    @Test
    public void calculateANA() throws IOException, IllegalAccessException {

        assertEquals(1.0, sParent.getMetricValue(ANA), Double.MIN_VALUE);
        assertEquals(1.0, sChild.getMetricValue(ANA), Double.MIN_VALUE);
        assertEquals(0.0, sOther.getMetricValue(ANA), Double.MIN_VALUE);
        assertEquals(0.5, sLast.getMetricValue(ANA), Double.MIN_VALUE);

        AppController app = new AppController();

        String file = app.openUcdFile(ana_path);
        UmlContext anaContext = app.parseUcdFile(file);
        anaContext.calculateMetrics();

        UmlClass test1 = anaContext.getUmlClass("Test1");
        UmlClass test2 = anaContext.getUmlClass("Test2");
        UmlClass test3 = anaContext.getUmlClass("Test3");
        UmlClass test4 = anaContext.getUmlClass("Test4");
        UmlClass test5 = anaContext.getUmlClass("Test5");
        UmlClass test6 = anaContext.getUmlClass("Test6");

        assertEquals(0.00, test1.getMetricValue(ANA), Double.MIN_VALUE);
        assertEquals(0.00, test2.getMetricValue(ANA), Double.MIN_VALUE);
        assertEquals(1.00, test3.getMetricValue(ANA), Double.MIN_VALUE);
        assertEquals(1.00, test4.getMetricValue(ANA), Double.MIN_VALUE);
        assertEquals(1.25, test5.getMetricValue(ANA), Double.MIN_VALUE);
        assertEquals(4.00, test6.getMetricValue(ANA), Double.MIN_VALUE);

    }

    @Test
    public void calculateNOM() throws IOException, IllegalAccessException{

        assertEquals(3.0, sParent.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(4.0, sChild.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(0.0, sOther.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(2.0, sLast.getMetricValue(NOM), Double.MIN_VALUE);

        AppController app = new AppController();

        String file = app.openUcdFile(nom_path);
        UmlContext nomContext = app.parseUcdFile(file);
        nomContext.calculateMetrics();

        UmlClass parent1 = nomContext.getUmlClass("Parent1");
        UmlClass parent2 = nomContext.getUmlClass("Parent2");
        UmlClass c1p1 = nomContext.getUmlClass("Child1_Parent1");
        UmlClass c2p1 = nomContext.getUmlClass("Child2_Parent1");
        UmlClass c3p1 = nomContext.getUmlClass("Child3_Parent1");
        UmlClass c1p2 = nomContext.getUmlClass("Child1_Parent2");
        UmlClass c2p2 = nomContext.getUmlClass("Child2_Parent2");
        UmlClass c3p2 = nomContext.getUmlClass("Child3_Parent2");
        UmlClass grc1 = nomContext.getUmlClass("GC1_Child1_Parent1");
        UmlClass grc2 = nomContext.getUmlClass("GC2_Child1_Parent1");


        assertEquals(2.00, parent1.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(4.00, parent2.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(3.00, c1p1.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(3.00, c2p1.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(2.00, c3p1.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(5.00, c1p2.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(4.00, c2p2.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(4.00, c3p2.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(3.00, grc1.getMetricValue(NOM), Double.MIN_VALUE);
        assertEquals(4.00, grc2.getMetricValue(NOM), Double.MIN_VALUE);

    }

    @Test
    public void calculateNOA() throws IOException, IllegalAccessException{

        assertEquals(1.0, sParent.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(2.0, sChild.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(2.0, sOther.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(0.0, sLast.getMetricValue(NOA), Double.MIN_VALUE);

        AppController app = new AppController();

        String file = app.openUcdFile(noa_path);
        UmlContext nomContext = app.parseUcdFile(file);
        nomContext.calculateMetrics();

        UmlClass parent1 = nomContext.getUmlClass("Parent1");
        UmlClass parent2 = nomContext.getUmlClass("Parent2");
        UmlClass c1p1 = nomContext.getUmlClass("Child1_Parent1");
        UmlClass c2p1 = nomContext.getUmlClass("Child2_Parent1");
        UmlClass c1p2 = nomContext.getUmlClass("Child1_Parent2");
        UmlClass grc1_1 = nomContext.getUmlClass("GC1_Child1_Parent1");
        UmlClass grc2_1 = nomContext.getUmlClass("GC2_Child1_Parent1");
        UmlClass grc1_2 = nomContext.getUmlClass("GC1_Child1_Parent2");
        UmlClass grc2_2 = nomContext.getUmlClass("GC2_Child1_Parent2");

        assertEquals(2.00, parent1.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(0.00, parent2.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(3.00, c1p1.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(1.00, c1p2.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(2.00, c2p1.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(4.00, grc1_1.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(2.00, grc1_2.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(3.00, grc2_1.getMetricValue(NOA), Double.MIN_VALUE);
        assertEquals(1.00, grc2_2.getMetricValue(NOA), Double.MIN_VALUE);

    }


    @Test
    public void calculateITC() throws IOException, IllegalAccessException {

        assertEquals(1, sParent.getMetricValue(ITC), Double.MIN_VALUE);
        assertEquals(1, sChild.getMetricValue(ITC), Double.MIN_VALUE);
        assertEquals(0, sOther.getMetricValue(ITC), Double.MIN_VALUE);
        assertEquals(1, sLast.getMetricValue(ITC), Double.MIN_VALUE);

        AppController app = new AppController();

        String file = app.openUcdFile(itc_path);
        UmlContext itcContext = app.parseUcdFile(file);
        itcContext.calculateMetrics();

        UmlClass c1 = itcContext.getUmlClass("C1");
        UmlClass c2 = itcContext.getUmlClass("C2");
        UmlClass c3 = itcContext.getUmlClass("C3");
        UmlClass c4 = itcContext.getUmlClass("C4");
        UmlClass c5 = itcContext.getUmlClass("C5");

        assertEquals(0.00, c1.getMetricValue(ITC), Double.MIN_VALUE);
        assertEquals(0.00, c2.getMetricValue(ITC), Double.MIN_VALUE);
        assertEquals(1.00, c3.getMetricValue(ITC), Double.MIN_VALUE);
        assertEquals(2.00, c4.getMetricValue(ITC), Double.MIN_VALUE);
        assertEquals(4.00, c5.getMetricValue(ITC), Double.MIN_VALUE);


    }

    @Test
    public void calculateETC() throws IOException, IllegalAccessException {
        //todo
       assertEquals(0, sParent.getMetricValue(ETC), Double.MIN_VALUE);
       assertEquals(2, sChild.getMetricValue(ETC), Double.MIN_VALUE);
       assertEquals(1, sOther.getMetricValue(ETC), Double.MIN_VALUE);
       assertEquals(0, sLast.getMetricValue(ETC), Double.MIN_VALUE);

        AppController app = new AppController();

        // on utilice le même fichier que ITC pour tester
        String file = app.openUcdFile(itc_path);
        UmlContext etcContext = app.parseUcdFile(file);
        etcContext.calculateMetrics();

        UmlClass c1 = etcContext.getUmlClass("C1");
        UmlClass c2 = etcContext.getUmlClass("C2");
        UmlClass c3 = etcContext.getUmlClass("C3");
        UmlClass c4 = etcContext.getUmlClass("C4");
        UmlClass c5 = etcContext.getUmlClass("C5");

        assertEquals(3.00, c1.getMetricValue(ETC), Double.MIN_VALUE);
        assertEquals(2.00, c2.getMetricValue(ETC), Double.MIN_VALUE);
        assertEquals(1.00, c3.getMetricValue(ETC), Double.MIN_VALUE);
        assertEquals(1.00, c4.getMetricValue(ETC), Double.MIN_VALUE);
        assertEquals(0.00, c5.getMetricValue(ETC), Double.MIN_VALUE);

    }

    @Test
    public void calculateCAC() throws IOException, IllegalAccessException {

        assertEquals(0, sParent.getMetricValue(CAC), Double.MIN_VALUE);
        assertEquals(0, sChild.getMetricValue(CAC), Double.MIN_VALUE);
        assertEquals(0, sOther.getMetricValue(CAC), Double.MIN_VALUE);
        assertEquals(0, sLast.getMetricValue(CAC), Double.MIN_VALUE);

        AppController app = new AppController();

        String file = app.openUcdFile(cac_path);
        UmlContext cacContext = app.parseUcdFile(file);

        cacContext.calculateMetrics();
        // cacContext.logMetrics();

        UmlClass c1 = cacContext.getUmlClass("C1");
        UmlClass c2 = cacContext.getUmlClass("C2");
        UmlClass c3 = cacContext.getUmlClass("C3");
        UmlClass c4 = cacContext.getUmlClass("C4");
        UmlClass c5 = cacContext.getUmlClass("C5");

        assertEquals(4.00, c1.getMetricValue(CAC), Double.MIN_VALUE);
        assertEquals(4.00, c2.getMetricValue(CAC), Double.MIN_VALUE);
        assertEquals(3.00, c3.getMetricValue(CAC), Double.MIN_VALUE);
        assertEquals(1.00, c4.getMetricValue(CAC), Double.MIN_VALUE);
        assertEquals(0.00, c5.getMetricValue(CAC), Double.MIN_VALUE);

    }

    @Test
    public void calculateDIT() throws IOException, IllegalAccessException {

        /* aucun héritage multiple */

        assertEquals(0, sParent.getMetricValue(DIT), Double.MIN_VALUE);
        assertEquals(1, sChild.getMetricValue(DIT), Double.MIN_VALUE);
        assertEquals(0, sOther.getMetricValue(DIT), Double.MIN_VALUE);
        assertEquals(0, sLast.getMetricValue(DIT), Double.MIN_VALUE);

        AppController app = new AppController();

        String file = app.openUcdFile(dit_path);
        UmlContext ditContext = app.parseUcdFile(file);

        ditContext.calculateMetrics();

        UmlClass nuthin = ditContext.getUmlClass("NoChildNoParent");
        UmlClass c1 = ditContext.getUmlClass("C1");
        UmlClass c2 = ditContext.getUmlClass("C2");
        UmlClass c3 = ditContext.getUmlClass("C3");
        UmlClass c4 = ditContext.getUmlClass("C4");
        UmlClass c5 = ditContext.getUmlClass("C5");
        UmlClass c6 = ditContext.getUmlClass("C6");
        UmlClass c7 = ditContext.getUmlClass("C7");
        UmlClass c8 = ditContext.getUmlClass("C8");

        assertEquals(0, nuthin.getMetricValue(DIT) , Double.MIN_VALUE);
        assertEquals(0, c1.getMetricValue(DIT) , Double.MIN_VALUE);
        assertEquals(1, c2.getMetricValue(DIT), Double.MIN_VALUE);
        assertEquals(1, c3.getMetricValue(DIT), Double.MIN_VALUE);
        assertEquals(1, c4.getMetricValue(DIT), Double.MIN_VALUE);
        assertEquals(2, c5.getMetricValue(DIT), Double.MIN_VALUE);
        assertEquals(3, c6.getMetricValue(DIT), Double.MIN_VALUE);
        assertEquals(4, c7.getMetricValue(DIT), Double.MIN_VALUE);
        assertEquals(2, c8.getMetricValue(DIT), Double.MIN_VALUE);

    }

    @Test
    public void calculateCLD() throws IOException, IllegalAccessException{

        assertEquals(1, sParent.getMetricValue(CLD), Double.MIN_VALUE);
        assertEquals(0, sChild.getMetricValue(CLD), Double.MIN_VALUE);
        assertEquals(0, sOther.getMetricValue(CLD), Double.MIN_VALUE);
        assertEquals(0, sLast.getMetricValue(CLD), Double.MIN_VALUE);


        AppController app = new AppController();

        // on utilise le même fichier pour DIT et CLD
        String file = app.openUcdFile(dit_path);
        UmlContext cldContext = app.parseUcdFile(file);

        cldContext.calculateMetrics();

        UmlClass nuthin = cldContext.getUmlClass("NoChildNoParent");
        UmlClass c1 = cldContext.getUmlClass("C1");
        UmlClass c2 = cldContext.getUmlClass("C2");
        UmlClass c3 = cldContext.getUmlClass("C3");
        UmlClass c4 = cldContext.getUmlClass("C4");
        UmlClass c5 = cldContext.getUmlClass("C5");
        UmlClass c6 = cldContext.getUmlClass("C6");
        UmlClass c7 = cldContext.getUmlClass("C7");
        UmlClass c8 = cldContext.getUmlClass("C8");

        assertEquals(0, nuthin.getMetricValue(CLD) , Double.MIN_VALUE);
        assertEquals(4, c1.getMetricValue(CLD) , Double.MIN_VALUE);
        assertEquals(0, c2.getMetricValue(CLD), Double.MIN_VALUE);
        assertEquals(1, c3.getMetricValue(CLD), Double.MIN_VALUE);
        assertEquals(3, c4.getMetricValue(CLD), Double.MIN_VALUE);
        assertEquals(2, c5.getMetricValue(CLD), Double.MIN_VALUE);
        assertEquals(1, c6.getMetricValue(CLD), Double.MIN_VALUE);
        assertEquals(0, c7.getMetricValue(CLD), Double.MIN_VALUE);
        assertEquals(0, c8.getMetricValue(CLD), Double.MIN_VALUE);

    }

    @Test
    public void calculateNOC() throws IOException, IllegalAccessException {
        //todo
        assertEquals(1, sParent.getMetricValue(NOC), Double.MIN_VALUE);
        assertEquals(0, sChild.getMetricValue(NOC), Double.MIN_VALUE);
        assertEquals(0, sOther.getMetricValue(NOC), Double.MIN_VALUE);
        assertEquals(0, sLast.getMetricValue(NOC), Double.MIN_VALUE);

        AppController app = new AppController();

        // on utilise le même fichier pour DIT et NOC
        String file = app.openUcdFile(dit_path);
        UmlContext nocContext = app.parseUcdFile(file);

        nocContext.calculateMetrics();

        UmlClass nuthin = nocContext.getUmlClass("NoChildNoParent");
        UmlClass c1 = nocContext.getUmlClass("C1");
        UmlClass c2 = nocContext.getUmlClass("C2");
        UmlClass c3 = nocContext.getUmlClass("C3");
        UmlClass c4 = nocContext.getUmlClass("C4");
        UmlClass c5 = nocContext.getUmlClass("C5");
        UmlClass c6 = nocContext.getUmlClass("C6");
        UmlClass c7 = nocContext.getUmlClass("C7");
        UmlClass c8 = nocContext.getUmlClass("C8");

        assertEquals(0, nuthin.getMetricValue(NOC) , Double.MIN_VALUE);
        assertEquals(3, c1.getMetricValue(NOC) , Double.MIN_VALUE);
        assertEquals(0, c2.getMetricValue(NOC), Double.MIN_VALUE);
        assertEquals(1, c3.getMetricValue(NOC), Double.MIN_VALUE);
        assertEquals(1, c4.getMetricValue(NOC), Double.MIN_VALUE);
        assertEquals(1, c5.getMetricValue(NOC), Double.MIN_VALUE);
        assertEquals(1, c6.getMetricValue(NOC), Double.MIN_VALUE);
        assertEquals(0, c7.getMetricValue(NOC), Double.MIN_VALUE);
        assertEquals(0, c8.getMetricValue(NOC), Double.MIN_VALUE);
    }

    @Test
    public void calculateNOD() throws IOException, IllegalAccessException{
        //todo
        assertEquals(1, sParent.getMetricValue(NOD), Double.MIN_VALUE);
        assertEquals(0, sChild.getMetricValue(NOD), Double.MIN_VALUE);
        assertEquals(0, sOther.getMetricValue(NOD), Double.MIN_VALUE);
        assertEquals(0, sLast.getMetricValue(NOD), Double.MIN_VALUE);

        AppController app = new AppController();

        // on utilise le même fichier pour DIT et NOD
        String file = app.openUcdFile(dit_path);
        UmlContext nocContext = app.parseUcdFile(file);

        nocContext.calculateMetrics();

        UmlClass nuthin = nocContext.getUmlClass("NoChildNoParent");
        UmlClass c1 = nocContext.getUmlClass("C1");
        UmlClass c2 = nocContext.getUmlClass("C2");
        UmlClass c3 = nocContext.getUmlClass("C3");
        UmlClass c4 = nocContext.getUmlClass("C4");
        UmlClass c5 = nocContext.getUmlClass("C5");
        UmlClass c6 = nocContext.getUmlClass("C6");
        UmlClass c7 = nocContext.getUmlClass("C7");
        UmlClass c8 = nocContext.getUmlClass("C8");

        assertEquals(0, nuthin.getMetricValue(NOD) , Double.MIN_VALUE);
        assertEquals(7, c1.getMetricValue(NOD) , Double.MIN_VALUE);
        assertEquals(0, c2.getMetricValue(NOD), Double.MIN_VALUE);
        assertEquals(1, c3.getMetricValue(NOD), Double.MIN_VALUE);
        assertEquals(3, c4.getMetricValue(NOD), Double.MIN_VALUE);
        assertEquals(2, c5.getMetricValue(NOD), Double.MIN_VALUE);
        assertEquals(1, c6.getMetricValue(NOD), Double.MIN_VALUE);
        assertEquals(0, c7.getMetricValue(NOD), Double.MIN_VALUE);
        assertEquals(0, c8.getMetricValue(NOD), Double.MIN_VALUE);

    }

}