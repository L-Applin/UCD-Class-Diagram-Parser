package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <model> ::= “MODEL” IDENTIFIER <list_dec> ;
 */
public class Model implements Expression {

    private Expression listDec;
    private Identifier id;

    public Model(final String  id, final ListDeclaration decs) {
        this.id = new Identifier(id);
        this.listDec = decs;
    }

    @Override
    public void tokenize(UmlContext ctx) {
        //todo: complete
    }

}
