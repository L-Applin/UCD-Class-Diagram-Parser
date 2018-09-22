package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <class_dec> ::= “CLASS” IDENTIFIER <class_content>
 */
public class ClassDeclaration extends Declaration {

    private Identifier id;
    private Expression classContent;

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        // todo: extract class infos
        return this;

    }
}
