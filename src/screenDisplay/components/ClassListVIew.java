package screenDisplay.components;

import app.theme.AppTheme;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import token.UmlClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassListVIew extends VBox {

    private Map<String, UmlClass> umlClasses;
    private AppTheme appTheme;
    private List<OverlayButton> classBtnList;

    private OverlayButton currentSelected;

    public ClassListVIew(Map<String, UmlClass> umlClasses, AppTheme theme) {
        this.umlClasses = umlClasses;
        this.appTheme = theme;
        classBtnList = new ArrayList<>();
    }

    public VBox init(){

        // setPadding(new Insets(0,10,0,10));
        setBackground(appTheme.getsecondaryDarkBackground());
        ScrollPane classContainer = new ScrollPane();
        classContainer.setPadding(Insets.EMPTY);
        classContainer.setBackground(appTheme.getsecondaryDarkBackground());
        VBox vb = new VBox();
        vb.setBackground(appTheme.getsecondaryDarkBackground());
        vb.setPadding(new Insets(0));

        umlClasses.forEach( (className, umlClass) -> {
            OverlayButton classButton = new OverlayButton(this, appTheme, className);
            classButton.setStyle(OverlayButton.ThemeStyle.CLASS);
/*
            setOnMouseEntered(event -> {
                classButton.setBackground(appTheme.getcontrastDarkBackground());
                if (currentSelected == null || !currentSelected.equals(classButton)){
                    classButton.setBackground(appTheme.getcontrastDarkBackground());
                    currentSelected = classButton;
                    System.out.println(classButton.getText());
                }


            });
*/

            classButton.setOnMouseClicked(event -> {
                classButtonClick(umlClass);
                classButton.setBackground(appTheme.getPrimaryLightBackground());
                classButton.setTextColor(appTheme.getPrimaryDarTextColor());
            });

            classBtnList.add(classButton);
            vb.getChildren().add(classButton);
        });

        classContainer.setContent(vb);
        Text classTitle = new Text("Class");
        classTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        classTitle.setFill(appTheme.getPrimaryLight());
        HBox classTitleBox = new HBox(classTitle);
        classTitleBox.setAlignment(Pos.CENTER);
        classTitleBox.setPadding(new Insets(10));
        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(classTitleBox, classContainer);
        return this;
    }

    public void classButtonClick(UmlClass clazz){
        classBtnList.forEach(OverlayButton::setDefaultBackground);

    }
}
