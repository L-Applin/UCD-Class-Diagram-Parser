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

    public Identifier getId() {
        return id;
    }
    public String getIdAsString() {
        return id.toString();
    }
    public void setId(Identifier id) {
        this.id = id;
    }

    public Expression getType() {
        return type;
    }
    public String getTypeAsString() {
        return type.toString();
    }
    public void setType(Expression type) {
        this.type = type;
    }

    /**
     * Represent a \<dataItem\> tag from the ucd Grammar syntax
     * @param parentId
     */
    public DataItem(Identifier parentId) {
        this.parentId = parentId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataItem tokenize(final UmlContext ctx, String content) {
        // todo : error handling
        UcdParser parser = new UcdParser(content);
        String[] splits = parser.splitDataItem(parentId.getValue());
        id = new Identifier(splits[0]);
        type = new Type(id).tokenize(ctx, splits[1]);
        return this;
    }

}
