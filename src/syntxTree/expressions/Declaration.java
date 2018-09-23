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

        UcdParser parser = new UcdParser(content);
        DeclarationEntry entry = parser.splitDeclarationEntry();

        switch (entry.getDecType()) {
            case GrammarModel.Decs.CLASS :
                return new ClassDeclaration(entry.getId()).tokenize(ctx, entry.getContent());
            case GrammarModel.Decs.AGGREGATION:
                return new Aggregation().tokenize(ctx, entry.getContent());
            case GrammarModel.Decs.GENERALIZATION:
                return new Generalization(entry.getId()).tokenize(ctx, entry.getContent());
            case GrammarModel.Decs.ASSOCIATION:
                return new Association(entry.getId()).tokenize(ctx, entry.getContent());
            default: //todo : throw exception ?
        }

        return this;
    }
}
