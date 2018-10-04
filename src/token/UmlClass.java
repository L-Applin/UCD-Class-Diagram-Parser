package token;

import screenDisplay.ScreenController;
import syntaxTree.expressions.DataItem;

import java.util.*;

public class UmlClass extends UmlToken {

    /**
     * All class attributs accessible by name
     */
    private Map<String, UmlToken> attributes;
    public Map<String, UmlToken> getAttributes() { return attributes; }

    /**
     * All class methods, accessible by name
     */
    private Map<String, UmlToken> operations;
    public Map<String, UmlToken> getOperations() { return operations; }


    /**
     *
     */
    private Map<String, UmlToken> subClasses;
    public Map<String, UmlToken> getSubClasses() { return subClasses; }

    /**
     *
     */
    private Map<String, UmlToken> associations;
    public Map<String, UmlToken> getAssociations() { return associations; }

    /**
     *
     */
    private List<UmlToken> agregations;
    public List<UmlToken> getAgregations() { return agregations; }

    /**
     *
     */
    private UmlClass superClass;
    public UmlClass getSuperClass() { return superClass; }
    public void setSuperClass(UmlClass superClass) { this.superClass = superClass; }

    public UmlClass(String name, String content) {
        super(content, name);
        attributes = new HashMap<>();
        operations = new HashMap<>();
        associations = new HashMap<>();
        agregations = new ArrayList<>();
        subClasses = new HashMap<>();
    }

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

    public void addAttributes(DataItem att, String content){
        attributes.put(att.getIdAsString(), new UmlAttribute(att.getIdAsString(), att.getTypeAsString(), content));
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
