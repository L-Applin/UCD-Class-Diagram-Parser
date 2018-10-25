package token;

import screenDisplay.ScreenController;
import token.visitor.UmlVisitor;

import java.util.*;

import static token.UmlMetric.*;

/**
 * Representation of a class element for a Uml class diagram.
 */
public class UmlClass extends UmlToken {

    /**
     * All class attributes, accessible by its name.
     */
    private Map<String, UmlAttribute> attributes;
    public Map<String, UmlAttribute> getAttributes() { return attributes; }

    /**
     * All class operations, accessible by its name.
     */
    private Map<String, UmlOperation> operations;
    public Map<String, UmlOperation> getOperations() { return operations; }

    /**
     * All the classes that extends this class.
     */
    private Map<String, UmlClass> subClasses;
    public Map<String, UmlClass> getSubClasses() { return subClasses; }

    /**
     * All classes that have an active association with this class. An association is when
     * another uses this class and defines it as an attributes.
     */
    private Map<String, UmlAssociation> associations;
    public Map<String, UmlAssociation> getAssociations() { return associations; }

    /**
     * All of the classes aggregation this attribute contains. List because AGGREGATION dont have tags, ids, or names.
     */
    private List<UmlAggregation> agregations;
    public List<UmlAggregation> getAgregations() { return agregations; }

    /**
     * The superclass this class extends.
     */
    private UmlClass superClass;
    public UmlClass getSuperClass() { return superClass; }
    public void setSuperClass(UmlClass superClass) { this.superClass = superClass; }

    /**
     * The metrics value of the class
     */
    private Map<String, UmlMetric> metrics;
    public Map<String, UmlMetric> getMetrics() { return metrics; }

    /**
     * The constructors must defines the name (tag) of the class and its string content. All other attributes are
     * defined as empty first and should be accessed with the createXXX or addXXX methods to put new element in them.
     * @param name the actual name of the class.
     * @param content the String content representation of the class. The content should define everything the class
     *                has and be coherent with how the {@link parsing.UcdParser} expect it to be formatted.
     */
    public UmlClass(String name, String content) {
        super(content, name);
        attributes =    new HashMap<>();
        operations =    new HashMap<>();
        associations =  new HashMap<>();
        subClasses =    new HashMap<>();
        metrics =       new HashMap<>();
        agregations =   new ArrayList<>();
    }

    /**
     * Used to merge the {@link UmlClass#agregations} and {@link UmlClass#associations} into a single list.
     * @return A new list containing both {@link UmlClass#agregations} and {@link UmlClass#associations}.
     */
    public List<UmlToken> getAggAssocList(){
        ArrayList<UmlToken> combined = new ArrayList<>();
        combined.addAll(agregations);
        combined.addAll(associations.values());
        return combined;
    }

    @Override
    public String display() {
        return name;
    }

    /**
     * Helper method used to create an {@link UmlOperation} associated with this class.
     * @param name the name of the method
     * @param type the return type of the method
     * @param content the text content description of the method
     */
    public void createOperation(String name, String type, String content){
        operations.put(name, new UmlOperation(name, type, content));
    }

    /**
     * Helper method to create an {@link UmlAttribute} associated with this class.
     * @param id the name, or identifierm of the attribut
     * @param type the type of the attribute
     * @param content the text content description of the attribute
     */
    public void createAttributes(String id, String type, String content){
        attributes.put(id, new UmlAttribute(id, type, content));
    }

    /**
     * Retrieve a single {@link UmlOperation} from methods this class defines by its name.
     * @param methodId the name of the method to retrieve as defined when
     * {@link UmlClass#createOperation(String, String, String)} was called.
     * @return the operation requested.
     */
    public UmlOperation getOperation(String methodId){
        return operations.get(methodId);
    }

    public void addSubClass(String classId, UmlClass subClass){
        subClasses.put(classId, subClass);
    }

    public void addMetric(UmlMetric.MetricType type, double value){
        String content;
        switch (type) {
            case ANA: content = ANA_DESC; break;
            case ETC: content = ETC_DESC; break;
            case ITC: content = ITC_DESC; break;
            case NOA: content = NOA_DESC; break;
            case NOC: content = NOC_DESC; break;
            case NOM: content = NOM_DESC; break;
            case CAC: content = CAC_DESC; break;
            case CLD: content = CLD_DESC; break;
            case DIT: content = DIT_DESC; break;
            case NOD: content = NOD_DESC; break;
            default: content = "";
        }
        metrics.put(type.name(), new UmlMetric(type, value, content));
    }

    /**
     *
     * @param toCompare
     * @return
     */
    public boolean hasOperation(UmlOperation toCompare){
        boolean hasOp = false;
        for (UmlOperation op : operations.values()){
            if (op.name.equals(toCompare.name) &&
                compareArgumentList(toCompare.getArguments(), op.getArguments())){
                hasOp = true;
                break;
            }
        }
        return hasOp;
    }

    /**
     *
     * @param args1
     * @param args2
     * @return
     */
    private static boolean compareArgumentList(List<UmlOperation.Args> args1, List<UmlOperation.Args> args2){
        // todo : test

        if (args1.size() != args2.size()){
            return false;
        }

        // lists have same length, if its 0 they have the same attributes, i.e. nothing
        if (args1.size() == 0){
            return true;
        }

        // todo : something must exist that already does that ...
        boolean same = true;
        for (UmlOperation.Args arg_1 : args1){
            boolean found = false;
            for (UmlOperation.Args arg_2 : args2){
                if (arg_1.equals(arg_2)){
                    found = true;
                    break;
                }
            }
            if (!found){
                same = false;
                break;
            }
        }
        return same;
    }

    @Override
    public String toString() {
        return "UmlClass{" + "name='" + name + '\'' + '}';
    }

    @Override
    public void updateScreen(ScreenController controller) {
        controller.updateSelection(this);
    }

    @Override
    public void accept(UmlVisitor visitor) {
        visitor.visit(this);
    }

}
