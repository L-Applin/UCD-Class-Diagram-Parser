package app.theme;


import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;


public class AppTheme {

    private Color primaryDark, primaryLight, secondaryDark, contrastDark, contrastLight;
    private Insets mediumPadding;
    private Font mediumFont;
    private Font classFont;
    private Color classFontColor;

    public Insets getMediumPadding() {
        return mediumPadding;
    }

    public Font getMediumFont() {
        return mediumFont;
    }

    public Color getPrimaryDark() {
        return primaryDark;
    }

    public void setPrimaryDark(Color primaryDark) {
        this.primaryDark = primaryDark;
    }

    public Color getPrimaryLight() {
        return primaryLight;
    }

    public void setPrimaryLight(Color primaryLight) {
        this.primaryLight = primaryLight;
    }

    public Color getSecondaryDark() {
        return secondaryDark;
    }

    public void setSecondaryDark(Color secondaryDark) {
        this.secondaryDark = secondaryDark;
    }

    public Color getContrastDark() {
        return contrastDark;
    }

    public void setContrastDark(Color contrastDark) {
        this.contrastDark = contrastDark;
    }

    public Color getContrastLight() {
        return contrastLight;
    }

    public void setContrastLight(Color contrastLight) {
        this.contrastLight = contrastLight;
    }

    public Font getClassFont() {
        return classFont;
    }

    public void setClassFont(Font classFont) {
        this.classFont = classFont;
    }

    public Color getClassFontColor() {
        return classFontColor;
    }

    public void setClassFontColor(Color classFontColor) {
        this.classFontColor = classFontColor;
    }

    public AppTheme(String primaryDark, String primaryLight, String secondaryDark, String contrastDark, String contrastLight) {
        this.primaryDark = Color.web(primaryDark);
        this.primaryLight = Color.web(primaryLight);
        this.secondaryDark = Color.web(secondaryDark);
        this.contrastDark = Color.web(contrastDark);
        this.contrastLight = Color.web(contrastLight);
    }

    /**
     * default values.
     */
    public AppTheme(){
        this.primaryDark = ThemeValue.primary_dark_color;
        this.primaryLight = ThemeValue.primary_light_color;
        this.secondaryDark = ThemeValue.secondary_dark_color;
        this.contrastDark = ThemeValue.primary_contrast_color;
        this.contrastLight = ThemeValue.secondary_contrast_color;

        this.mediumPadding = ThemeValue.defaul_padding;
        this.mediumFont = ThemeValue.default_font;
        this.classFont = ThemeValue.default_class_font;
        this.classFontColor = Color.web("#EEE", 1);

    }

    public Background getprimaryDarkBackground(){
        return new Background(new BackgroundFill(primaryDark, null,null));
    }

    public Background getprimaryDarkBackground(CornerRadii radii, Insets insets){
        return new Background(new BackgroundFill(primaryDark, radii,insets));
    }

    public Background getPrimaryLightBackground(){
        return new Background(new BackgroundFill(primaryLight, null,null));
    }

    public Background getPrimaryLightBackground(CornerRadii radii, Insets insets){
        return new Background(new BackgroundFill(primaryLight, radii, insets));
    }


    public Background getsecondaryDarkBackground(){
        return new Background(new BackgroundFill(secondaryDark, null,null));
    }

    public Background getcontrastDarkBackground(){
        return new Background(new BackgroundFill(contrastDark, null,null));
    }

    public Background getcontrastLightBackground(){
        return new Background( new BackgroundFill(contrastLight, null,null));
    }

    public Background getcontrastLightBackground(CornerRadii radii, Insets insets){
        return new Background( new BackgroundFill(contrastLight, radii,insets));
    }

    public Background primaryRadialGradientBackground(){
        return new Background(new BackgroundFill(new RadialGradient(
                0, 0, 0.5, 0.5, 1.5,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, primaryDark),
                new Stop(1, primaryLight)
        ), CornerRadii.EMPTY, Insets.EMPTY));
    }


}
