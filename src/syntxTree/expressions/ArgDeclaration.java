package syntxTree.expressions;

import syntxTree.UmlContext;
import utils.Utils;

/**
 * <arg_declaration> ::= “(” <arg_list> “)”
 */
public class ArgDeclaration extends Declaration {

    private Expression argList;
    private Identifier methodId;

    public ArgDeclaration(Identifier methodId) {
        this.methodId = methodId;
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        Utils.Log.all("\t\targs =");
        argList = new ArgList(methodId).tokenize(ctx, content);
        return this;
    }

}
