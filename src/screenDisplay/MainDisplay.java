package screenDisplay;

import app.ShortcutController;
import app.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import parsing.FileController;
import app.AppController;
import app.Main;
import screenDisplay.components.umlComponents.ClassListView;
import screenDisplay.components.umlComponents.MainCenterClassInfo;
import screenDisplay.components.MyAlertDialog;
import screenDisplay.components.umlComponents.MyTopBar;
import syntaxTree.exceptions.MalformedFileException;
import token.UmlContext;
import token.UmlClass;
import token.UmlMetric;
import token.UmlToken;

import java.io.File;
import java.util.List;

public class MainDisplay extends BorderPane {

    public static final String ATTRIBUTES_TITLE = "Attributs";
    public static final String OPERATIONS_TITLE = "Méthodes";
    public static final String SUBCLASS_TITLE = "Sous-classes";
    public static final String ASSO_INTRG_TITLE = "Associations/agrégations";
    public static final String DETAILS_TITLE = "Détails";

    private Stage primaryStage;
    public Stage getPrimaryStage() { return primaryStage; }

    private AppTheme appTheme;
    public AppTheme getAppTheme() { return appTheme; }

    private ClassListView classView;
    private MainCenterClassInfo centerView;

    public MainDisplay(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.appTheme = new AppTheme();
    }

    /**
     * Setup main screen before file is imported.
     */
    public void init(){

        primaryStage.initStyle(StageStyle.UNDECORATED);

        setBackground(appTheme.getPrimaryDarkBackground());

        MyTopBar topBar = new MyTopBar(appTheme, this);
        setTop(topBar);

        Node center = getOnOpenCenterView();
        setCenter(center);

        Scene scene = new Scene(this, 1024, 768);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("stylesheet.css");

        primaryStage.setTitle(Main.APP_NAME);
        primaryStage.setScene(scene);

        // setup shortcut
        scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (ShortcutController.META_0.match(event)) {
                new FileController().openUcdFileFromSystemExplorer(this);
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (ShortcutController.ESC.match(event)) {
                System.exit(1);
            }
        });

        primaryStage.show();

    }

    /**
     * Setup main display once the .ucd file is loaded
     * @param context the UmlContext of the parsed file.
     */
    public void setupUcdDisplay(UmlContext context){

        classView = new ClassListView(context, this);
        setLeft(classView.init());

        // force select the first element, if it exist
        if (classView.getBtnList().size() > 0){
            classView.getBtnList().get(0).forceClick();
        }

    }



    public void errorScreen(MalformedFileException ucde){
        MyAlertDialog dialog = new MyAlertDialog(ucde.getMessage(), appTheme);
        setOnMousePressed( mousePressedEvent -> {
            if (dialog != null && dialog.isShowing()){
                dialog.close();
            }
        });
        dialog.make(primaryStage).show();
    }


    public void errorScreen(Exception e){
        MyAlertDialog dialog = new MyAlertDialog(e.getMessage(), appTheme);
        setOnMousePressed( mousePressedEvent -> {
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
                setBackground(appTheme.primaryRadialGradientBackground());
            }
            event.consume();
        });

        center.setOnDragExited( event -> setBackground(appTheme.getPrimaryDarkBackground()));

        center.setOnDragDropped( event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                List<File> files = db.getFiles();
                if (files.size() > 1) {

                    MyAlertDialog dialog = new MyAlertDialog("Only one file is supported", appTheme);
                    dialog.make(primaryStage).show();
                    setOnMousePressed( mousePressedEvent -> {
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
            event.setDropCompleted(success);

            event.consume();
        });

        return center;
    }

    public void resetLayout(){
        setCenter(null);
        setLeft(null);
        setBottom(null);
        setRight(null);
        setCenter(getOnOpenCenterView());

    }

    public void updateClassSelected(UmlClass clazz){
        centerView = new MainCenterClassInfo(clazz, appTheme, this);
        setCenter(centerView.init());
        classView.forceClick(clazz.getName());

    }

    public void updateTokenClicked(UmlToken token){
        centerView.resetButtons();
        centerView.updateDetails(token);
    }

    public void updateMetricClicked(UmlMetric metric){

    }

}
