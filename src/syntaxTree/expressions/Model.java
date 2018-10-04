package syntaxTree.expressions;

import parsing.GrammarModel;
import parsing.UcdParser;
import syntaxTree.entries.IdentifierEntry;
import syntaxTree.UmlContext;

import java.util.ArrayList;

/**
 * <model> ::= "MODEL_TAG" IDENTIFIER <list_dec>
 */
public class Model implements Expression {

    private Expression listDec;
    private Identifier id;


    @Override
    public Expression tokenize(final UmlContext ctx, String content) {

        // todo (OPTIONNAL) : manage if there is multiple MODEL_TAG declaration in the same file ?
        UcdParser parser = new UcdParser(content);
        IdentifierEntry modelId = parser.convertIdEntry(GrammarModel.MODEL_TAG);

        id = new Identifier(modelId.getId());
        ctx.setModelId(id.toString());
        listDec = new DeclarationList(new ArrayList<>()).tokenize(ctx, modelId.getExpression());
        ctx.put(modelId.getId(), listDec);
        return this;

    }


    @Override
    public String toString() {
        return "\tMODEL_TAG \nid : \t" + id.toString() + "\n"+ listDec.toString();
    }

}
