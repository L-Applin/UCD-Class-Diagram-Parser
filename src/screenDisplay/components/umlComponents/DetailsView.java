package screenDisplay.components.umlComponents;

import javafx.scene.layout.HBox;
import screenDisplay.theme.AppTheme;
import screenDisplay.theme.DefaultThemeValue;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import screenDisplay.MainDisplay;
import token.UmlToken;

public class DetailsView extends VBox implements UmlView {

    private static double width = 950;
    private static double height = 300;

    private HBox title;
    private MainDisplay mainDisplay;
    private AppTheme appTheme;
    private ScrollPane container;
    private UmlToken content;

    private Text textDisplay;

    public DetailsView(MainDisplay mainDisplay, UmlToken token) {
        this.mainDisplay = mainDisplay;
        appTheme = mainDisplay.getAppTheme();
        this.content = token;
    }

    @SuppressWarnings("unchecked")
    public DetailsView init(){
        container = new ScrollPane();

        container.setStyle(
                "-fx-background:" + appTheme.getPrimaryLightValue() + ";\n" +
                "-fx-focus-color: transparent;\n" +
                "-fx-faint-focus-color: transparent;");
        container.setPadding(Insets.EMPTY);
        container.setFocusTraversable(false);

        this.textDisplay = createTextContent(content.getContent());

        container.setMinSize(width, height);
        container.setMaxSize(width, height);

        container.setContent(textDisplay);

        getChildren().addAll(title, container);

        return this;

    }


    public void setTitle(String txt){
        this.title = mainDisplay.sectionTitle(txt);
        title.setBackground(mainDisplay.getAppTheme().getPrimaryDarkBackground());
    }


    public void updateDetails(UmlToken token){
        this.content = token;
        textDisplay.setText(token.getContent());

    }

    private Text createTextContent(String content){
        Text txt = new Text(content);
        txt.setFill(appTheme.getSecondaryDark());
        txt.setFont(Font.font(DefaultThemeValue.font_weight));
        return txt;
    }

}
