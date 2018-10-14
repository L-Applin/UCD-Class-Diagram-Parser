package token.visitor;

import token.*;

public class SuperClassAssignationVisitor implements UmlVisitor {
    @Override public void visit(UmlAggregation aggregation) { }
    @Override public void visit(UmlAssociation association) { }
    @Override public void visit(UmlAttribute attribute) { }
    @Override public void visit(UmlOperation operation) { }

    @Override
    public void visit(UmlClass umlClass) {
        umlClass.getSubClasses().values().forEach(child -> {
            ((UmlClass)child).setSuperClass(umlClass);
        });
    }

}
