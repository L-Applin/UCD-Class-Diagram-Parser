package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * General representation of an Expression of the BNF grammar.
 */
public interface Expression {

    void tokenize(UmlContext ctx, String content);

}
