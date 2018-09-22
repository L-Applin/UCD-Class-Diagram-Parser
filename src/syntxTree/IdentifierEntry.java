package syntxTree;

import syntxTree.expressions.Expression;

/**
 * Wrapper around a element of suntax that has an identifer followed by the identifier value
 */
public class IdentifierEntry {

    private String id;
    private String expression;

    public IdentifierEntry(String id, String expression) {
        this.id = id;
        this.expression = expression;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

}
