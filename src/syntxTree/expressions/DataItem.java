package syntxTree.expressions;

import syntxTree.UmlContext;

import static utils.Utils.*;

/**
 * <data_item> ::= IDENTIFIER “:” <type>
 */
public class DataItem implements Expression {

    private Identifier id;
    private Expression type;

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        String[] splits = content.split(":");
        id = new Identifier(splits[0]);
        type = new Type().tokenize(ctx, splits[1]);
        Log.all("\t\t"+id.getValue() + " : " + type.toString() ); // todo : remove (debug)
        return this;
    }

}
