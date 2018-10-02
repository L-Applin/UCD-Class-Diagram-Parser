package syntaxTree.expressions;

import parsing.UcdParser;
import syntaxTree.UmlContext;

import java.util.ArrayList;
import java.util.List;

/**
 * <operation_list>::= [<operation> {“,” <operation>}]
 */
public class OperationList implements Expression {

    private ArrayList<Expression> opList;
    private Identifier classId;

    public OperationList(ArrayList<Expression> opList, Identifier classId) {
        this.opList = opList; this.classId = classId;
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        UcdParser parser = new UcdParser(content);
        List<String> opStringList = parser.splitList();
        opStringList.forEach( op -> {
            if (op != null && op.length() > 0) {
                opList.add(new Operation(classId).tokenize(ctx, op));
            }
        });
        return this;

    }
}
