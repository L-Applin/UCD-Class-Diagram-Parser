package token;

import screenDisplay.ScreenController;

public class UmlAttribute extends UmlToken {

    private String type;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }


    public UmlAttribute(String name, String type, String content) {
        super(content, name);
        this.type = type;
    }


    @Override
    public String display() {
        return type + " " + name;
    }

    @Override
    public void updateScreen(ScreenController controller) {
        controller.updateSelection(this);
    }


}
