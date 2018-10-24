package token;

import screenDisplay.ScreenController;
import token.visitor.UmlVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of a class element for a Uml class diagram.
 */
public class UmlClass extends UmlToken {

    /**
     * All class attributes, accessible by its name.
     */
    private Map<String, UmlToken> attributes;
    public Map<String, UmlToken> getAttributes() { return attributes; }

    /**
     * All class operations, accessible by its name.
     */
    private Map<String, UmlToken> operations;
    public Map<String, UmlToken> getOperations() { return operations; }

    /**
     * All the classes that extends this class.
     */
    private Map<String, UmlToken> subClasses;
    public Map<String, UmlToken> getSubClasses() { return subClasses; }

    /**
     * All classes that have an active association with this class. An association is when
     * another uses this class and defines it as an attributes.
     */
    private Map<String, UmlToken> associations;
    public Map<String, UmlToken> getAssociations() { return associations; }

    /**
     * All of the classes aggregation this attribute contains.
     */
    private List<UmlToken> agregations;
    public List<UmlToken> getAgregations() { return agregations; }

    /**
     * The superclass this class extends.
     */
    private UmlClass superClass;
    public UmlClass getSuperClass() { return superClass; }
    public void setSuperClass(UmlClass superClass) { this.superClass = superClass; }

    /**
     * The metrics value of the class
     */
    private UmlMetric metric;
    public UmlMetric getMetric() { return metric; }
    public void setMetric(UmlMetric metric) { this.metric = metric; }

    /**
     * The constructors must defines the name (tag) of the class and its string content. All other attributes are
     * defined as empty first and should be accessed with the createXXX or addXXX methods to put new element in them.
     * @param name the actual name of the class.
     * @param content the String content representation of the class. The content should define everything the class
     *                has and be coherent with how the {@link parsing.UcdParser} expect it to be formatted.
     */
    public UmlClass(String name, String content) {
        super(content, name);
        attributes = new HashMap<>();
        operations = new HashMap<>();
        associations = new HashMap<>();
        agregations = new ArrayList<>();
        subClasses = new HashMap<>();
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
        return (UmlOperation) operations.get(methodId);
    }

    public void addSubClass(String classId, UmlClass subClass){
        subClasses.put(classId, subClass);
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
