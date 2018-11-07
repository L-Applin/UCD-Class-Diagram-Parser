package parsing.syntaxTree.expressions;

import token.UmlContext;
import app.Utils;

/**
 * <arg_declaration> ::= "(" <arg_list> ")"
 */
public class ArgDeclaration extends Declaration {

    private Expression argList;
    private Identifier methodId, classId;

    public ArgDeclaration(Identifier methodId, Identifier classId) {
        this.methodId = methodId; this.classId = classId;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        argList = new ArgList(methodId, classId).tokenize(ctx, content);
        return this;
    }

    @Override
    public String toString() {
        return "ArgDeclaration {" +
                "methodId = " + methodId +
                ", argList = " + argList.toString() +
                '}';
    }
}
