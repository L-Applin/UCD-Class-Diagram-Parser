package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <arg_declaration> ::= “(” <arg_list> “)”
 */
public class ArgDeclaration implements Expression {

    private Expression argList;

    @Override
    public void tokenize(UmlContext ctx, String content) {
        //todo: complete

    }

}
