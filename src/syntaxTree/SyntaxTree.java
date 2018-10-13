package syntaxTree;

import syntaxTree.expressions.Expression;
import syntaxTree.expressions.Model;
import token.UmlContext;

/**
 * Entry point for parsing the .ucd file
 */
public class SyntaxTree implements Expression {

    private Expression root;

    /**
     * The full context of the parsed tree. Contains all variable / identifiers and methods to fetch them.
     */
    private UmlContext ctx;
    public UmlContext getCtx() { return ctx; }


    public SyntaxTree(UmlContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        root = new Model().tokenize(this.ctx, content);
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
