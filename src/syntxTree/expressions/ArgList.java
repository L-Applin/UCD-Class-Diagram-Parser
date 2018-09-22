package syntxTree.expressions;

import syntxTree.UmlContext;

import java.util.List;

/**
 * <arg_list> ::= [<data_item> {“,” <data_item>}]
 */
public class ArgList implements Expression {

    private List<Expression> dataItems;

    @Override
    public void tokenize(UmlContext ctx, String content) {
        //todo: complete
    }

}
