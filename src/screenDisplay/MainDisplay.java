package screenDisplay;

import app.AppController;
import app.Main;
import app.ShortcutController;
import app.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import app.FileController;
import screenDisplay.components.MyAlertDialog;
import screenDisplay.components.OverlayButton;
import screenDisplay.components.umlComponents.ClassListView;
import screenDisplay.components.umlComponents.MainCenterClassInfo;
import parsing.syntaxTree.exceptions.MalformedFileException;
import token.UmlClass;
import token.UmlContext;
import token.UmlToken;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainDisplay extends BorderPane {

    public static final String ATTRIBUTES_TITLE = "Attributs";
    public static final String OPERATIONS_TITLE = "Méthodes";
    public static final String SUBCLASS_TITLE = "Sous-classes";
    public static final String ASSO_INTRG_TITLE = "Associations/agrégations";
    public static final String DETAILS_TITLE = "Détails";
    public static final String METRIC_TITLE = "Métriques";

    private Stage primaryStage;
    public Stage getPrimaryStage() { return primaryStage; }

    private AppTheme appTheme;
    public AppTheme getAppTheme() { return appTheme; }

    private ClassListView classView;
    private MainCenterClassInfo centerView;

    private UmlClass currentlySelected = null;
    private boolean fileLoaded = false;
    public void setFileLoaded(boolean fileLoaded) { this.fileLoaded = fileLoaded; }

    private AppController controller = new AppController();
    public AppController getController() { return controller; }

    // used for controlling mouse drag of the window
    private static double xOffset = 0;
    private static double yOffset = 0;


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

        // MyTopBar topBar = new MyTopBar(appTheme, this);
        setTop(createTopBar());

        Node center = createCenterView();
        setCenter(center);

        Scene scene = new Scene(this, 1244, 768);
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
        dialog.make().show();
    }


    public void errorScreen(Exception e){
        MyAlertDialog dialog = new MyAlertDialog(e.getMessage(), appTheme);
        setOnMousePressed( mousePressedEvent -> {
            if (dialog != null && dialog.isShowing()){
                dialog.close();
            }
        });
        dialog.make().show();
    }


    public void resetLayout(){
        setCenter(null);
        setLeft(null);
        setBottom(null);
        setRight(null);
        setCenter(createCenterView());

    }

    public void updateClassSelected(UmlClass clazz){
        centerView = new MainCenterClassInfo(clazz, appTheme, this);
        setCenter(centerView.init());
        classView.forceClick(clazz.getName());
        currentlySelected = clazz;

    }

    public void updateTokenClicked(UmlToken token){
        centerView.resetButtons();
        centerView.updateDetails(token);
    }



    /* *************
     *
     *      Method for creating the basic displays

     ************* */

    private BorderPane createTopBar(){

        BorderPane topBar = new BorderPane();

        Insets default_bar_margin = new Insets(8);
        String trash_url = "garbage.png";
        String click_me = "Charger fichier";
        String exit = "EXIT";
        String csvFileButtonTitle = "Générer .csv";

        ImageView trashIcon = new ImageView(trash_url);
        Background background = appTheme.getSecondaryDarkBackground();
        topBar.setBackground(background);
        topBar.setPadding(new Insets(0,0,0,0));
        topBar.setBottom(new Separator());


        OverlayButton mImport = new OverlayButton(this, appTheme, click_me);
        OverlayButton mTrash = new OverlayButton(this, appTheme, trashIcon, 20);
        OverlayButton mExit = new OverlayButton(this, appTheme, exit);
        OverlayButton mCsvMetricFile = new OverlayButton(this, appTheme, csvFileButtonTitle);

        mImport.setStyle(OverlayButton.ThemeStyle.ROUNDED);
        mTrash.setStyle(OverlayButton.ThemeStyle.ROUNDED);
        mExit.setStyle(OverlayButton.ThemeStyle.ROUNDED);
        mCsvMetricFile.setStyle(OverlayButton.ThemeStyle.ROUNDED);

        HBox rightContainer = new HBox();
        HBox leftContainer = new HBox();
        rightContainer.getChildren().addAll(mCsvMetricFile, mTrash, mExit);
        leftContainer.getChildren().addAll(mImport);

        BorderPane.setMargin(rightContainer, default_bar_margin);
        BorderPane.setMargin(leftContainer, default_bar_margin);

        HBox.setMargin(mImport, OverlayButton.default_button_seperator_margin);
        HBox.setMargin(mTrash, OverlayButton.default_button_seperator_margin);
        HBox.setMargin(mExit, OverlayButton.default_button_seperator_margin);
        HBox.setMargin(mCsvMetricFile, OverlayButton.default_button_seperator_margin);

        topBar.setRight(rightContainer);
        topBar.setLeft(leftContainer);

        // drag screen
        setOnMousePressed( event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });

        setOnMouseDragged( event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });


        mImport.setOnMouseClicked(event ->
                new FileController().openUcdFileFromSystemExplorer(this)
        );

        mTrash.setOnMouseClicked( event -> {
            resetLayout();
            fileLoaded = false;
        });

        mExit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        mCsvMetricFile.setOnMouseClicked(event -> {
            FileController fc = new FileController();
            if (!fileLoaded){
                MyAlertDialog alertDialog= new MyAlertDialog("Un fichier doit être ouvert", appTheme);
                alertDialog.make().show();
            } else {
                try {
                    File savedFile =
                            fc.createCsvFile(controller.getCtx().getModelId(), controller.getCtx().getClasses().values());
                    new MyAlertDialog("Fichier enregistré : " + savedFile.getAbsolutePath(), appTheme).make().show();

                } catch (IOException ioe){
                    ioe.printStackTrace();
                    new MyAlertDialog("Une erreur s'est produite : " + ioe.getMessage(), appTheme).make().show();
                }
            }

        });

        return topBar;
    }


    private VBox createCenterView(){

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
                    dialog.make().show();
                    setOnMousePressed( mousePressedEvent -> {
                        if (dialog != null && dialog.isShowing()){
                            dialog.close();
                        }
                    });

                } else {

                    File file = files.get(0);
                    controller.lauchUcdActivity(this, file);
                    System.out.println("LOADED FILE");
                    success = true;

                }

            }
            event.setDropCompleted(success);

            event.consume();
        });


        return center;
    }

}
