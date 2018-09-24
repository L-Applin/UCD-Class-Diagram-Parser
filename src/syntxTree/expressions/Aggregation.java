package syntxTree.expressions;

import parsing.GrammarModel;
import parsing.UcdParser;
import syntxTree.UmlContext;
import utils.Utils.*;

/**
 * <aggregation> ::= “AGGREGATION” “CONTAINER” role “PARTS” roles
 */
public class Aggregation implements Expression {

    private Expression role, roles;

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {

        UcdParser parser = new UcdParser(content);
        parser.checkValidAggregations(content);

        String roleString = parser.extractBetween(GrammarModel.CONTAINER_TAG, GrammarModel.PARTS_TAG);
        role = new Role(new Identifier(GrammarModel.Decs.AGGREGATION)).tokenize(ctx, UcdParser.removeNewLines(roleString));

        String partsString = parser.extractcParts();
        roles = new Roles().tokenize(ctx, partsString);

        return this;
    }

    @Override
    public String toString() {
        return "\n\n\tAggregation \n" +
                "\t\trole = " + role.toString() +
                ", roles = " + roles.toString();
    }
}
