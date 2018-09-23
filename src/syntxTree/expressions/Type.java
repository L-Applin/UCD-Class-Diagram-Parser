package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <type> ::= IDENTIFIER
 */
public class Type implements Expression {

    private Identifier id;

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        // todo : check for illegal character in identifier
        this.id = new Identifier(content);
        return this;
    }

    @Override
    public String toString() {
        return id.getValue();
    }
}
