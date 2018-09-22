package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <multiplcity> ::= “ONE”
 *    | “MANY”
 *    | “ONE_OR_MANY”
 *    | “OPTIONALLY_ONE”
 *    | “UNDEFINED”
 */
public class Multiplicity implements Expression {

    private MultiplicityValue mulitplicity;

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        // todo : complete
        return this;

    }

    public enum MultiplicityValue {
        MANY, ONE_OR_MANY, OPTIONNALY_ONE, UNDEFINED
    }

}
