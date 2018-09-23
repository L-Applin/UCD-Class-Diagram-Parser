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

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        checkClassContent(id.getValue(), content); // from ExceptionCheckProvider interface
        Log.all("\nClass : ", id.toString()); //todo : remove (debug)
        classContent = new ClassContent(id).tokenize(ctx, content);
        ctx.put(id.toString(), classContent);
        return this;
    }


}
