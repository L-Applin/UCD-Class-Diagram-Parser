package parsing.syntaxTree.expressions;

import token.UmlContext;

/**
 * General representation of an Expression of the BNF grammar.<p></p>
 * Every grammar rule of the BNF syntax must be declared as a class and must implement this interface.
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
