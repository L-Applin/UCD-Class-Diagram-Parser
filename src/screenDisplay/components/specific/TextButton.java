package screenDisplay.components.specific;


import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import screenDisplay.components.ListItem;

/**
 * Custom button for mouseOver and clicked (selected) operation based on text content
 */
public class TextButton extends HBox implements ListItem {

    boolean isSelected;

    private Text content;
    private OverlayStyle basicStyle, mouseoverStyle, clickedStyle;
    private BtnAction onClickAction;

    public TextButton(OverlayStyle basicStyle, OverlayStyle mouseoverStyle, OverlayStyle clickedStyle) {
        this.basicStyle = basicStyle;
        this.mouseoverStyle = mouseoverStyle;
        this.clickedStyle = clickedStyle;

        setOnMouseEntered(event -> {this.setStatus(Status.MOUSEOVER);});
        setOnMouseExited(event -> this.setStatus(Status.BASIC));
        setOnMouseClicked(event -> {
            this.select();
            if (onClickAction != null){
                onClickAction.run(this);
            }
        });
    }

    public void setText(String txt){
        content = new Text(txt);
        content.setFill(basicStyle.textColor);
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

    public void select(){
        setStatus(Status.SELECTED);
        isSelected = true;
    }

    private void updateStyle(OverlayStyle style){
        setBackground(style.background);
        content.setFill(style.textColor);
    }

    public void setOnClickListener(BtnAction action){
        onClickAction = action;
    }

    /**
     * Basic wrapper around style values for the button
     */
    public class OverlayStyle {
        private Background background;
        private Color textColor;

        public OverlayStyle(Background background, Color textColor) {
            this.background = background;
            this.textColor = textColor;
        }

        public Background getBackground() {
            return background;
        }

        public void setBackground(Background background) {
            this.background = background;
        }

        public Color getTextColor() {
            return textColor;
        }

        public void setTextColor(Color textColor) {
            this.textColor = textColor;
        }

    }

    /**
     * Represent the three possible state of the button
     */
    public enum Status { BASIC, MOUSEOVER, SELECTED }

    @FunctionalInterface
    public interface BtnAction {
        void run(TextButton btn);
    }

}
