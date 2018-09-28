package screenDisplay.components;

import app.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import screenDisplay.MainDisplay;
import screenDisplay.components.specific.ListButton;
import token.UmlClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassListVIew extends BtnListView {

    private static Insets button_padding = new Insets(4,18,4,18);

    private Map<String, UmlClass> umlClasses;
    private AppTheme appTheme;
    // private List<OverlayButton> classBtnList;
    private MainDisplay mainDisplay;

    public ClassListVIew(Map<String, UmlClass> umlClasses, AppTheme theme, MainDisplay mainDisplay) {
        super(new ListButton.OverlayStyle(theme.getsecondaryDarkBackground(), theme.getPrimaryLight()),
              new ListButton.OverlayStyle(theme.getcontrastDarkBackground(), theme.getPrimaryLight()),
              new ListButton.OverlayStyle(theme.getPrimaryLightBackground(), theme.getSecondaryDark()));
        this.mainDisplay = mainDisplay;
        this.umlClasses = umlClasses;
        this.appTheme = theme;
        // classBtnList = new ArrayList<>();
        this.font = Font.font ("Verdana", 20);

    }

    @Override
    public BtnListView init(){

        // setPadding(new Insets(0,10,0,10));
        setAlignment(Pos.TOP_CENTER);
        setBackground(appTheme.getsecondaryDarkBackground());

        ScrollPane classContainer = new ScrollPane();
        classContainer.setPadding(Insets.EMPTY);
        classContainer.setBackground(appTheme.getsecondaryDarkBackground());

        VBox vb = new VBox();
        vb.setBackground(appTheme.getsecondaryDarkBackground());
        vb.setPadding(Insets.EMPTY);

        umlClasses.forEach( (className, umlClass) -> {

            ListButton btn = createButton(className);
            btnList.add(btn);
            btn.setPadding(button_padding);
            btn.setOnClickListener(button -> mainDisplay.updateClassSelected(umlClass));

        });

        vb.getChildren().addAll(btnList);
        classContainer.setContent(vb);

        getChildren().addAll(new SectionTitle(appTheme), classContainer);
        return this;
    }

}
