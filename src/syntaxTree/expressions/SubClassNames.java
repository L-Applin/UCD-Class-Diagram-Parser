package syntaxTree.expressions;

import parsing.UcdParser;
import syntaxTree.UmlContext;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SubClassNames implements Expression {

    private List<Identifier> subClassList;
    private Identifier parentClassId;


    public SubClassNames(Identifier parentClassId) {
        this.parentClassId = parentClassId;
        subClassList = new ArrayList<>();
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        UcdParser parser = new UcdParser(content);
        List<String> classList = parser.splitList();
        return this;
    }



}
