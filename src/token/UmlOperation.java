package token;

import screenDisplay.ScreenController;
import syntaxTree.expressions.DataItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a method in a form convenient for displaying onto a {@link screenDisplay.MainDisplay}.
 */
public class UmlOperation extends UmlToken {

    /**
     * The return type of the method
     */
    private String type;
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    /**
     * Arguments of the method, with their name and type.
     */
    private List<Args> arguments;
    public List<Args> getArguments() { return arguments; }
    public void setArguments(List<Args> arguments) { this.arguments = arguments; }



    public UmlOperation(String name, String type, String content) {
        super(content, name);
        this.type = type;
        arguments = new ArrayList<>();
    }


    public void addArgument(DataItem item){
        arguments.add(new Args(item.getIdAsString(), item.getTypeAsString()));
    }

    @Override
    public String display() {
        if (arguments.size() == 0) { return type + " " + name + "()"; }

        StringBuilder args = new StringBuilder().append("(");
        arguments.forEach(arg-> args.append(arg.toString()).append(", "));
        return type + " " + name + args.substring(0, args.length() - 2) + ")";
    }


    @Override
    public void updateScreen(ScreenController controller) {
        controller.updateSelection(this);
    }

    /**
     * Simple immutable wrapper around method argurments
     */
    public class Args {

        public final String name, type;

        public Args(String name, String type) {
            this.name = name;
            this.type = type;
        }

        @Override
        public String toString() {
            return name + ":" + type;
        }
    }


}
