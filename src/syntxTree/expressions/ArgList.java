package syntxTree.expressions;

import parsing.UcdParser;
import syntxTree.UmlContext;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * <arg_list> ::= [<data_item> {“,” <data_item>}]
 */
public class ArgList implements Expression {

    private Identifier methodId, classId;
    private List<Expression> dataItems;

    public ArgList(Identifier methodId, Identifier classId) {
        this.methodId = methodId; this.classId = classId;
        dataItems = new ArrayList<>();
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        UcdParser parser = new UcdParser(content);
        List<String> args = parser.splitArgs();
        args.forEach(arg -> {
            if (arg != null && arg.length() > 0){
                DataItem item = new DataItem(methodId).tokenize(ctx, arg);
                dataItems.add(item);
                ctx.getUmlClass(classId.getValue()).getOperation(methodId.getValue()).addArgument(item);
            }
        });
        return this;
    }



}
