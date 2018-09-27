package screenDisplay;

import javafx.application.Application;
import javafx.stage.Stage;

public class ScreenController {

    private Stage primaryStage;
    private Application.Parameters parameters;
    private MainDisplay screen;

    public ScreenController(Stage primaryStage, Application.Parameters parameters) {
        this.primaryStage = primaryStage;
        this.parameters = parameters;
    }

    public void setupMainDisplay(){
        screen = new MainDisplay(primaryStage);
        screen.init();
    }

}
