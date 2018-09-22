package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <declaration> ::= <class_dec> “;”
 *    | <association> “;”
 *    | <generalization> “;”
 *    | <aggregation> “;”;
 */
public class Declaration implements Expression {

    private Expression classDeclaration, association, generalization, aggregation;

    @Override
    public void tokenize(UmlContext ctx) {
        //todo: complete

    }
}
