package sample;

import parsing.UcdFileReader;
import screenDisplay.MainDisplay;
import syntxTree.SyntaxTree;
import syntxTree.UmlContext;
import syntxTree.exceptions.UcdParsingException;

import java.io.File;
import java.io.IOException;

public class AppController {

    /**
     * Reads and cleand up the .ucd file
     * @param path path of the file to open, read and clean
     * @return
     * @throws IOException
     */
    public String openUcdFile(String path) throws IOException {
        return new UcdFileReader(path).readAndCleanFile();
    }

    /**
     *
     * @param doc
     * @return
     */
    public UmlContext parseUcdFile(String doc){
        UmlContext ctx = new UmlContext();
        SyntaxTree tree = (SyntaxTree) new SyntaxTree(ctx).tokenize(ctx, doc);
        return ctx;

    }

    public void lauchUcdActivity(MainDisplay screen, File ucdFile) {

        try {
            String stringFile = openUcdFile(ucdFile.getAbsolutePath());
            UmlContext ctx = parseUcdFile(stringFile);
            screen.setupUcdDisplay(ctx);
        } catch (IOException ioe){
            screen.errorScreen(ioe);
        } catch (UcdParsingException ucde) {
            screen.errorScreen(ucde);
        }

    }

}
