package screenDisplay.components;

import screenDisplay.theme.DefaultThemeValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Custom button for mouseOver and clicked (selected) operation based on text content
 */
public class ListButton extends HBox implements ListItem {


    private boolean isSelected;
    public void setSelected(boolean selected){ isSelected = selected; }

    private Text content;
    public Text getContent() { return content; }

    private Font font;
    public void setFont(Font font) { this.font = font; }

    private EventHandler<? super MouseEvent> onClickevent;

    /**
     * Overlay style for the buttons created
     */
    private OverlayStyle basicStyle, mouseoverStyle, clickedStyle;
    private ListItem.BtnAction onClickAction;
    private BtnListView listView;

    ListButton(OverlayStyle basicStyle, OverlayStyle mouseoverStyle, OverlayStyle clickedStyle) {
        this.basicStyle = basicStyle;
        this.mouseoverStyle = mouseoverStyle;
        this.clickedStyle = clickedStyle;

        this.onClickevent = event -> {
            if (!isSelected){
                listView.resetSelect();
                this.select();
                if (onClickAction != null){
                    onClickAction.run(this);
                }
                listView.resetButtonView();
            }
        };

        setBackground(basicStyle.background);

        setOnMouseEntered(event -> {
            if (!isSelected){
                this.setStatus(Status.MOUSEOVER);
            }
        });


        setOnMouseExited(event -> {
            if (!isSelected) {
                this.setStatus(Status.BASIC);
            }
        });

        setOnMouseClicked(onClickevent);

    }

    public void setText(String txt){
        content = new Text(txt);
        content.setFill(basicStyle.textColor);
        content.setFont(Font.font(DefaultThemeValue.font_weight));
        getChildren().add(content);
    }

    public void setStatus(Status status){

        switch (status){
            case BASIC:
                updateStyle(basicStyle);
                break;
            case MOUSEOVER:
                updateStyle(mouseoverStyle);
                break;
            case SELECTED:
                isSelected = true;
                updateStyle(clickedStyle);
                break;
        }
    }

    public void setStatus(){
        if (isSelected) {
            setStatus(Status.SELECTED);
        } else {
            setStatus(Status.BASIC);
        }
    }

    /**
     * used to manually select this Button from the {@link screenDisplay.components.umlComponents.UmlBtnListView}
     * that this Item is part of
     */
    public void select(){
        listView.setCurrentSelected(this);
        setStatus(Status.SELECTED);
        isSelected = true;
    }

    /**
     * Simulate a mouse click on this Button
     */
    public void forceClick(){
        if (!isSelected){
            listView.resetSelect();
            this.select();
            if (onClickAction != null){
                onClickAction.run(this);
            }
            listView.resetButtonView();
        }
    }

    private void updateStyle(OverlayStyle style){
        setBackground(style.background);
        content.setFill(style.textColor);
    }

    public void setOnClickListener(BtnAction action){
        onClickAction = action;
    }

    public void registerTo(BtnListView listView){
        this.listView = listView;
    }


    /**
     * Basic immutable wrapper around style values for the button
     */
    public static class OverlayStyle {
        public final Background background;
        public final Color textColor;

        public OverlayStyle(Background background, Color textColor) {
            this.background = background;
            this.textColor = textColor;
        }

    }


    /**
     * Represent the three possible state of the button
     */
    public enum Status { BASIC, MOUSEOVER, SELECTED }


}
