import app.AppController;
import app.Main;
import org.junit.Test;
import parsing.syntaxTree.exceptions.MalformedFileException;
import token.UmlContext;

import java.io.IOException;

import static token.MetricCalculatorTest.path_to_medium_test_file;
import static token.MetricCalculatorTest.path_to_res;
import static token.MetricCalculatorTest.path_to_small_test_file;

public class ParsingTest {

    @Test
    public void testParsing() throws IOException, IllegalAccessException {

        AppController app = new AppController();
        app.parseUcdFile(app.openUcdFile(path_to_small_test_file));
        app.parseUcdFile(app.openUcdFile(path_to_medium_test_file));

        // assertion for classes
    }

    @Test(expected = MalformedFileException.class)
    public void emptyFile() throws IOException, IllegalAccessException {
        AppController app = new AppController();
        app.parseUcdFile(app.openUcdFile(path_to_res + "/res/testFiles/empty.ucd"));
    }


    @Test(expected = MalformedFileException.class)
    public void sameClass() throws IOException, IllegalAccessException {
        AppController app = new AppController();
        app.parseUcdFile(app.openUcdFile(path_to_res + "/res/testFiles/sameClass.ucd"));
    }


    @Test(expected = MalformedFileException.class)
    public void sameClassBig() throws IOException, IllegalAccessException {
        AppController app = new AppController();
        app.parseUcdFile(app.openUcdFile(path_to_res + "/res/testFiles/sameClassBig.ucd"));
    }

}
