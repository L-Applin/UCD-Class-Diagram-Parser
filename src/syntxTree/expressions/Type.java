package syntxTree.expressions;

import syntxTree.UmlContext;

public class Type implements Expression {

    private Identifier id;

    public Type(Identifier id) {
        this.id = id;
    }

    @Override
    public void tokenize(UmlContext ctx) {
        //todo: complete

    }

}
