package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <data_item> ::= IDENTIFIER “:” <type>
 */
public class DataItem implements Expression {

    private Identifier id;
    private Expression type;

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        // todo : complete
        return this;
    }

}
