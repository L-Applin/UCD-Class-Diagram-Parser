package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <association> ::= “RELATION” IDENTIFIER “ROLES” <two_roles>
 */
public class Association implements Expression {

    private Expression twoRole;
    private Identifier id;

    public Association(String id) {
        this.id = new Identifier(id);
    }

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        //todo: complete
        return this;

    }
}
