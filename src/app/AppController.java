package app;

import parsing.UcdFileReader;
import screenDisplay.MainDisplay;
import syntaxTree.SyntaxTree;
import syntaxTree.exceptions.MalformedFileException;
import token.UmlContext;
import token.visitor.InfoDisplayVisitor;
import token.visitor.SuperClassAssignationVisitor;

import java.io.File;
import java.io.IOException;

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
        if (doc.equals("")) {
            throw new MalformedFileException("Cannot read empty files");
        }
        UmlContext ctx = new UmlContext();
        SyntaxTree tree = (SyntaxTree) new SyntaxTree(ctx).tokenize(ctx, doc);
        ctx.setTree(tree);
        ctx.visitClasses(new SuperClassAssignationVisitor());
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
            ctx.visitClasses(new InfoDisplayVisitor());
            screen.setupUcdDisplay(ctx);
        } catch (MalformedFileException ucde) {
            screen.errorScreen(ucde);
            ucde.printStackTrace();
        } catch (Exception e){
            screen.errorScreen(e);
            e.printStackTrace();
        }

    }

}
