package parsing.syntaxTree.expressions;

import parsing.UcdParser;
import token.UmlContext;

public class Generalization implements Expression {

    private Identifier id;
    private Expression subClassNames;

    public Generalization(String id) {
        this.id = new Identifier(id);
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        UcdParser parser = new UcdParser(content);
        subClassNames = new SubClassNames(id).tokenize(ctx, parser.extractGeneralizationSubclasses());
        return this;
    }

    @Override
    public String toString() {
        return "\n\n\tGeneralization" +
                "\n\t\tid=" + id +
                ", subClassNames=" + subClassNames.toString();
    }
}
