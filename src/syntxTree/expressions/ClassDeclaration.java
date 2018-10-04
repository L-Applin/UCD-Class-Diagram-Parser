package syntxTree.expressions;

import syntxTree.UmlContext;
import syntxTree.exceptions.ExceptionCheckProvider;

import static utils.Utils.*;

/**
 * <class_dec> ::= “CLASS” IDENTIFIER <class_content>
 */
public class ClassDeclaration extends Declaration implements ExceptionCheckProvider {

    private Identifier id;
    private Expression classContent;

    public ClassDeclaration(String id) {
        this.id = new Identifier(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        checkClassContent(id.getValue(), content); // from ExceptionCheckProvider interface
        ctx.createClass(id.toString(), content);
        classContent = new ClassContent(id).tokenize(ctx, content);
        return this;
    }

    @Override
    public String toString() {
        return "\n\n\tClassDeclaration \n" +
                "\t\tid=" + id.toString() +
                ", classContent=" + classContent.toString();

    }
}
