package syntxTree.expressions;

import parsing.UcdParser;
import syntxTree.UmlContext;
import syntxTree.entries.RoleEntry;
import utils.Utils;

/**
 * <role> ::= “CLASS” IDENTIFIER <multiplicity>
 */
public class Role implements Expression {

    private Identifier associationId, classId;
    private Expression multiplicity;


    public Role(Identifier associationId) {
        this.associationId = associationId;
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        Utils.Log.all("role = ", content);
        UcdParser parser = new UcdParser(content);
        RoleEntry splits = parser.convertRolesEntry(associationId.getValue());
        classId = new Identifier(splits.getClassId());
        multiplicity = new Multiplicity().tokenize(ctx, splits.getMultiplicity());

        return this;

    }
}
