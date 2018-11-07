package app;

import com.oracle.tools.packager.Platform;
import javafx.application.Application;
import javafx.stage.Stage;
import screenDisplay.ScreenController;

import java.io.IOException;

public class Main extends Application {

    public static final String APP_NAME = "ucd file reader 4000";
    public static final String SELF_MODEL_NAME = "Parser";
    public static final boolean CREATE_SELF_UCD = true;
    public static final Platform os = Platform.getPlatform();


    @Override
    public void start(Stage primaryStage) throws IOException {



        final ScreenController screen = new ScreenController(primaryStage, getParameters());
        screen.setupMainDisplay();
        if (JavaAnalyzer.CREATE_UCD_FILE_AT_LAUNCH) {
            FileController.createUcdFileFromJavaClass(System.getProperty("user.dir") + "/res/");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
