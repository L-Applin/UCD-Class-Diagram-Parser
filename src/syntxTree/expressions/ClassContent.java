package syntxTree.expressions;

import syntxTree.UmlContext;

/**
 * <class_content> ::= “ATTRIBUTES” <attribute_list> “OPERATIONS” <operation_list>
 */
public class ClassContent implements Expression {

    private Expression attributeList, operationList;

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        //todo: complete
        return this;

    }

}
