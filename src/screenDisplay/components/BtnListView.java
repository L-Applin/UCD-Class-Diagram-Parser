package screenDisplay.components;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import screenDisplay.components.specific.ListButton;

import java.util.ArrayList;
import java.util.List;

public abstract class BtnListView extends VBox {

    protected ListButton currentSelected;
    protected List<ListButton> btnList;

    protected ListButton.OverlayStyle basicStyle, mouseoverStyle, clickedStyle;
    protected Font font;


    public BtnListView(ListButton.OverlayStyle basicStyle, ListButton.OverlayStyle mouseoverStyle, ListButton.OverlayStyle clickedStyle) {
        this.basicStyle = basicStyle;
        this.mouseoverStyle = mouseoverStyle;
        this.clickedStyle = clickedStyle;
        btnList = new ArrayList<>();

    }

    public ListButton createButton(String content){
        ListButton btn = new ListButton(basicStyle, mouseoverStyle, clickedStyle);
        btn.setText(content);
        btn.setFont(font);
        btn.registerTo(this);
        return btn;
    }

    public abstract BtnListView init();

}
