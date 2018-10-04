package token;

import screenDisplay.ScreenController;
import utils.Utils;

import java.util.*;

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
     * The constructors must defines the name (tag) of the class and its string content. All other attributes are
     * defined as empty first and should be accessed with the createXXX or addXXX methods to put new element in them.
     * @param name the actual name of the class.
     * @param content the String content representation of the class. The content should define everything the class
     *                has and be coherent with how the {@link parsing.UcdParser} expect it to be formatted.
     */
    public UmlClass(String name, String content) {
        super(content, name);
        Utils.Log.test("UML CLASS:", name);
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

    public void createOperation(String name, String type, String content){
        operations.put(name, new UmlOperation(name, type, content));
    }


    public void createAttributes(String id, String type, String content){
        attributes.put(id, new UmlAttribute(id, type, content));
    }

    public UmlToken getOperation(String methodId){
        return operations.get(methodId);
    }

    public void addSubClass(String classId, UmlClass subClass){
        subClasses.put(classId, subClass);
    }

    @Override
    public String toString() {
        return "UmlClass{" +
                "name='" + name + '\'' + '}';
    }

    @Override
    public void updateScreen(ScreenController controller) {
        controller.updateSelection(this);
    }


}
