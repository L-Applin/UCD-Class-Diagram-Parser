package syntaxTree.entries;

/**
 * Wrapper around a element of syntax that has an identifier followed by the identifier value
 */
public class IdentifierEntry {

    public final String id, expression;

    public IdentifierEntry(String id, String expression) {
        this.id = id.trim();
        this.expression = expression.trim();
    }

    public IdentifierEntry(String[] entries) {
        this(entries[0], entries[1]);
    }

}
