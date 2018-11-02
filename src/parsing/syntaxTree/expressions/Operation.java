package parsing.syntaxTree.expressions;

import parsing.UcdParser;
import token.UmlContext;

import static app.Utils.Log;

/**
 * <operation> ::= IDENTIFIER <arg_declaration> ":" <type>
 */
public class Operation implements Expression {

    private Identifier methodId, classId;
    private Expression argDeclaration, type;

    public Operation(Identifier classId) {
        this.classId = classId;
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {

        if (!(content.equals("") || content.equals(" "))) {
            UcdParser parser = new UcdParser(content);
            methodId = new Identifier(parser.getOperationId());

            type = new Type(methodId).tokenize(ctx, parser.extractType());
            Log.all("\t\tmethodId =", methodId.getValue(), ": type =", type.toString());

            ctx.getUmlClass(classId.getValue()).createOperation(methodId.getValue(), type.toString(), content);

            String extractedArs = parser.extractArgList(methodId.getValue());
            argDeclaration = new ArgDeclaration(methodId, classId).tokenize(ctx, extractedArs);
        }
        return this;

    }
}
