package syntxTree.expressions;

import syntxTree.UmlContext;

import java.util.List;

/**
 * <attribute_list> ::= [<data_item> {“,” <data_item>}] ;
 */
public class AttributeList implements Expression {

    private List<Expression> attributeList;

    @Override
    public void tokenize(UmlContext ctx) {
        //todo: complete
    }

}
