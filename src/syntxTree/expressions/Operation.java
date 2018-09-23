package syntxTree.expressions;

import syntxTree.UmlContext;

import static utils.Utils.*;

/**
 * <operation> ::= IDENTIFIER <arg_declaration> “:” <type>
 */
public class Operation implements Expression {

    private Identifier id;
    private Expression argDeclaration;

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        Log.all("\t\t", content);
        return this;

    }
}
