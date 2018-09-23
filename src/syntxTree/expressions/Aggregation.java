package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <aggregation> ::= “AGGREGATION” “CONTAINER” role “PARTS” roles
 */
public class Aggregation implements Expression {

    private Expression role, roles;

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        // todo : complete
        return this;
    }
}
