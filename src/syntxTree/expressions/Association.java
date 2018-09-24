package syntxTree.expressions;

import syntxTree.UmlContext;
import utils.Utils;

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
    public Expression tokenize(final UmlContext ctx, String content) {
        Utils.Log.all("\nAssocitaion :", id.getValue());
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
