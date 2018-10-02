package screenDisplay.components;

import app.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class OverlayButton extends HBox {

    private static final int small_margin = 8;
    private static final int medium_margin = 16;
    private static final int large_margin = 22;

    private static final CornerRadii rounded_corner_radii = new CornerRadii(10);
    private static final Insets default_inset_value = new Insets(small_margin);
    private static final Insets square_padding = new Insets(small_margin, large_margin, small_margin, large_margin);
    public static final Insets default_button_seperator_margin = new Insets(0, small_margin, 0, small_margin);

    private Text textContent;

    private AppTheme appTheme;

    public OverlayButton(Node parent, AppTheme appTheme) {

        this.appTheme = appTheme;

        setAlignment(Pos.CENTER);
        setPadding(default_inset_value);

        setOnMouseExited(event -> setDefaultBackground());

    }

    public OverlayButton(Node parent, AppTheme appTheme, String text){
        this(parent, appTheme);
        textContent = new Text(text);
        textContent.setFont(appTheme.getMediumFont());
        textContent.setFill(appTheme.getPrimaryLightTextColor());
        setContent(textContent);


    }

    public OverlayButton(Node parent, AppTheme appTheme, ImageView icon){
        this(parent, appTheme);
        setContent(icon);
    }

    public OverlayButton(Node parent, AppTheme appTheme, ImageView icon, int resizeWidthHeight){
        this(parent, appTheme);
        icon.setFitWidth(resizeWidthHeight);
        icon.setFitHeight(resizeWidthHeight);
        setContent(icon);
    }


    public OverlayButton setContent(Node... content){
        getChildren().clear();
        getChildren().addAll(content);
        return this;
    }

    public void setStyle(ThemeStyle style){

        switch (style){
            case SQUARE_FULL:
                setPadding(square_padding);
                setOnMouseEntered(event -> setBackground(appTheme.getContrastDarkBackground()));

                break;
            case ROUNDED:
                setOnMouseEntered(event -> setBackground(appTheme.getContrastLightBackground(rounded_corner_radii)));
                break;
        }

    }

    public void setDefaultBackground(){
        setBackground(appTheme.getSecondaryDarkBackground());
        if (textContent != null){
            textContent.setFill(appTheme.getPrimaryLightTextColor());
        }
    }

    public String getText(){
        return textContent == null?"null":textContent.getText();
    }

    public void setTextColor(Color color){
        textContent.setFill(color);
    }

    public enum ThemeStyle {
        ROUNDED, SQUARE_FULL
    }
}
