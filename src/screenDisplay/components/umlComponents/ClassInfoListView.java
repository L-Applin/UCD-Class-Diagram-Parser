package screenDisplay.components.umlComponents;

import app.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import screenDisplay.MainDisplay;
import screenDisplay.ScreenController;
import screenDisplay.components.BtnListView;
import screenDisplay.components.ListButton;
import screenDisplay.components.SectionTitle;
import token.UmlToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ClassInfoListView extends UmlBtnListView {

    public static final Insets list_container_padding = new Insets(0,20,0,20);
    public static final double default_width = 300;
    public static final double default_height = 29.5*4; // four times the height of the button elements

    private SectionTitle title;
    private MainDisplay mainDisplay;


    private ClassInfoListView(MainDisplay mainDisplay) {
        super(mainDisplay.getAppTheme());
        this.mainDisplay = mainDisplay;
        this.button_padding = new Insets(4,12,4,12);
    }

    public ClassInfoListView(MainDisplay mainDisplay, Map<String, ? extends UmlToken> tokens) {
        this(mainDisplay);
        listItem = new ArrayList<>(tokens.values());

    }

    public ClassInfoListView(MainDisplay mainDisplay, List<? extends UmlToken> tokens) {
        this(mainDisplay);
        listItem = tokens;

    }


    public void setTitle(String txt){
        this.title = new SectionTitle(mainDisplay.getAppTheme(), txt);
        title.setBackground(mainDisplay.getAppTheme().getPrimaryDarkBackground());
    }


    @Override
    public BtnListView init() {

        AppTheme appTheme = mainDisplay.getAppTheme();

        setAlignment(Pos.TOP_CENTER);

        container.setStyle("-fx-background:" + appTheme.getSecondaryDarkValue() +";\n"
                + "-fx-focus-color: transparent;\n-fx-faint-focus-color: transparent;");
        container.setPadding(Insets.EMPTY);
        container.setFocusTraversable(false);


        // make sure the button fills the box horizontally
        if (size == null){
            // default value
            container.setMinSize(default_width, default_height);
            container.setMaxSize(default_width, default_height);
        } else {
            // overridden value
            container.setMinSize(size.width, size.height);
            container.setMaxSize(size.width, size.height);
        }


        VBox scrollViewContent = new VBox();
        scrollViewContent.setMinHeight(default_height);
        scrollViewContent.setBackground(appTheme.getSecondaryDarkBackground());
        scrollViewContent.setPadding(Insets.EMPTY);
        setMargin(scrollViewContent, Insets.EMPTY);

        listItem.forEach( token -> {

            ListButton btn = createButton(token.display());
            btn.setMinWidth(size == null ? default_width: size.width);
            btnList.add(btn);
            btn.setPadding(button_padding);
            btn.setOnClickListener(button -> {
                ScreenController screenCtrl = new ScreenController(mainDisplay);
                token.updateScreen(screenCtrl);
                button.select();
            });
        });

        scrollViewContent.getChildren().addAll(btnList);
        container.setContent(scrollViewContent);
        container.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        getChildren().addAll(title, container);


        return this;


    }

    public void overrideDefaultSize(Size size){
        this.size = size;
    }

}
