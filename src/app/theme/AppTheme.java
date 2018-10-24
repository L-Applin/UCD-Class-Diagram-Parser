package app.theme;


import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

/**
 * Manages the color display and padding value of the app
 */
public class AppTheme {

    private Color primaryDark, primaryLight, secondaryDark, contrastDark, contrastLight, primaryDarkTransparent;
    private String primaryDarkValue, primaryLightValue, secondaryDarkValue, contrastDarkValue, contrastLightValue;
    private Insets mediumPadding;
    private Font mediumFont;
    private Font classFont;
    private Color classFontColor;
    private Color primaryLightTextColor = Color.WHITE;
    private Color primaryDarkTextColor;

    /**
     * Overrides default specified in {@link DefaultThemeValue} value with those passed as parameter.
     * @param primaryDark the new color for the parameter in the form '#HHHHHH' where H is anan haxidecimal character. Opacity will be 1.
     * @param primaryLight the new color for the parameter in the form '#HHHHHH' where H is anan haxidecimal character. Opacity will be 1.
     * @param secondaryDark the new color for the parameter in the form '#HHHHHH' where H is anan haxidecimal character. Opacity will be 1.
     * @param contrastDark the new color for the parameter in the form '#HHHHHH' where H is anan haxidecimal character. Opacity will be 1.
     * @param contrastLight the new color for the parameter in the form '#HHHHHH' where H is anan haxidecimal character. Opacity will be 1.
     */
    public AppTheme(String primaryDark,
                    String primaryLight,
                    String secondaryDark,
                    String contrastDark,
                    String contrastLight) {

        this.primaryDarkValue =     primaryDark;
        this.primaryLightValue =    primaryLight;
        this.secondaryDarkValue =   secondaryDark;
        this.contrastDarkValue =    contrastDark;
        this.contrastLightValue =   contrastLight;

        try {
            this.primaryDark =          Color.web(primaryDark);
            this.primaryLight =         Color.web(primaryLight);
            this.secondaryDark =        Color.web(secondaryDark);
            this.contrastDark =         Color.web(contrastDark);
            this.contrastLight =        Color.web(contrastLight);
        } catch (IllegalArgumentException | NullPointerException npe){
            this.primaryDark =          DefaultThemeValue.primary_dark_color;
            this.primaryLight =         DefaultThemeValue.primary_light_color;
            this.secondaryDark =        DefaultThemeValue.secondary_dark_color;
            this.contrastDark =         DefaultThemeValue.primary_contrast_color;
            this.contrastLight =        DefaultThemeValue.secondary_contrast_color;
        }
    }

    /**
     * default values.
     */
    public AppTheme(){

        this.primaryDarkValue =     DefaultThemeValue.primary_dark_color_value;
        this.primaryLightValue =    DefaultThemeValue.primary_light_color_value;
        this.secondaryDarkValue =   DefaultThemeValue.secondary_dark_color_value;
        this.contrastDarkValue =    DefaultThemeValue.primary_contrast_color_value;
        this.contrastLightValue =   DefaultThemeValue.secondary_contrast_color_value;

        this.primaryDarkTransparent = DefaultThemeValue.primary_dark_transparent_color;

        this.primaryDark =          DefaultThemeValue.primary_dark_color;
        this.primaryLight =         DefaultThemeValue.primary_light_color;
        this.secondaryDark =        DefaultThemeValue.secondary_dark_color;
        this.contrastDark =         DefaultThemeValue.primary_contrast_color;
        this.contrastLight =        DefaultThemeValue.secondary_contrast_color;

        this.mediumPadding =        DefaultThemeValue.defaul_padding;
        this.mediumFont =           DefaultThemeValue.default_font;
        this.classFont =            DefaultThemeValue.default_class_font;

        this.classFontColor = Color.web("#EEE", 1);

        this.primaryLightTextColor = Color.WHITE;
        this.primaryDarkTextColor = contrastDark;

    }

    public Background getPrimaryDarkBackground(){
        return new Background(new BackgroundFill(primaryDark, null,null));
    }

    public Background getPrimaryDarkBackgroundAlpha(){
        return new Background(new BackgroundFill(primaryDarkTransparent, null,null));
    }


    public Background getPrimaryDarkBackground(CornerRadii radii, Insets insets){
        return new Background(new BackgroundFill(primaryDark, radii,insets));
    }

    public Background getPrimaryLightBackground(){
        return new Background(new BackgroundFill(primaryLight, null,null));
    }

    public Background getPrimaryLightBackground(CornerRadii radii, Insets insets){
        return new Background(new BackgroundFill(primaryLight, radii, insets));
    }


    public Background getSecondaryDarkBackground(){
        return new Background(new BackgroundFill(secondaryDark, null,null));
    }

    public Background getContrastDarkBackground(){
        return new Background(new BackgroundFill(contrastDark, null,null));
    }

    public Background getContrastLightBackground(){
        return new Background( new BackgroundFill(contrastLight, null,null));
    }

    public Background getContrastLightBackground(CornerRadii radii, Insets insets){
        return new Background( new BackgroundFill(contrastLight, radii,insets));
    }

    public Background getContrastLightBackground(CornerRadii radii){
        return new Background( new BackgroundFill(contrastLight, radii, Insets.EMPTY));
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


    public DropShadow elevation(Color color){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(12.0);
        dropShadow.setOffsetY(4);
        dropShadow.setColor(color);
        return dropShadow;
    }



    // GETTERS AND SETTER FLOOD

    public Insets getMediumPadding() { return mediumPadding; }

    public Font getMediumFont() { return mediumFont; }

    public String getPrimaryDarkValue() {
        return primaryDarkValue;
    }

    public void setPrimaryDarkValue(String primaryDarkValue) {
        this.primaryDarkValue = primaryDarkValue;
    }

    public String getPrimaryLightValue() {
        return primaryLightValue;
    }

    public void setPrimaryLightValue(String primaryLightValue) {
        this.primaryLightValue = primaryLightValue;
    }

    public String getSecondaryDarkValue() {
        return secondaryDarkValue;
    }

    public void setSecondaryDarkValue(String secondaryDarkValue) {
        this.secondaryDarkValue = secondaryDarkValue;
    }

    public String getContrastDarkValue() {
        return contrastDarkValue;
    }

    public void setContrastDarkValue(String contrastDarkValue) {
        this.contrastDarkValue = contrastDarkValue;
    }

    public String getContrastLightValue() {
        return contrastLightValue;
    }

    public void setContrastLightValue(String contrastLightValue) {
        this.contrastLightValue = contrastLightValue;
    }

    public void setMediumPadding(Insets mediumPadding) {
        this.mediumPadding = mediumPadding;
    }

    public void setMediumFont(Font mediumFont) {
        this.mediumFont = mediumFont;
    }

    public Color getPrimaryLightTextColor() {
        return primaryLightTextColor;
    }

    public void setPrimaryLightTextColor(Color primaryLightTextColor) {
        this.primaryLightTextColor = primaryLightTextColor;
    }

    public Color getPrimaryDarkTextColor() {
        return primaryDarkTextColor;
    }

    public void setPrimaryDarkTextColor(Color primaryDarkTextColor) {
        this.primaryDarkTextColor = primaryDarkTextColor;
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


}
