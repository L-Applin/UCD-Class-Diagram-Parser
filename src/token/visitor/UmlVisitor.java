package token.visitor;

import token.*;

/**
 * Must be carefull with cyclic !
 */
public interface UmlVisitor {
    void visit(UmlAggregation aggregation);
    void visit(UmlAssociation association);
    void visit(UmlAttribute attribute);
    void visit(UmlOperation operation);
    void visit(UmlClass umlClass);
}
