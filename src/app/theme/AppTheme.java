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


public class AppTheme {


    private Color primaryDark, primaryLight, secondaryDark, contrastDark, contrastLight;
    private String primaryDarkValue, primaryLightValue, secondaryDarkValue, contrastDarkValue, contrastLightValue;
    private Insets mediumPadding;
    private Font mediumFont;
    private Font classFont;
    private Color classFontColor;
    private Color primaryLightTextColor = Color.WHITE;
    private Color primaryDarkTextColor;



    public AppTheme(String primaryDark, String primaryLight, String secondaryDark, String contrastDark, String contrastLight) {

        this.primaryDarkValue =     primaryDark;
        this.primaryLightValue =    primaryLight;
        this.secondaryDarkValue =   secondaryDark;
        this.contrastDarkValue =    contrastDark;
        this.contrastLightValue =   contrastLight;

        this.primaryDark =      Color.web(primaryDark);
        this.primaryLight =     Color.web(primaryLight);
        this.secondaryDark =    Color.web(secondaryDark);
        this.contrastDark =     Color.web(contrastDark);
        this.contrastLight =    Color.web(contrastLight);

    }

    /**
     * default values.
     */
    public AppTheme(){

        this.primaryDarkValue =     ThemeValue.primary_dark_color_value;
        this.primaryLightValue =    ThemeValue.primary_light_color_value;
        this.secondaryDarkValue =   ThemeValue.secondary_dark_color_value;
        this.contrastDarkValue =    ThemeValue.primary_contrast_color_value;
        this.contrastLightValue =   ThemeValue.secondary_contrast_color_value;

        this.primaryDark =          ThemeValue.primary_dark_color;
        this.primaryLight =         ThemeValue.primary_light_color;
        this.secondaryDark =        ThemeValue.secondary_dark_color;
        this.contrastDark =         ThemeValue.primary_contrast_color;
        this.contrastLight =        ThemeValue.secondary_contrast_color;

        this.mediumPadding =        ThemeValue.defaul_padding;
        this.mediumFont =           ThemeValue.default_font;
        this.classFont =            ThemeValue.default_class_font;

        this.classFontColor = Color.web("#EEE", 1);

        this.primaryLightTextColor = Color.WHITE;
        this.primaryDarkTextColor = contrastDark;

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

    public Background getcontrastLightBackground(CornerRadii radii){
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

    public void setPrimaryDarkTextColor(Color primaryDarkTextColor) { this.primaryDarkTextColor = primaryDarkTextColor; }

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
