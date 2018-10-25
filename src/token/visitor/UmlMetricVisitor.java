package token.visitor;

import token.*;

public class UmlMetricVisitor implements UmlVisitor {

    /**
     * A value to represent the calculated metric.
     */
    private double  value;
    public  double  getValue()                      { return value; }
    public  void    setValue(double value)          { this.value = value; }
    public  void    incrementValue(double value)    { this.value += value;  }
    public  void    incrementValue()                { this.value++; }


    // all runnables
    private UmlVisitorAggregationRunnable   aggregationVisitor;
    private UmlVisitorAssociationRunnable   associationVisitor;
    private UmlVisitorClassRunnable         classVisitor;
    private UmlVisitorOperationRunnable     operationVisitor;
    private UmlVisitorAttributeRunnable     attributeVisitor;
    private UmlVisitorMetricRunnable        metricVisitor;

    public UmlMetricVisitor(UmlVisitorClassRunnable classVisitor) {
        this();
        this.classVisitor = classVisitor;
    }

    public UmlMetricVisitor() {
        this.value = 0;
    }

    public UmlMetricVisitor setAggregationVisitor(UmlVisitorAggregationRunnable aggregationVisitor) {
        this.aggregationVisitor = aggregationVisitor;
        return this;
    }

    public UmlMetricVisitor setAssociationVisitor(UmlVisitorAssociationRunnable associationVisitor) {
        this.associationVisitor = associationVisitor;
        return this;
    }

    public UmlMetricVisitor setClassVisitor(UmlVisitorClassRunnable classVisitor) {
        this.classVisitor = classVisitor;
        return this;
    }

    public UmlMetricVisitor setOperationVisitor(UmlVisitorOperationRunnable operationVisitor) {
        this.operationVisitor = operationVisitor;
        return this;
    }

    public UmlMetricVisitor setAttributeVisitor(UmlVisitorAttributeRunnable attributeVisitor) {
        this.attributeVisitor = attributeVisitor;
        return this;
    }

    public UmlMetricVisitor setMetricVisitor(UmlVisitorMetricRunnable metricVisitor) {
        this.metricVisitor = metricVisitor;
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

    public void visit(UmlMetric umlMetric) {
        if (metricVisitor != null){
            metricVisitor.visit(umlMetric);
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

    public interface UmlVisitorMetricRunnable {
        void visit(UmlMetric token);
    }



}
