package parsing.syntaxTree.expressions;

import app.Utils;
import parsing.UcdParser;
import parsing.syntaxTree.exceptions.MalformedFileException;
import token.UmlAssociation;
import token.UmlClass;
import token.UmlContext;

/**
 * <two_roles> ::= <role> "," <role>
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
        String[] roles = parser.splitTwoRoles();
        role1 = new Role(associationId).tokenize(ctx, roles[0]);
        role2 = new Role(associationId).tokenize(ctx, roles[1]);

        UmlClass first = ctx.getUmlClass(((Role) role1).getClassId().getValue());
        UmlClass second = ctx.getUmlClass(((Role) role2).getClassId().getValue());
        UmlAssociation umlAssociation =
                new UmlAssociation(associationId.toString(),
                        first, ((Role) role1).getMultiplicity().toString(),
                        second, ((Role) role2).getMultiplicity().toString(),
                        parser.formatContent());

        try {

            Utils.Log.test(associationId.toString());

            if (first.getAssociations().containsKey(associationId.getValue())){
                throw new MalformedFileException();
            }
            if (second.getAssociations().containsKey(associationId.getValue())){
                throw new MalformedFileException();
            }

            first.getAssociations().put(associationId.getValue(), umlAssociation);
            second.getAssociations().put(associationId.getValue(), umlAssociation);

        } catch (NullPointerException npe){
            throw new MalformedFileException();
        }
        return this;

    }


    @Override
    public String toString() {
        return "TwoRole { " +
                "role1 = " + role1 +
                ",role2 = " + role2 +
                ",associationId = " + associationId +
                "}";
    }
}
