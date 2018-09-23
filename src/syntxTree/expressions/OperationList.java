package syntxTree.expressions;

import parsing.UcdParser;
import syntxTree.UmlContext;

import java.util.ArrayList;
import java.util.List;

import static utils.Utils.*;

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
    public Expression tokenize(UmlContext ctx, String content) {
        Log.all("\tOperations"); // todo : remove (debug)
        UcdParser parser = new UcdParser(content);
        List<String> opStringList = parser.splitList();
        opStringList.forEach( op -> {
            if (op != null && op.length() > 0) {
                opList.add(new Operation().tokenize(ctx, op));
            }
        });
        return this;

    }
}
