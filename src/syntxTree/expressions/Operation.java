package syntxTree.expressions;

import parsing.UcdParser;
import syntxTree.UmlContext;

import static utils.Utils.*;

/**
 * <operation> ::= IDENTIFIER <arg_declaration> “:” <type>
 */
public class Operation implements Expression {

    private Identifier methodId, classId;
    private Expression argDeclaration, type;

    public Operation(Identifier classId) {
        this.classId = classId;
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {

        UcdParser parser = new UcdParser(content);
        methodId = new Identifier(parser.getOperationId(classId.getValue()));

        type = new Type(methodId).tokenize(ctx, parser.extractType(content));
        Log.all("\t\tmethodId =", methodId.getValue(), ": type =", type.toString());

        ctx.getUmlClass(classId.getValue()).createOperation(methodId.getValue(), type.toString(), content);

        String extractedArs = parser.extractArgList(methodId.getValue(), classId.getValue());
        argDeclaration = new ArgDeclaration(methodId, classId).tokenize(ctx, extractedArs);

        return this;

    }
}
