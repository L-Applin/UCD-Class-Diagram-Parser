package syntxTree.expressions;

import syntxTree.UmlContext;

public class Generalization implements Expression {

    private Identifier id;
    private Expression subClassNames;

    public Generalization(String id) {
        this.id = new Identifier(id);
    }

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        // todo : complete
        return this;
    }
}
