package syntaxTree.expressions;

import parsing.GrammarModel;
import parsing.UcdParser;
import syntaxTree.entries.DeclarationEntry;
import syntaxTree.UmlContext;
import syntaxTree.exceptions.IncompatibleTagException;


/**
 * <declaration> ::= <class_dec> “;”
 *    | <association> “;”
 *    | <generalization> “;”
 *    | <aggregation> “;”
 */
public class Declaration implements Expression {

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression tokenize(final UmlContext ctx, String content) {

        UcdParser parser = new UcdParser(content);
        DeclarationEntry entry = parser.convertDeclarationEntry();

        switch (entry.getDecType()) {
            case GrammarModel.Decs.CLASS :
                return new ClassDeclaration(entry.getId()).tokenize(ctx, entry.getContent());
            case GrammarModel.Decs.AGGREGATION:
                return new Aggregation().tokenize(ctx, entry.getContent());
            case GrammarModel.Decs.GENERALIZATION:
                return new Generalization(entry.getId()).tokenize(ctx, entry.getContent());
            case GrammarModel.Decs.ASSOCIATION:
                return new Association(entry.getId()).tokenize(ctx, entry.getContent());
            default: throw new IncompatibleTagException(entry.getDecType(), content);
        }

    }
}