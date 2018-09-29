package syntxTree.expressions;

import parsing.UcdParser;
import syntxTree.UmlContext;
import syntxTree.entries.RoleEntry;
import utils.Utils.*;

/**
 * <role> ::= “CLASS” IDENTIFIER <multiplicity>
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
        classId = new Identifier(splits.getClassId());
        multiplicity = new Multiplicity(associationId).tokenize(ctx, splits.getMultiplicity());
        Log.all("\trole =", classId.getValue(), ((Multiplicity) multiplicity).getMulitplicity().toString());

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
        return ((Multiplicity) multiplicity).getMulitplicity().toString();
    }

}
