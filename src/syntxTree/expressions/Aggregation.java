package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <aggregation> ::= “AGGREGATION” “CONTAINER” role “PARTS” roles;
 */
public class Aggregation implements Expression {

    private Expression role, roles;

    @Override
    public void tokenize(UmlContext ctx) {
        // todo : complete
    }
}
