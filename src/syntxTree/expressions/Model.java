package syntxTree.expressions;

import parsing.GrammarModel;
import parsing.UcdParser;
import syntxTree.IdentifierEntry;
import syntxTree.UmlContext;

import java.util.ArrayList;

/**
 * <model> ::= “MODEL_TAG” IDENTIFIER <list_dec>
 */
public class Model implements Expression {

    private Expression listDec;
    private Identifier id;

    private String _tmpContent;

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {

        // todo : manage if there is multiple MODEL_TAG declaration in the same file
        UcdParser parser = new UcdParser(content);
        IdentifierEntry modelId = parser.splitIdContent(GrammarModel.MODEL_TAG);

        id = new Identifier(modelId.getId());
        listDec = new DeclarationList(new ArrayList<>());
        _tmpContent = modelId.getExpression();
        listDec.tokenize(ctx, modelId.getExpression());

        ctx.put(modelId.getId(), listDec);
        return this;

    }


    @Override
    public String toString() {
        return "MODEL_TAG \nid : \t" + id.toString() + "\ncontent : \t" + _tmpContent;
    }

}
