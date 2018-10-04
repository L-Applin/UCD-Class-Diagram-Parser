package app;

import com.oracle.tools.packager.Platform;
import javafx.application.Application;
import javafx.stage.Stage;
import screenDisplay.ScreenController;

public class Main extends Application {

    public static final String APP_NAME = "ucd file reader 4000";
    public static final Platform os = Platform.getPlatform();

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(os);
        final ScreenController screen = new ScreenController(primaryStage, getParameters());
        screen.setupMainDisplay();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
