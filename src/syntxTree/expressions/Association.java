package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <association> ::= “RELATION” IDENTIFIER “ROLES” <two_roles> ;
 */
public class Association implements Expression {

    private Expression twoRole;
    private Identifier id;

    @Override
    public void tokenize(UmlContext ctx) {
        //todo: complete

    }
}
