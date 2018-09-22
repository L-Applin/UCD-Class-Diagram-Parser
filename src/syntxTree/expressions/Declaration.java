package syntxTree.expressions;

import parsing.GrammarModel;
import parsing.UcdParser;
import syntxTree.DeclarationEntry;
import syntxTree.UmlContext;


/**
 * <declaration> ::= <class_dec> “;”
 *    | <association> “;”
 *    | <generalization> “;”
 *    | <aggregation> “;”
 */
public class Declaration implements Expression {

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        //todo : check which declration class in instanciate
        UcdParser parser = new UcdParser(content);
        DeclarationEntry entry = parser.splitDeclarationEntry();
        String tagId = entry.getDecType();
        String expression = entry.getContent();
        switch (tagId) {
            case GrammarModel.Decs.CLASS :
                return new ClassDeclaration().tokenize(ctx, expression);
            case GrammarModel.Decs.AGGREGATION:
                return new Aggregation().tokenize(ctx, expression);
            case GrammarModel.Decs.GENERALIZATION:
                return new Generalization().tokenize(ctx, expression);
            case GrammarModel.Decs.ASSOCIATION:
                return new Association().tokenize(ctx, expression);
            default: //todo : throw exception ?
        }
        return this;
    }
}
