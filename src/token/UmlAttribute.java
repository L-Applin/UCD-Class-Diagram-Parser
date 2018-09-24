package token;

import javafx.scene.text.Text;

public class UmlAttribute extends UmlToken {

    private String name, type;

    public UmlAttribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Text display() {
        // todo
        return null;
    }
}
