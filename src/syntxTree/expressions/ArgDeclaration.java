package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <arg_declaration> ::= “(” <arg_list> “)”
 */
public class ArgDeclaration extends Declaration {

    private Expression argList;

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        //todo: complete
        return this;
    }

}
