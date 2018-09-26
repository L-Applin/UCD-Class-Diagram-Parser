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

    private static int small_margin = 8;
    private static int medium_margin = 16;
    private static int large_margin = 22;

    private static CornerRadii default_corner_radii = new CornerRadii(10);
    private static Insets default_inset_value = new Insets(small_margin);
    private static Insets class_padding = new Insets(small_margin, large_margin, small_margin, large_margin);
    public static final Insets default_button_seperator_margin = new Insets(0, small_margin, 0, small_margin);

    private ThemeStyle style;
    private Text textContent;

    private AppTheme appTheme;
    private Node parent;

    public OverlayButton(Node parent, AppTheme appTheme) {

        this.appTheme = appTheme;

        setAlignment(Pos.CENTER);
        setPadding(default_inset_value);

        setOnMouseEntered(event -> setBackground(appTheme.getcontrastLightBackground(default_corner_radii)));
        setOnMouseExited(event -> setDefaultBackground());

    }

    public OverlayButton(Node parent, AppTheme appTheme, String text){
        this(parent, appTheme);
        textContent = new Text(text);
        textContent.setFont(appTheme.getMediumFont());
        textContent.setFill(appTheme.getPrimaryLightTextColor());
        setContent(textContent);

        setOnMouseEntered(event -> {
            setBackground(appTheme.getcontrastLightBackground(default_corner_radii));
        });

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

        System.out.println(style.toString());

        switch (style){
            case CLASS:
                setPadding(class_padding);
                break;
            case PRIMARY:
                setOnMouseEntered(event -> {
                    setBackground(appTheme.getcontrastLightBackground(default_corner_radii));
                });
                break;
        }

    }

    public void setDefaultBackground(){
        setBackground(appTheme.getsecondaryDarkBackground());
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
        PRIMARY, CLASS
    }
}
