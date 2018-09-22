package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <data_item> ::= IDENTIFIER “:” <type>
 */
public class DataItem implements Expression {

    private Identifier id;
    private Expression type;

    @Override
    public void tokenize(UmlContext ctx, String content) {

    }

}
