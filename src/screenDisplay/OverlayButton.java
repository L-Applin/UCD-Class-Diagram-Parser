package screenDisplay;

import app.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;

public class OverlayButton extends HBox {

    private AppTheme appTheme;

    public OverlayButton(AppTheme appTheme, Node parent) {

        this.appTheme = appTheme;

        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));

        setOnMouseEntered( event -> {
            setBackground(
                    appTheme.getcontrastLightBackground(new CornerRadii(10), null));
        });
    }
}
