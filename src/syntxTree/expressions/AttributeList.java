package syntxTree.expressions;

import parsing.UcdParser;
import syntxTree.UmlContext;
import utils.Utils.*;

import java.util.List;

/**
 * <attribute_list> ::= [<data_item> {“,” <data_item>}]
 */
public class AttributeList implements Expression {

    private List<Expression> attributes;
    private Identifier classId;

    public AttributeList(List<Expression> attributes, Identifier classId) {
        this.attributes = attributes; this.classId = classId;
    }

    @Override
    public Expression tokenize(UmlContext ctx, String content) {
        Log.all("\tAttributes"); // todo : remove (debug)
        UcdParser parser = new UcdParser(content);
        List<String> stringAttributes = parser.splitList();
        stringAttributes.forEach(attr -> {
            if (attr != null && attr.length() > 0) {
                attributes.add(new DataItem().tokenize(ctx, attr));
            }
        });
        return this;
    }

}
