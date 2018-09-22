package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <class_dec> ::= “CLASS” IDENTIFIER <class_content>
 */
public class ClassDeclaration implements Expression {

    private Identifier id;
    private Expression classContent;

    @Override
    public void tokenize(UmlContext ctx, String content) {
        //todo: complete

    }
}
