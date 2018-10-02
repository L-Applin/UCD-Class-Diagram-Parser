package syntaxTree.expressions;

import syntaxTree.UmlContext;

/**
 * General representation of an Expression of the BNF grammar.
 */
public interface Expression {

    /**
     * Method used to recursively parse the .ucd file.
     * @param ctx
     * @param content
     * @return
     */
    Expression tokenize(final UmlContext ctx, String content);

}