package syntaxTree.entries;

/**
 * Wrapper around a element of syntax that has an identifier followed by the identifier value
 */
public class IdentifierEntry {

    public final String id, expression;

    public IdentifierEntry(String id, String expression) {
        this.id = id;
        this.expression = expression;
    }

    public IdentifierEntry(String[] entries) {
        this.id = entries[0].trim();
        this.expression = entries[1].trim();
    }

}
