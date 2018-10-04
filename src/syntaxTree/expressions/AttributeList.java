package syntaxTree.expressions;

import parsing.UcdParser;
import syntaxTree.UmlContext;
import utils.Utils;

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
        Utils.Log.test("ATTR ClassID", classId.getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        UcdParser parser = new UcdParser(content);
        List<String> stringAttributes = parser.splitList();
        stringAttributes.forEach(attr -> {
            if (attr != null && attr.length() > 0) {
                DataItem attributeData = new DataItem(classId).tokenize(ctx, attr);
                attributes.add(attributeData);
                ctx.getUmlClass(classId.getValue()).createAttributes(
                        attributeData.getIdAsString(), attributeData.getTypeAsString(), content);
            }
        });
        return this;
    }

}
