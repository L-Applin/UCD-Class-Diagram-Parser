package token;

import screenDisplay.ScreenController;
import token.visitor.UmlVisitor;

/**
 * A general representation of a UML Aggregation between a class and the classes that it aggregate to.
 * Used to model a UML class diagram.
 */
public class UmlAggregation extends UmlToken {

    /**
     * The containing class.
     */
    private UmlClass container;
    public UmlClass getContainer() { return container; }
    public void setContainer(UmlClass container) { this.container = container; }

    /**
     * Theparts that arre contained by the container
     */
    private PartsEntry part;
    public PartsEntry getPart() { return part; }
    public void setPart(PartsEntry part) { this.part = part; }

    /**
     * The constructor needs to receive the containing class. Part is added using the
     * {@link UmlAggregation#setPart(PartsEntry)} method.
     * @param container The class that will contain the parts.
     * @param content used for display purpose
     */
    public UmlAggregation(UmlClass container, String content) {
        super(content, container.name);
        this.container = container;
    }


    @Override
    public String display() {
        // todo : find out why part.clazz.name is sometimes null for UmlContext and romevoe try catch :)
        try  {
            return String.format("(A) %s : %s", part.clazz.name, part.mult);
        } catch (NullPointerException npe){
            // todo manage with exception ?
            npe.printStackTrace();
            return "NULL aggregations";
        }
    }

    @Override
    public void updateScreen(ScreenController controller) {
        controller.updateSelection(this);
    }

    /**
     * Immutable parts representation
     */
    public static class PartsEntry{
        public final UmlClass clazz;
        public final String mult;
        public PartsEntry(UmlClass clazz, String mult) {
            this.clazz = clazz;
            this.mult = mult;
        }

        @Override
        public String toString() {
            return "PartsEntry{" +
                    "clazz=" + clazz +
                    ", mult='" + mult + '\'' +
                    '}';
        }
    }

    @Override
    public void accept(UmlVisitor visitor) {
        visitor.visit(this);
    }
}
