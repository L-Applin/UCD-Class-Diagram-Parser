package syntxTree.expressions;

import syntxTree.UmlContext;

import java.util.List;

/**
 * <attribute_list> ::= [<data_item> {“,” <data_item>}]
 */
public class AttributeList implements Expression {

    private List<Expression> attributeList;

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        //todo: complete
        return this;
    }

}
