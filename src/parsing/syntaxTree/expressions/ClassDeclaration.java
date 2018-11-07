package parsing.syntaxTree.expressions;

import parsing.UcdParser;
import parsing.syntaxTree.exceptions.ExceptionCheckProvider;
import token.UmlContext;

/**
 * <class_dec> ::= "CLASS" IDENTIFIER <class_content>
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
    public Expression tokenize(final UmlContext ctx, String content) throws IllegalAccessException {
    	checkClassContent(content); // from ExceptionCheckProvider interface
        UcdParser p = new UcdParser(content);
        ctx.createClass(UcdParser.removeSpaces(id.toString()), p.formatContent());
        classContent = new ClassContent(id).tokenize(ctx, content);
        System.out.println(id.getValue());
        return this;
    }

    @Override
    public String toString() {
        return "\n\n\tClassDeclaration \n" +
                "\t\tid=" + id.toString() +
                ", classContent=" + classContent.toString();

    }
}
