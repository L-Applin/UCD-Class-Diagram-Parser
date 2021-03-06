package parsing.syntaxTree.expressions;

import parsing.UcdParser;
import token.UmlContext;

import java.util.List;

/**
 * <list_dec> ::= {<declaration>}
 */
public class DeclarationList implements Expression {

    private List<Expression> decs;

    public DeclarationList(List<Expression> decs){
        this.decs = decs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression tokenize(final UmlContext ctx, String content) throws IllegalAccessException {

        UcdParser parser = new UcdParser(content);

        List<String> stringDecs = parser.splitDeclarations();
        for (String declaration : stringDecs){
            if (declaration.length() > 0){
                decs.add(new Declaration().tokenize(ctx, declaration));
            }
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nDeclarations : ");
        decs.forEach( dec -> sb.append(dec.toString()));
        sb.append("\n}");
        return sb.toString();
    }
}
