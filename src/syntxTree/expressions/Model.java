package syntxTree.expressions;

import parsing.UmlParser;
import syntxTree.IdentifierEntry;
import syntxTree.UmlContext;

/**
 * <model> ::= “MODEL” IDENTIFIER <list_dec>
 */
public class Model implements Expression {

    private Expression listDec;
    private Identifier id;

    private String _tmpContent;

    public Model(final Identifier id, final ListDeclaration decs) {
        this.id = id;
        this.listDec = decs;
    }

    @Override
    public void tokenize(UmlContext ctx, String content) {

        UmlParser parser = new UmlParser(content);
        IdentifierEntry modelId = parser.splitIdContent();

        id = new Identifier(modelId.getId());
        listDec = new ListDeclaration();
        _tmpContent = modelId.getExpression();
        listDec.tokenize(ctx, modelId.getExpression());

        ctx.put(modelId.getId(), listDec);

    }


    @Override
    public String toString() {
        return "MODEL \nid : \t" + id.toString() + "\ncontent : \t" + _tmpContent;
    }
}
