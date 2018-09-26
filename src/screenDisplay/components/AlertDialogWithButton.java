package screenDisplay.components;

import app.theme.AppTheme;
import javafx.scene.layout.HBox;

public class AlertDialogWithButton extends MyAlertDialog {

    public AlertDialogWithButton(String message, AppTheme theme) {
        super(message, theme);
        HBox buttonContainer = new HBox();
        content.getChildren().add(buttonContainer);

        OverlayButton confirm = new OverlayButton(buttonContainer, theme, "Confrim");
        OverlayButton cancel = new OverlayButton(buttonContainer, theme, "cancel");
        buttonContainer.getChildren().addAll(confirm, cancel);

    }
}
