package syntxTree.expressions;

import syntxTree.UmlContext;

import java.util.ArrayList;

/**
 * <operation_list>::= [<operation> {“,” <operation>}]
 */
public class OperationList implements Expression {

    private ArrayList<Expression> opList;

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        //todo: complete
        return this;

    }
}
