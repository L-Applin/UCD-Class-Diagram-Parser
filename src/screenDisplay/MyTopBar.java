package screenDisplay;

import app.theme.AppTheme;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyTopBar extends BorderPane {

    public static final String trash_url = "garbage.png";
    public static final String click_me = "Click me to import file manually";

    private AppTheme appTheme;
    private ImageView trashIcon;
    private HBox trashContainer;
    private HBox clickContainer;
    private HBox exitCOntainer;
    private Text clickMeText;

    private Background background;

    private static double xOffset = 0;
    private static double yOffset = 0;

    public MyTopBar(AppTheme appTheme, Stage primaryStage) {

        this.appTheme = appTheme;

        background = appTheme.getsecondaryDarkBackground();
        setBackground(background);
        setPadding(appTheme.getMediumPadding());

        clickMeText = new Text(click_me);
        clickMeText.setFont(appTheme.getMediumFont());
        clickMeText.setFill(Color.WHITE);
        clickContainer = new HBox();
        clickContainer.setAlignment(Pos.CENTER);
        clickContainer.setPadding(new Insets(10));
        setMargin(clickContainer, new Insets(8));
        clickContainer.getChildren().add(clickMeText);
        setLeft(clickContainer);

        HBox rightContainer = new HBox();
        exitCOntainer = new HBox();
        Text exit = new Text("Quit program");
        exit.setFont(appTheme.getMediumFont());
        exit.setFill(Color.WHITE);

        trashIcon = new ImageView(trash_url);
        trashIcon.setFitHeight(24);
        trashIcon.setFitWidth(24);
        trashContainer = new HBox();
        trashContainer.setAlignment(Pos.CENTER);
        trashContainer.setPadding(new Insets(10));
        setMargin(trashContainer, new Insets(8));
        trashContainer.getChildren().add(trashIcon);
        setRight(trashContainer);


        trashContainer.setOnMouseEntered( event -> {
            trashContainer.setBackground(
                    appTheme.getcontrastLightBackground(new CornerRadii(10), null));
        });


        clickContainer.setOnMouseEntered( event -> {
            clickContainer.setBackground(
                    appTheme.getcontrastLightBackground(new CornerRadii(10), null));
        });


        trashContainer.setOnMouseExited(event -> trashContainer.setBackground(background));
        clickContainer.setOnMouseExited(event -> clickContainer.setBackground(background));

        // drag screen
        setOnMousePressed( event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });

        setOnMouseDragged( event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });

        setPadding(new Insets(0,0,0,0));
        setBottom(new Separator());

    }

    public void setOnTrashClickedListener(EventHandler<? super MouseEvent> value) {
        trashContainer.setOnMouseClicked(value);
    }

    public void setOnClickMeListener(EventHandler<? super MouseEvent> value){
        clickContainer.setOnMouseClicked(value);
    }


}
