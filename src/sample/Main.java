package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import parsing.UcdParser;
import syntxTree.SyntaxTree;
import syntxTree.UmlContext;
import utils.Utils.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        AppController ctrl = new AppController();
        Parameters params = getParameters();
        String doc = ctrl.openUCDFile(params.getRaw().get(0));

        String test= "     test test   ";

        final UmlContext ctx = new UmlContext();
        final SyntaxTree tree = (SyntaxTree) new SyntaxTree().tokenize(ctx, doc);

        // System.out.println(tree);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
