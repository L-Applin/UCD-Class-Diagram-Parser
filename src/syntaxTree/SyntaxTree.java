package syntaxTree;

import syntaxTree.expressions.Expression;
import syntaxTree.expressions.Model;

/**
 * Entry point for parsing the .ucd file
 */
public class SyntaxTree implements Expression {

    private Expression root;


    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        root = new Model().tokenize(ctx, content);
        return this;
    }

    @Override
    public String toString() {
        return "\n\n===============================================================" +
                 "\n===================== UCD FILE PARSE TREE =====================\n" +
                "===============================================================\n\n" +
                "root : " + root.toString();
    }
}
