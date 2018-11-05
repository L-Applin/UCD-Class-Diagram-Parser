package app;

import parsing.UcdFileReader;
import screenDisplay.MainDisplay;
import parsing.syntaxTree.SyntaxTree;
import parsing.syntaxTree.exceptions.MalformedFileException;
import token.UmlContext;
import token.visitor.UmlMetricVisitor;

import java.io.File;
import java.io.IOException;

public class AppController {

    private UmlContext ctx;
    public UmlContext getCtx() { return ctx; }

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
    public UmlContext parseUcdFile(String doc) throws IllegalAccessException {

        if (doc.trim().equals("")) {
            throw new MalformedFileException("Cannot read empty files");
        }

        UmlContext ctx = new UmlContext();
        SyntaxTree tree = new SyntaxTree(ctx);

        // begin the actuall parsing operation
        tree.tokenize(ctx, doc);

        // set parent (super) class for inheritence
        UmlMetricVisitor superClassVisitor = new UmlMetricVisitor();
        superClassVisitor.setClassVisitor(clazz ->
                clazz.getSubClasses().forEach((__, child) ->
                        child.setSuperClass(clazz)));
        ctx.visitClasses(superClassVisitor);

        ctx.setImmutable();

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
            ctx = parseUcdFile(stringFile);
            ctx.calculateMetrics();
            screen.setupUcdDisplay(ctx);
            screen.setFileLoaded(true);
        } catch (MalformedFileException ucde) {
            screen.errorScreen(ucde);
            // ucde.printStackTrace();
            // System.out.println(ucde.getTextCause());
        }catch (IllegalAccessException iae){
            screen.errorScreen("Le fichier ne peut être analysé qu'une seule fois");
        } catch (NullPointerException npe){
    	    screen.errorScreen(npe);
    	    npe.printStackTrace();
        } catch (Exception e){
            screen.errorScreen(e);
            e.printStackTrace();
        }

    }

}
