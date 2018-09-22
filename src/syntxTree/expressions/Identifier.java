package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * Final expression
 */
public class Identifier implements Expression {

    private String name;

    public Identifier(final String name) {
        this.name = name;
    }

    @Override
    public void tokenize(UmlContext ctx) {
        //todo: complete

    }

}
