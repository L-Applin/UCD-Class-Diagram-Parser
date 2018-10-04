package app;

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
        if (doc.equals("")) {
            throw new UcdParsingException("Cannot read empty files"){ };

        }
        UmlContext ctx = new UmlContext();
        SyntaxTree tree = (SyntaxTree) new SyntaxTree().tokenize(ctx, doc);
        ctx.setTree(tree);
        return ctx;

    }

    /**
     * Called to start parsing the ucd file and update the display
     *
     * @param screen the MainDisplay of the application
     * @param ucdFile the FIle reference to the .ucd file.
     */
    public void lauchUcdActivity(MainDisplay screen, File ucdFile) {

        try {
            String stringFile = openUcdFile(ucdFile.getAbsolutePath());
            UmlContext ctx = parseUcdFile(stringFile);
            screen.setupUcdDisplay(ctx);
        } catch (IOException ioe){
            screen.errorScreen(ioe);
        } catch (UcdParsingException ucde) {
            screen.errorScreen(ucde);
        } catch (NullPointerException npe) {
            screen.errorScreen(npe);
        }

    }

}
