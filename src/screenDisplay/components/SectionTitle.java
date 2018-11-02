package screenDisplay.components;

import screenDisplay.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SectionTitle extends HBox {

    public SectionTitle(AppTheme appTheme, String title) {

        Text classTitle = new Text(title);
        classTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        classTitle.setFill(appTheme.getPrimaryLight());
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
        getChildren().add(classTitle);

    }

}
