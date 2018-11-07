import app.AppController;
import org.junit.Test;
import token.UmlContext;

import java.io.IOException;

public class ParsingTest {

    private static String path_to_small_test_file = "/Users/Applin/Documents/dev/parser-tp1/res/testFiles/test_small.ucd";

    @Test
    public void testParsing() throws IOException, IllegalAccessException {

        AppController app = new AppController();
        UmlContext testContext = app.parseUcdFile(app.openUcdFile(path_to_small_test_file));
        testContext.calculateMetrics();
        testContext.logMetrics();

    }

}
