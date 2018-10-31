package parsing.syntaxTree.expressions;

import parsing.GrammarModel;
import token.UmlContext;

import java.util.ArrayList;
import java.util.List;

import static parsing.GrammarModel.LIST_SEPERATOR;

public class Roles implements Expression {

    private List<Expression> roleList;
    public List<Expression> getRoleList() { return roleList; }

    public Roles() {
        this.roleList = new ArrayList<>();
    }

    @Override
    public Expression tokenize(final UmlContext ctx, String content) {
        String[] rolesString = content.split(LIST_SEPERATOR);
        for (String role:rolesString){
            roleList.add(new Role(new Identifier(GrammarModel.PARTS_TAG)).tokenize(ctx, role));
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        roleList.forEach(role -> sb.append(role.toString()).append(", "));
        return "Roles {" +
                "roleList = { " + sb.toString().substring(0, sb.length() - 2)
                + "}}";
    }
}
