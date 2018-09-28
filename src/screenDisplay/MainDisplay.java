package screenDisplay;

import app.MyApp;
import app.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.AppController;
import screenDisplay.components.ClassListVIew;
import screenDisplay.components.MyAlertDialog;
import screenDisplay.components.MyTopBar;
import syntxTree.UmlContext;
import syntxTree.exceptions.UcdParsingException;
import token.UmlClass;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainDisplay {

    private Stage primaryStage;
    public Stage getPrimaryStage() { return primaryStage; }

    private AppTheme appTheme;
    private BorderPane rootLayout;

    public MainDisplay(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.appTheme = new AppTheme();
    }

    /**
     * Setup main screen
     */
    public void init(){

        primaryStage.initStyle(StageStyle.UNDECORATED);

        rootLayout = new BorderPane();

        rootLayout.setBackground(appTheme.getprimaryDarkBackground());
        rootLayout.setStyle("-fx-border-color:black;"); // TODO: change to theme color

        MyTopBar topBar = new MyTopBar(appTheme, this);
        rootLayout.setTop(topBar);

        Node center = getOnOpenCenterView();
        rootLayout.setCenter(center);

        // TODO: set dynamic width/height ?
        Scene scene = new Scene(rootLayout, 1024, 768);
        primaryStage.setTitle(MyApp.APP_NAME);
        primaryStage.setScene(scene);

        primaryStage.show();

    }


    public void setupUcdDisplay(UmlContext context){

        ClassListVIew classView = new ClassListVIew(context.getClasses(), appTheme, this);
        System.out.println(context.toString());
        rootLayout.setLeft(classView.init());

    }


    public void errorScreen(IOException ioe){
        MyAlertDialog dialog = new MyAlertDialog(ioe.getMessage(), appTheme);
        rootLayout.setOnMousePressed( mousePressedEvent -> {
            if (dialog != null && dialog.isShowing()){
                dialog.close();
            }
        });
        dialog.make(primaryStage).show();

    }

    public void errorScreen(UcdParsingException ucde){
        MyAlertDialog dialog = new MyAlertDialog(ucde.getMessage(), appTheme);
        rootLayout.setOnMousePressed( mousePressedEvent -> {
            if (dialog != null && dialog.isShowing()){
                dialog.close();
            }
        });
        dialog.make(primaryStage).show();
    }



    private Node getOnOpenCenterView(){

        ImageView importIcon = new ImageView("importWhite.png");
        VBox center = new VBox();
        center.setPadding(new Insets(100, 100, 100, 100));
        center.setSpacing(25);
        center.setAlignment(Pos.CENTER);

        Text centerText = new Text("Drag file here");
        centerText.setFont(Font.font("Verdana", 20));
        centerText.setFill(Color.WHITE);

        center.getChildren().addAll(importIcon, centerText);

        // thx Stack overflow : https://stackoverflow.com/questions/32534113/javafx-drag-and-drop-a-file-into-a-program
        center.setOnDragOver(event -> {
            if (event.getGestureSource() != center && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                rootLayout.setBackground(appTheme.primaryRadialGradientBackground());
            }
            event.consume();
        });

        center.setOnDragExited( event -> rootLayout.setBackground(appTheme.getprimaryDarkBackground()));

        center.setOnDragDropped( event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                List<File> files = db.getFiles();
                if (files.size() > 1) {

                    MyAlertDialog dialog = new MyAlertDialog("Only one file is supported", appTheme);
                    dialog.make(primaryStage).show();
                    rootLayout.setOnMousePressed( mousePressedEvent -> {
                        if (dialog != null && dialog.isShowing()){
                            dialog.close();
                        }
                    });

                } else {

                    File file = files.get(0);
                    AppController controller = new AppController();
                    controller.lauchUcdActivity(this, file);

                }

            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        });

        return center;
    }

    public void resetLayout(){
        rootLayout.setCenter(null);
        rootLayout.setLeft(null);
        rootLayout.setBottom(null);
        rootLayout.setRight(null);
        rootLayout.setCenter(getOnOpenCenterView());

    }

    public void updateClassSelected(UmlClass clazz){
        System.out.println("CLICKED ON " + clazz.toString());
    }
}
