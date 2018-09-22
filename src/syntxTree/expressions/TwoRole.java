package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <two_roles> ::= <role> “,” <role>
 */
public class TwoRole implements Expression {

    private Expression role1, role2;

    @Override
    public void tokenize(UmlContext ctx, String content) {
        //todo: complete
    }

}
