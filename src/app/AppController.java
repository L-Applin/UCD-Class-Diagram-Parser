package app;

import parsing.UcdFileReader;
import screenDisplay.MainDisplay;
import syntaxTree.SyntaxTree;
import syntaxTree.UmlContext;
import syntaxTree.exceptions.UcdParsingException;
import token.UmlToken;
import token.visitor.UmlChildClassVisitor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppController {

    /**
     * Reads and cleans up the .ucd file
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
        ctx.setTree(tree);
        return ctx;

    }

    /**
     * Called to start parsing the ucd file and update the display
     *
     * @param screen the MainDisplay of the application
     * @param ucdFile the File reference to the .ucd file.
     */
    public void lauchUcdActivity(MainDisplay screen, File ucdFile) {

    	try {
            String stringFile = openUcdFile(ucdFile.getAbsolutePath());
            UmlContext ctx = parseUcdFile(stringFile);
            List<UmlToken> tokens = new ArrayList<>(ctx.getClasses().values());
            tokens.get(0).accept(new UmlChildClassVisitor());
            screen.setupUcdDisplay(ctx);
        } catch (IOException ioe){
            screen.errorScreen(ioe);
            ioe.printStackTrace();
        } catch (UcdParsingException ucde) {
            screen.errorScreen(ucde);
            ucde.printStackTrace();
        } catch (NullPointerException npe) {
            screen.errorScreen(npe);
            npe.printStackTrace();
        }

    }

}
