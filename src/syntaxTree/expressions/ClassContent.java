package syntaxTree.expressions;

import parsing.UcdParser;
import syntaxTree.UmlContext;
import syntaxTree.exceptions.ExceptionCheckProvider;

import java.util.ArrayList;



/**
 * <class_content> ::= “ATTRIBUTES” <attribute_list> “OPERATIONS” <operation_list>
 */
public class ClassContent implements Expression, ExceptionCheckProvider {

    private Expression attributeList, operationList;
    private Identifier classId;

    public ClassContent(Identifier classId) {
        this.classId = classId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression tokenize(final UmlContext ctx, String content) {

        UcdParser parser = new UcdParser(content);

        String attributes = parser.extractAttributes(classId.getValue(), content);
        attributeList = new AttributeList(new ArrayList<>(), classId).tokenize(ctx, UcdParser.removeNewLines(attributes));

        String operations = parser.extractOperations(classId.getValue(), content);
        operationList = new OperationList(new ArrayList<>(), classId).tokenize(ctx, UcdParser.removeNewLines(operations));

        return this;

    }

    @Override
    public String toString() {
        return "ClassContent {" +
                "attributeList = " + attributeList.toString() +
                ", operationList = " + operationList.toString() +
                ", classId = " + classId.toString() +
                '}';
    }
}
