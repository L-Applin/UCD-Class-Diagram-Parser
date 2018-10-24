package screenDisplay.components.umlComponents;

import app.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import screenDisplay.MainDisplay;
import screenDisplay.ScreenController;
import screenDisplay.components.BtnListView;
import screenDisplay.components.ListButton;
import screenDisplay.components.SectionTitle;
import token.UmlClass;
import token.UmlContext;
import token.UmlToken;

import java.util.ArrayList;
import java.util.Comparator;

public class ClassListView extends UmlBtnListView {

    private static Insets button_padding = new Insets(4,18,4,18);

    private AppTheme appTheme;
    private MainDisplay mainDisplay;
    private String modelName;

    public ClassListView(UmlContext context, MainDisplay mainDisplay) {
        super(mainDisplay.getAppTheme());
        this.mainDisplay = mainDisplay;
        this.listItem = new ArrayList<>(context.getClasses().values());
        this.modelName = context.getModelId();
        this.appTheme = mainDisplay.getAppTheme();
        this.font = Font.font ("Verdana", 20);
    }

    @Override
    public BtnListView init(){

        // setPadding(new Insets(0,10,0,10));
        setAlignment(Pos.TOP_CENTER);
        setBackground(appTheme.getSecondaryDarkBackground());

        container.setPadding(Insets.EMPTY);
        container.setBackground(appTheme.getSecondaryDarkBackground());

        VBox scrollViewContent = new VBox();
        scrollViewContent.setBackground(appTheme.getSecondaryDarkBackground());
        scrollViewContent.setPadding(Insets.EMPTY);

        // sort list item by alphabetical lexicographic order of their display name
        // so that they are display in order on screen
        listItem.sort(Comparator.comparing(UmlToken::getName, String.CASE_INSENSITIVE_ORDER));

        listItem.forEach( umlClass -> {

            ListButton btn = createButton(umlClass.display());
            btnList.add(btn);
            btn.setPadding(button_padding);
            btn.setOnClickListener(button -> {
                ScreenController screenCtrl = new ScreenController(mainDisplay);
                screenCtrl.updateSelection((UmlClass) umlClass);
            });

        });

        scrollViewContent.getChildren().addAll(btnList);
        container.setContent(scrollViewContent);

        getChildren().addAll(new SectionTitle(appTheme, modelName), container);
        setEffect(appTheme.elevation(Color.BLACK));

        return this;
    }

    public void forceClick(String classId){
        btnList.forEach(item -> {
            if(item.getContent().getText().equals(classId)){
                item.forceClick();
            }
        });
    }

}
