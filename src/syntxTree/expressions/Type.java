package syntxTree.expressions;

import parsing.GrammarModel;
import parsing.UcdParser;
import syntxTree.UmlContext;
import syntxTree.exceptions.MalformedTypeException;
import utils.Utils;

/**
 * <type> ::= IDENTIFIER
 */
public class Type implements Expression {

    private Identifier id, parentId;

    public Type(Identifier parentId) {
        this.parentId = parentId;
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        UcdParser parser = new UcdParser(content);
        parser.checkValidType(content, parentId.getValue());
        this.id = new Identifier(content);
        return this;
    }

    @Override
    public String toString() {
        return id.getValue();
    }
}
