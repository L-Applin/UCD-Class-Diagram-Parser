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
    public void tokenize(UmlContext ctx, String content) {
        //todo: complete

    }

    public String getValue(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
