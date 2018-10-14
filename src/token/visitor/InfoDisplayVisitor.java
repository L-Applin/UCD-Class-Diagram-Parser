package token.visitor;

import token.*;
import utils.Utils;

public class InfoDisplayVisitor implements UmlVisitor {

    // unused for this implementation
    @Override public void visit(UmlAggregation aggregation){ }
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
    public void visit(UmlClass umlClass) {
        System.out.print("\n"+umlClass.getName());
        if (umlClass.getSuperClass() != null){
            System.out.print(" extends "+umlClass.getSuperClass().getName());
        }
        System.out.print("\n");
        Utils.Log.log("attributs");
        umlClass.getAttributes().values().forEach(attr -> attr.accept(this));
        Utils.Log.log("operations");
        umlClass.getOperations().values().forEach(op -> op.accept(this));
        Utils.Log.log("Relations");
        umlClass.getAssociations().values().forEach(assoc -> assoc.accept(this));
    }

}
