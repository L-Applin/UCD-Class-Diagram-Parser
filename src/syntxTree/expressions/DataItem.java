package syntxTree.expressions;

import parsing.UcdParser;
import syntxTree.UmlContext;

import static utils.Utils.*;

/**
 * <data_item> ::= IDENTIFIER “:” <type>
 */
public class DataItem implements Expression {

    private Identifier id, parentId;
    private Expression type;


    public DataItem(Identifier parentId) {
        this.parentId = parentId;
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        // todo : error handling
        UcdParser parser = new UcdParser(content);
        String[] splits = parser.splitDataItem(parentId.getValue());
        id = new Identifier(splits[0]);
        type = new Type(id).tokenize(ctx, splits[1]);
        Log.all("\t\t\t"+"id = ", id.getValue() + " : " + "type = ",type.toString() ); // todo : remove (debug)
        return this;
    }

}
