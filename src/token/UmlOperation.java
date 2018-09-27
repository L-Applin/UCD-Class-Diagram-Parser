package token;

import javafx.scene.text.Text;
import syntxTree.expressions.DataItem;

import java.util.ArrayList;
import java.util.List;

public class UmlOperation extends UmlToken {

    private String name, type;
    private List<Args> arguments;


    public UmlOperation(String name, String type) {
        this.name = name;
        this.type = type;
        arguments = new ArrayList<>();
    }

    public void addArgument(DataItem item){
        arguments.add(new Args(item.getIdAsString(), item.getTypeAsString()));
    }

    @Override
    public Text display() {
        // todo
        return null;
    }


    public class Args {

        private String name, type;

        public Args(String name, String type) {
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

    public List<Args> getArguments() {
        return arguments;
    }

    public void setArguments(List<Args> arguments) {
        this.arguments = arguments;
    }

}
