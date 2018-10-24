package parsing.syntaxTree.expressions;

import token.UmlContext;

/**
 * Final expression
 */
public class Identifier implements Expression {

    private String name;

    public Identifier(final String name) {
        this.name = name;
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
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
