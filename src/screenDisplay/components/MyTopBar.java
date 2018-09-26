package screenDisplay.components;

import app.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import parsing.FileController;
import sample.AppController;
import screenDisplay.MainDisplay;

import java.io.File;

public class MyTopBar extends BorderPane {

    private static final Insets default_bar_margin = new Insets(8);
    private static final String trash_url = "garbage.png";
    private static final String click_me = "Click me to import file manually";
    private static final String exit = "EXIT";

    private AppTheme appTheme;
    private ImageView trashIcon;
    private MainDisplay mainDisplay;

    private Background background;

    private static double xOffset = 0;
    private static double yOffset = 0;

    public MyTopBar(AppTheme appTheme, MainDisplay main) {
        this.appTheme = appTheme;

        this.mainDisplay = main;
        Stage primaryStage = main.getPrimaryStage();

        background = appTheme.getsecondaryDarkBackground();
        setBackground(background);
        setPadding(new Insets(0,0,0,0));
        setBottom(new Separator());


        trashIcon = new ImageView(trash_url);

        OverlayButton mImport = new OverlayButton(this, appTheme, click_me);
        OverlayButton mTrash = new OverlayButton(this, appTheme, trashIcon, 20);
        OverlayButton mExit = new OverlayButton(this, appTheme, exit);

        HBox rightContainer = new HBox();
        HBox leftContainer = new HBox();
        rightContainer.getChildren().addAll(mTrash, mExit);
        leftContainer.getChildren().addAll(mImport);

        setMargin(rightContainer, default_bar_margin);
        setMargin(leftContainer, default_bar_margin);

        HBox.setMargin(mImport, OverlayButton.default_button_seperator_margin);
        HBox.setMargin(mTrash, OverlayButton.default_button_seperator_margin);
        HBox.setMargin(mExit, OverlayButton.default_button_seperator_margin);

        setRight(rightContainer);
        setLeft(leftContainer);

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
                new FileController().openUcdFIleFromSystemExplorer(mainDisplay)
        );

        mTrash.setOnMouseClicked( event -> {
            mainDisplay.resetLayout();
        });

        mExit.setOnMouseClicked(event -> {
            System.exit(0);
        });



    }



}
