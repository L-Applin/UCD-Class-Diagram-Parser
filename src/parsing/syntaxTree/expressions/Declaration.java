package parsing.syntaxTree.expressions;

import parsing.GrammarModel;
import parsing.UcdParser;
import parsing.syntaxTree.entries.DeclarationEntry;
import parsing.syntaxTree.exceptions.MalformedFileException;
import token.UmlContext;


/**
 * <declaration> ::= <class_dec> ";"
 *    | <association> ";"
 *    | <generalization> ";"
 *    | <aggregation> ";"
 */
public class Declaration implements Expression {

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression tokenize(final UmlContext ctx, String content) throws IllegalAccessException {

        UcdParser parser = new UcdParser(content);
        DeclarationEntry entry = parser.convertDeclarationEntry();

        switch (entry.decType) {
            case GrammarModel.Decs.CLASS :
                return new ClassDeclaration(entry.id).tokenize(ctx, entry.content);
            case GrammarModel.Decs.AGGREGATION:
                return new Aggregation().tokenize(ctx, entry.content);
            case GrammarModel.Decs.GENERALIZATION:
                return new Generalization(entry.id).tokenize(ctx, entry.content);
            case GrammarModel.Decs.ASSOCIATION:
                return new Association(entry.id).tokenize(ctx, entry.content);
            default: throw new MalformedFileException();
        }

    }
}
