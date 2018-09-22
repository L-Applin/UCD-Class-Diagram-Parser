package syntxTree.expressions;

import syntxTree.UmlContext;

public class Multiplicity implements Expression {

    private MultiplicityValue mulitplicity;

    @Override
    public void tokenize(UmlContext ctx) {
        // todo : complete
    }

    public enum MultiplicityValue {
        MANY, ONE_OR_MANY, OPTIONNALY_ONE, UNDEFINED
    }

}
