package parsing.syntaxTree.expressions;

import token.UmlContext;

/**
 * <association> ::= "RELATION" IDENTIFIER "ROLES" <two_roles>
 */
public class Association implements Expression {

    private Expression twoRole;
    private Identifier id;

    public Association(String id) {
        this.id = new Identifier(id);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        twoRole = new TwoRole(id).tokenize(ctx, content);
        return this;

    }

    @Override
    public String toString() {
        return "\n\n\tAssociation \n" +
                "\t\tid = " + id.toString() +
                "twoRole = " + twoRole.toString();
    }
}
