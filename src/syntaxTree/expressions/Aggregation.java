package syntaxTree.expressions;

import parsing.GrammarModel;
import parsing.UcdParser;
import syntaxTree.UmlContext;
import token.UmlAggregation;
import token.UmlClass;

/**
 * <aggregation> ::= “AGGREGATION” “CONTAINER” role “PARTS” roles
 */
public class Aggregation implements Expression {

    private Expression role, roles;

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression tokenize(final UmlContext ctx, String content) {

        UcdParser parser = new UcdParser(content);
        parser.checkValidAggregations(content);

        String roleString = parser.extractBetween(GrammarModel.CONTAINER_TAG, GrammarModel.PARTS_TAG);
        role = new Role(new Identifier(GrammarModel.Decs.AGGREGATION)).tokenize(ctx, UcdParser.removeNewLines(roleString));

        String partsString = parser.extractcParts();
        roles = new Roles().tokenize(ctx, partsString);

        createAggregaton(ctx, content);

        return this;
    }

    @Override
    public String toString() {
        return "\n\n\tAggregation \n" +
                "\t\trole = " + role.toString() +
                ", roles = " + roles.toString();
    }


    private void createAggregaton(UmlContext ctx, String content){

        UmlClass containerClass = ctx.getUmlClass(((Role) role).getClassId().getValue());

        ((Roles) roles).getRoleList().forEach( partRole -> {
            UmlAggregation agg = new UmlAggregation(containerClass, content);
            UmlClass partClass = ctx.getUmlClass(((Role) partRole).getClassId().getValue());
            agg.setPart(new UmlAggregation.PartsEntry(partClass, ((Role) partRole).getMultiplicityValueAsString()));
            containerClass.getAgregations().add(agg);
        });

    }
}
