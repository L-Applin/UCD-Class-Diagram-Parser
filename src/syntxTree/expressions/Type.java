package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <type> ::= IDENTIFIER
 */
public class Type implements Expression {

    private Identifier id;

    public Type(Identifier id) {
        this.id = id;
    }

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        //todo: complete
        return this;

    }

}
