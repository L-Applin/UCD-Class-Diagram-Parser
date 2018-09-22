package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <operation> ::= IDENTIFIER <arg_declaration> “:” <type>
 */
public class Operation implements Expression {

    private Identifier id;
    private Expression argDeclaration;

    @Override
    public void tokenize(UmlContext ctx, String content) {
        //todo: complete

    }
}
