package app;

import javafx.application.Application;
import javafx.stage.Stage;
import screenDisplay.ScreenController;
import syntxTree.SyntaxTree;
import syntxTree.UmlContext;
import utils.Utils.*;

public class Main extends Application {

    public static final String APP_NAME = "ucd file reader 4000";

    @Override
    public void start(Stage primaryStage) throws Exception {

        final ScreenController screen = new ScreenController(primaryStage, getParameters());
        screen.setupMainDisplay();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
