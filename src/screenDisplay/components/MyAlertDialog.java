package screenDisplay.components;

import screenDisplay.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyAlertDialog extends Stage {

    protected String message;
    protected AppTheme theme;
    protected VBox content;

    public MyAlertDialog(String message, AppTheme theme) {
        this.message = message;
        this.theme = theme;
    }

    public Stage make(){

        initModality(Modality.WINDOW_MODAL);
        initStyle(StageStyle.UNDECORATED);

        content = new VBox();
        content.setSpacing(200);
        content.setPadding(new Insets(100,100,100,100));
        content.setBackground(theme.getSecondaryDarkBackground());

        Text text = new Text(message);
        text.setFont(theme.getMediumFont());
        text.setFill(Color.WHITE);

        content.getChildren().add(text);

        content.setOnMouseClicked( event -> close());
        setScene(new Scene(content));

        return this;
    }

}
