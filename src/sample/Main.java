package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        AppController ctrl = new AppController();
        Parameters params = getParameters();
        System.out.println(params.getRaw().get(0));
        String doc = ctrl.openUCDFile(params.getRaw().get(0));
        System.out.println(doc);

    }


    public static void main(String[] args) {
        launch(args);
    }

}
