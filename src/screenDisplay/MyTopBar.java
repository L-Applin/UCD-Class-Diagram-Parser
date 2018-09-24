package screenDisplay;

import app.theme.AppTheme;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyTopBar extends BorderPane {

    public static final String trash_url = "garbage.png";
    public static final String click_me = "Click me to import file manually";

    private AppTheme appTheme;
    private ImageView trashIcon;
    private Text clickMeText;

    private static double xOffset = 0;
    private static double yOffset = 0;

    public MyTopBar(AppTheme appTheme, Stage primaryStage) {

        this.appTheme = appTheme;

        setBackground(appTheme.getprimaryDarkBackground());
        setPadding(appTheme.getMediumPadding());

        clickMeText = new Text(click_me);
        clickMeText.setFont(appTheme.getMediumFont());
        clickMeText.setFill(Color.WHITE);
        HBox hb = new HBox();
        hb.setPadding(new Insets(12,10,0,20));
        hb.getChildren().add(clickMeText);
        setLeft(hb);

        trashIcon = new ImageView(trash_url);
        trashIcon.setFitHeight(24);
        trashIcon.setFitWidth(24);
        HBox hbIm = new HBox();
        hbIm.setPadding(new Insets(10,10,10,20));
        hbIm.getChildren().add(trashIcon);
        setRight(hbIm);

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
        trashIcon.setOnMouseClicked(value);
    }

    public void setOnClickMeListener(EventHandler<? super MouseEvent> value){
        clickMeText.setOnMouseClicked(value);
    }


}
