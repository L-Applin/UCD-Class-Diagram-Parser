package app;

import javafx.application.Application;
import javafx.stage.Stage;
import screenDisplay.ScreenController;

import java.io.IOException;

public class Main extends Application {

    public static final String APP_NAME = "ucd file reader 4000";

    /**
     * Le nom du MODEL pour lequel un fichier .csv avec plus d'informations sera créé
     */
    public static final String SELF_MODEL_NAME = "Parser";

    /**
     * Permet de créer automatiquement le .csv avec plus d'information que le .csv standard si
     * le fichier à analyser a comme nom de MOEL {@link Main#SELF_MODEL_NAME}
     */
    public static final boolean CREATE_SELF_UCD = false;

    public static final String USER_PATH = System.getProperty("user.dir");


    @Override
    public void start(Stage primaryStage) throws IOException {

        // Log.log(String.format("Project root path : %s", USER_PATH));

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
