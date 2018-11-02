package deprecated;

import screenDisplay.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import app.FileController;
import screenDisplay.MainDisplay;
import screenDisplay.components.OverlayButton;

@Deprecated
public class MyTopBar extends BorderPane {

    private static final Insets default_bar_margin = new Insets(8);
    private static final String trash_url = "garbage.png";
    private static final String click_me = "Charger fichier";
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

        background = appTheme.getSecondaryDarkBackground();
        setBackground(background);
        setPadding(new Insets(0,0,0,0));
        setBottom(new Separator());

        trashIcon = new ImageView(trash_url);

        OverlayButton mImport = new OverlayButton(this, appTheme, click_me);
        OverlayButton mTrash = new OverlayButton(this, appTheme, trashIcon, 20);
        OverlayButton mExit = new OverlayButton(this, appTheme, exit);

        mImport.setStyle(OverlayButton.ThemeStyle.ROUNDED);
        mTrash.setStyle(OverlayButton.ThemeStyle.ROUNDED);
        mExit.setStyle(OverlayButton.ThemeStyle.ROUNDED);

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
                new FileController().openUcdFileFromSystemExplorer(mainDisplay)
        );

        mTrash.setOnMouseClicked( event -> {
            mainDisplay.resetLayout();
        });

        mExit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        // setEffect(appTheme.elevation(appTheme.getContrastDark()));
    }



}
