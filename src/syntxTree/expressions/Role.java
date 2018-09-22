package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <role> ::= “CLASS” IDENTIFIER <multiplicity>
 */
public class Role implements Expression {

    private Identifier id;
    private Expression multiplicity;

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        //todo: complete
        return this;

    }
}
