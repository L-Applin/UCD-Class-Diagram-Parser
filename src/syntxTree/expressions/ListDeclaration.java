package syntxTree.expressions;

import syntxTree.UmlContext;

import java.util.List;

/**
 * <list_dec> ::= {<declaration>}
 */
public class ListDeclaration implements Expression {

    private List<Expression> decs;

    @Override
    public void tokenize(UmlContext ctx, String content) {
        //todo: complete

    }

}
