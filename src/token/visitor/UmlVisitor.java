package token.visitor;

import token.*;

/**
 * Must be carfull with cyclic  !
 */
public interface UmlVisitor {
    void visit(UmlAggregation aggregation);
    void visit(UmlAssociation association);
    void visit(UmlAttribute attribute);
    void visit(UmlClass umlClass);
    void visit(UmlOperation operation);
}