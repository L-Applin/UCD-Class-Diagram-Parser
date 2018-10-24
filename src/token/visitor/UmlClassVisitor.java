package token.visitor;

import token.*;

public class UmlClassVisitor implements UmlVisitor {

    protected UmlVisitorAggregationRunnable aggregationVisitor;
    protected UmlVisitorAssociationRunnable associationVisitor;
    protected UmlVisitorClassRunnable classVisitor;
    protected UmlVisitorOperationRunnable operationVisitor;
    protected UmlVisitorAttributeRunnable attributeVisitor;

    public UmlClassVisitor(UmlVisitorClassRunnable classVisitor) {
        this.classVisitor = classVisitor;
    }

    public UmlClassVisitor() { }

    public UmlClassVisitor setAggregationVisitor(UmlVisitorAggregationRunnable aggregationVisitor) {
        this.aggregationVisitor = aggregationVisitor;
        return this;
    }

    public UmlClassVisitor setAssociationVisitor(UmlVisitorAssociationRunnable associationVisitor) {
        this.associationVisitor = associationVisitor;
        return this;
    }

    public UmlClassVisitor setClassVisitor(UmlVisitorClassRunnable classVisitor) {
        this.classVisitor = classVisitor;
        return this;
    }

    public UmlClassVisitor setOperationVisitor(UmlVisitorOperationRunnable operationVisitor) {
        this.operationVisitor = operationVisitor;
        return this;
    }

    public UmlClassVisitor setAttributeVisitor(UmlVisitorAttributeRunnable attributeVisitor) {
        this.attributeVisitor = attributeVisitor;
        return this;
    }

    public void visit(UmlAggregation aggregation) {
        if (attributeVisitor != null) {
            aggregationVisitor.visit(aggregation);
        }
    }

    public void visit(UmlAssociation association) {
        if (associationVisitor != null) {
            associationVisitor.visit(association);
        }
    }

    public void visit(UmlAttribute attribute) {
        if (attributeVisitor != null) {
            attributeVisitor.visit(attribute);
        }
    }

    public void visit(UmlOperation operation) {
        if (operationVisitor != null) {
            operationVisitor.visit(operation);
        }
    }

    public void visit(UmlClass umlClass) {
        if(classVisitor != null) {
            classVisitor.visit(umlClass);
        }
    }

    public interface UmlVisitorClassRunnable {
        void visit(UmlClass token);
    }

    public interface UmlVisitorAggregationRunnable {
        void visit(UmlAggregation token);
    }

    public interface UmlVisitorAssociationRunnable {
        void visit(UmlAssociation token);
    }

    public interface UmlVisitorAttributeRunnable {
        void visit(UmlAttribute token);
    }

    public interface UmlVisitorOperationRunnable {
        void visit(UmlOperation token);
    }


}
