package token.visitor;

import jdk.jshell.execution.Util;
import token.*;
import utils.Utils;

/**
 * Used as a test for Visitor pattern design. <p></p>
 * Will display in the console all informations about every class in the ucd file.
 */
public class InfoDisplayVisitor implements UmlVisitor {

    // unused for this implementation
    @Override public void visit(UmlAggregation aggregation){
        Utils.Log.log(aggregation.display());
    }

    @Override
    public void visit(UmlAssociation association){
        Utils.Log.log(association.display());
    }
    @Override public void visit(UmlOperation operation){
        Utils.Log.log("\t"+operation.display());
    }

    @Override
    public void visit(UmlAttribute attribute){
        Utils.Log.log("\t"+attribute.display());
    }

    @Override
    public void visit(UmlMetric umlMetric) {
        Utils.Log.log("\t"+umlMetric.display());
    }

    @Override
    public void visit(UmlClass umlClass) {
        System.out.print("\n"+umlClass.getName());
        if (umlClass.getSuperClass() != null){
            System.out.print(" extends "+umlClass.getSuperClass().getName());
        }
        System.out.print("\n");
        Utils.Log.log("Attributs");
        umlClass.getAttributes().values().forEach(attr -> attr.accept(this));
        Utils.Log.log("Operations");
        umlClass.getOperations().values().forEach(op -> op.accept(this));
        Utils.Log.log("Relations");
        umlClass.getAssociations().values().forEach(assoc -> assoc.accept(this));
        Utils.Log.log("Aggregation");
        umlClass.getAgregations().forEach(agg -> agg.accept(this));
        Utils.Log.log("Metrics");
        umlClass.getMetrics().values().forEach(metric -> metric.accept(this));

    }

}
