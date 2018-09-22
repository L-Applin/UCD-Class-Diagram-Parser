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
    public Expression tokenize(UmlContext ctx, String content) {
        //todo: complete
        return this;

    }

    public String getValue(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
