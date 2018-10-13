package token.visitor;

import token.*;

public class UmlChildClassVisitor implements UmlVisitor {

    // unused for this implementation
    @Override public void visit(UmlAggregation aggregation){ }
    @Override public void visit(UmlAssociation association){ }
    @Override public void visit(UmlAttribute attribute){ }
    @Override public void visit(UmlOperation operation){ }

    @Override
    public void visit(UmlClass umlClass) {
        System.out.println(umlClass.getName());
    }
}
