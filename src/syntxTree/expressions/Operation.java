package syntxTree.expressions;

import parsing.UcdParser;
import syntxTree.UmlContext;

import static utils.Utils.*;

/**
 * <operation> ::= IDENTIFIER <arg_declaration> “:” <type>
 */
public class Operation implements Expression {

    private Identifier id, classId;
    private Expression argDeclaration, type;

    public Operation(Identifier classId) {
        this.classId = classId;
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {

        UcdParser parser = new UcdParser(content);
        id = new Identifier(parser.getOperationId(classId.getValue()));

        type = new Type(id).tokenize(ctx, parser.extractType(content));
        Log.all("\t\tid =", id.getValue(), ": type =", type.toString());

        String extractedArs = parser.extractArgList(id.getValue(), classId.getValue());
        argDeclaration = new ArgDeclaration(id).tokenize(ctx, extractedArs);


        return this;

    }
}
