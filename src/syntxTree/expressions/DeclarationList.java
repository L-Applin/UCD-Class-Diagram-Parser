package syntxTree.expressions;

import parsing.UcdParser;
import syntxTree.UmlContext;

import java.util.List;

/**
 * <list_dec> ::= {<declaration>}
 */
public class DeclarationList implements Expression {

    private List<Expression> decs;

    public DeclarationList(List<Expression> decs){
        this.decs = decs;
    }

    @Override
    public Expression tokenize(UmlContext ctx, String content) {

        UcdParser parser = new UcdParser(content);

        List<String> stringDecs = parser.splitDeclarations();
        stringDecs.forEach( declaration -> {
            if (declaration.length() > 0){
                decs.add(new Declaration().tokenize(ctx, declaration));
            }
        });
        return this;
    }

}
