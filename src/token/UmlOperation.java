package token;

import screenDisplay.ScreenController;
import token.visitor.UmlVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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


    /**
     * A method also need a return type to be valid. Arguments are added through the
     * {@link UmlOperation#addArgument(String, String)} method defined in this class.
     * @param name the name (or id) of the method
     * @param type the return type
     * @param content it's String content used for display
     */
    public UmlOperation(String name, String type, String content) {
        super(content, name);
        this.type = type;
        arguments = new ArrayList<>();
    }


    /**
     * Adds an argument to the method
     * @param id
     * @param type
     */
    public void addArgument(String id, String type){
        arguments.add(new Args(id, type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String display() {
        if (arguments.size() == 0) { return type + " " + name + "()"; }

        StringBuilder args = new StringBuilder().append("(");
        arguments.forEach(arg-> args.append(arg.toString()).append(", "));
        return type + " " + name + args.substring(0, args.length() - 2) + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateScreen(ScreenController controller) {
        controller.updateSelection(this);
    }

    /**
     * Simple immutable wrapper around method argurments
     */
    public static class Args {

        public final String name, type;

        public Args(String name, String type) {
            this.name = name;
            this.type = type;
        }

        @Override
        public String toString() {
            return name + ":" + type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Args args = (Args) o;
            return Objects.equals(name, args.name) &&
                    Objects.equals(type, args.type);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(UmlVisitor visitor) {
        visitor.visit(this);
    }
}
