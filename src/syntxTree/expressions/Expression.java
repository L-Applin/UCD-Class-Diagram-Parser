package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * General representation of an Expression of the BNF grammar.
 */
public interface Expression {

    Expression tokenize(final UmlContext ctx, String content);

}
