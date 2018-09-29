package screenDisplay.components;

import app.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import screenDisplay.components.umlComponents.UmlView;

import java.util.ArrayList;
import java.util.List;

public abstract class BtnListView extends VBox implements UmlView {

    protected ListButton currentSelected;
    public void setCurrentSelected(ListButton btn){ this.currentSelected = btn; }

    protected List<ListButton> btnList;
    protected ScrollPane container;

    protected Insets button_padding;

    protected ListButton.OverlayStyle basicStyle, mouseoverStyle, clickedStyle;
    protected Font font;
    protected Size size;


    public BtnListView(AppTheme theme) {
        this.basicStyle = new ListButton.OverlayStyle(theme.getsecondaryDarkBackground(), theme.getPrimaryLight());
        this.mouseoverStyle = new ListButton.OverlayStyle(theme.getcontrastDarkBackground(), theme.getPrimaryLight());
        this.clickedStyle = new ListButton.OverlayStyle(theme.getPrimaryLightBackground(), theme.getContrastDark());
        btnList = new ArrayList<>();
        container = new ScrollPane();

    }

    public ListButton createButton(String content){
        ListButton btn = new ListButton(basicStyle, mouseoverStyle, clickedStyle);
        btn.setText(content);
        btn.setFont(font);
        btn.registerTo(this);
        return btn;
    }

    public void resetButtonView(){
        btnList.forEach(ListButton::setStatus);
    }

    public void resetSelect(){
        btnList.forEach(btn -> {
            btn.setSelected(false);
            btn.setStatus();
        });
    }

    public abstract BtnListView init();

    /**
     * Immutable size values
     */
    public static class Size {
        public final double width;
        public final double height;
        public Size(double width, double height) {
            this.width = width;
            this.height = height;
        }
    }


}
