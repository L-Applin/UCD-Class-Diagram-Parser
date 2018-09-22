package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <role> ::= “CLASS” IDENTIFIER <multiplicity> ;
 */
public class Role implements Expression {

    private Identifier id;
    private Expression multiplicity;

    @Override
    public void tokenize(UmlContext ctx) {
        //todo: complete

    }
}
