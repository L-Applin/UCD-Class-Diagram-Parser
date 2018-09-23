package syntxTree.expressions;

import parsing.UcdParser;
import syntxTree.UmlContext;

/**
 * <two_roles> ::= <role> “,” <role>
 */
public class TwoRole implements Expression {

    private Expression role1, role2;
    private Identifier associationId;

    public TwoRole(Identifier associationId) {
        this.associationId = associationId;
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {

        UcdParser parser = new UcdParser(content);
        String[] roles = parser.splitTwoRoles(associationId.getValue());
        role1 = new Role(associationId).tokenize(ctx, roles[0]);
        role2 = new Role(associationId).tokenize(ctx, roles[1]);

        return this;

    }

}
