package token;

import screenDisplay.ScreenController;
import token.visitor.UmlVisitor;

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
        this.type = type.trim();
    }


    @Override
    public String display() {
        return type + " " + name;
    }

    @Override
    public void updateScreen(ScreenController controller) {
        controller.updateSelection(this);
    }

    @Override
    public void accept(UmlVisitor visitor) {
        visitor.visit(this);
    }
}
