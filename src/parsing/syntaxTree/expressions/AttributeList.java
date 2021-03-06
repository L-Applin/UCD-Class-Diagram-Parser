package parsing.syntaxTree.expressions;

import app.Utils;
import parsing.UcdParser;
import token.UmlContext;

import java.util.List;

/**
 * <attribute_list> ::= [<data_item> {"," <data_item>}]
 */
public class AttributeList implements Expression {

    private List<Expression> attributes;
    private Identifier classId;

    public AttributeList(List<Expression> attributes, Identifier classId) {
        this.attributes = attributes;
        this.classId = classId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression tokenize(final UmlContext ctx, String content) throws IllegalAccessException {
        UcdParser parser = new UcdParser(content);
        List<String> stringAttributes = parser.splitAttributeList();
        for (String attr : stringAttributes){
            if (attr != null && attr.length() > 0) {
                DataItem attributeData = new DataItem(classId).tokenize(ctx, attr);
                attributes.add(attributeData);
                ctx.addAttributeToMethod(
                        classId.getValue(),
                        attributeData.getIdAsString(),
                        attributeData.getTypeAsString(),
                        attr);
            }
        }
        return this;
    }

}
