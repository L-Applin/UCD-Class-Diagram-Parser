package syntaxTree.expressions;

import parsing.UcdParser;
import token.UmlContext;
import syntaxTree.entries.RoleEntry;
import utils.Utils.*;

/**
 * <role> ::= "CLASS" IDENTIFIER <multiplicity>
 */
public class Role implements Expression {

    private Identifier associationId, classId;
    private Expression multiplicity;

    public Role(Identifier associationId) {
        this.associationId = associationId;
    }

    public Identifier getClassId() {
        return classId;
    }

    public Expression getMultiplicity() {
        return multiplicity;
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        Log.test("ROLE :", content);
        UcdParser parser = new UcdParser(content);
        RoleEntry splits = parser.convertRolesEntry(associationId.getValue());
        classId = new Identifier(splits.classId);
        multiplicity = new Multiplicity(associationId).tokenize(ctx, splits.multiplicity);
        Log.all("\trole =", classId.getValue(), ((Multiplicity) multiplicity).getMultiplicity().toString());

        return this;

    }

    @Override
    public String toString() {
        return "Role {" +
                "associationId = " + associationId.toString() +
                ", classId =" + classId.toString() +
                ", multiplicity = " + multiplicity.toString() +
                "}";
    }

    public String getMultiplicityValueAsString(){
        return ((Multiplicity) multiplicity).getMultiplicity().toString();
    }

}
