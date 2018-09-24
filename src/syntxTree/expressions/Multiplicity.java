package syntxTree.expressions;

import parsing.GrammarModel;
import syntxTree.UmlContext;
import syntxTree.exceptions.MalformedDeclarationException;

/**
 * <multiplcity> ::=
 *    “ONE”
 *    | “MANY”
 *    | “ONE_OR_MANY”
 *    | “OPTIONALLY_ONE”
 *    | “UNDEFINED”
 */
public class Multiplicity implements Expression {

    private Identifier associationId;
    private MultiplicityValue mulitplicity;
    public MultiplicityValue getMulitplicity(){ return mulitplicity; }


    public Multiplicity(Identifier associationId) {
        this.associationId = associationId;
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        mulitplicity = fromString(content);
        return this;

    }

    public enum MultiplicityValue {
        MANY, ONE_OR_MANY, OPTIONALLY_ONE, UNDEFINED, ONE
    }

    public MultiplicityValue fromString(String value){
        switch (value){
            case GrammarModel.Mult.ONE : return MultiplicityValue.ONE;
            case GrammarModel.Mult.MANY : return MultiplicityValue.MANY;
            case GrammarModel.Mult.ONE_OR_MANY : return MultiplicityValue.ONE_OR_MANY;
            case GrammarModel.Mult.OPTIONALLY_ONE : return MultiplicityValue.OPTIONALLY_ONE;
            case GrammarModel.Mult.UNDEFINED : return MultiplicityValue.UNDEFINED;
            default: throw new MalformedDeclarationException(
                    String.format("Cannot parse multiplicity value : '%s' of association '%s'. " +
                            "Multiplicity value must be one of " +
                            "'ONE', 'MANY', 'ONE_OR_MANY', 'OPTIONALLY_ONE' or 'UNDEFINED'",
                            value, associationId.getValue()));

        }
    }

    @Override
    public String toString() {
        return mulitplicity.toString();
    }
}
