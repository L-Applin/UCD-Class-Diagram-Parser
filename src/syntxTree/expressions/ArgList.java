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

    private List<Expression> dataItems;
    private Identifier methodId;

    public ArgList(Identifier methodId) {
        this.methodId = methodId;
        dataItems = new ArrayList<>();
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        UcdParser parser = new UcdParser(content);
        List<String> args = parser.splitArgs();
        args.forEach(arg -> {
            if (arg != null && arg.length() > 0){
                dataItems.add(new DataItem(methodId).tokenize(ctx, arg));
            }
        });
        return this;
    }

}
