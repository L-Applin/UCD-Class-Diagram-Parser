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
    public void tokenize(UmlContext ctx, String content) {
        // todo : complete
    }

    public enum MultiplicityValue {
        MANY, ONE_OR_MANY, OPTIONNALY_ONE, UNDEFINED
    }

}
