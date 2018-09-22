package syntxTree.expressions;

import syntxTree.UmlContext;

public class Generalization implements Expression {

    private Identifier id;
    private Expression subClassNames;

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        // todo : complete
        return this;
    }
}
