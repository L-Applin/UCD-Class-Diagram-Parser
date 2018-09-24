package screenDisplay;

import app.MyApp;
import app.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.AppController;
import syntxTree.SyntaxTree;
import syntxTree.UmlContext;
import syntxTree.exceptions.UcdParsingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MainDisplay {

    private Stage primaryStage;
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
        MyTopBar topBar = new MyTopBar(appTheme, primaryStage);

        rootLayout.setTop(topBar);

        ImageView importIcon = new ImageView("importWhite.png");
        VBox center = new VBox();
        center.setPadding(new Insets(100, 100, 100, 100));
        center.setSpacing(25);
        center.setAlignment(Pos.CENTER);

        Text centerText = new Text("Drag file here");
        centerText.setFont(Font.font("Verdana", 20));
        centerText.setFill(Color.WHITE);
        Group g = new Group();
        VBox paths = new VBox();
        paths.setPadding(new Insets(0, 100, 100, 100));
        paths.setSpacing(25);
        paths.setAlignment(Pos.CENTER);

        g.getChildren().add(paths);
        center.getChildren().addAll(importIcon, centerText, paths);

        topBar.setOnTrashClickedListener( event -> {
            paths.getChildren().clear();
        });


        // thx Stack overflow : https://stackoverflow.com/questions/32534113/javafx-drag-and-drop-a-file-into-a-program
        center.setOnDragOver(event -> {
            if (event.getGestureSource() != center
                    && event.getDragboard().hasFiles()) {
                /* allow for both copying and moving, whatever user chooses */
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


        rootLayout.setCenter(center);
        Scene scene = new Scene(rootLayout, 1024, 768);
        primaryStage.setTitle(MyApp.APP_NAME);
        primaryStage.setScene(scene);

        primaryStage.show();

    }


    public void setupUcdDisplay(UmlContext context){

        System.out.println(context.toString());
        ScrollPane classContainer = new ScrollPane();
        classContainer.setBackground(appTheme.getsecondaryDarkBackground());
        VBox vb = new VBox();
        vb.setBackground(appTheme.getsecondaryDarkBackground());
        vb.setPadding(new Insets(0));

        context.getClasses().entrySet().forEach( entry -> {
            HBox hb = new HBox();
            hb.setPadding(new Insets(10));
            Text text = new Text(entry.getKey());
            text.setFont(appTheme.getClassFont());
            text.setFill(appTheme.getClassFontColor());
            hb.getChildren().add(text);
            vb.getChildren().add(hb);
        });
        classContainer.setContent(vb);
        rootLayout.setLeft(classContainer);

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


}
