package token;

import javafx.scene.text.Text;
import syntxTree.expressions.DataItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UmlClass extends UmlToken {

    private String name;

    private HashMap<String, UmlAttribute> attributes;
    private HashMap<String, UmlOpertaion> operations;

    private UmlClass superClass;

    private List<UmlClass> associations;
    private List<UmlClass> agregation;

    public UmlClass(String name) {
        this.name = name;
        attributes = new HashMap<>();
        operations = new HashMap<>();
        associations = new ArrayList<>();
        agregation = new ArrayList<>();
    }

    @Override
    public Text display() {
        // todo
        return null;
    }

    public void createOperation(String name, String type){
        operations.put(name, new UmlOpertaion(name, type));
    }

    public void addAttributes(DataItem att){
        attributes.put(att.getIdAsString(), new UmlAttribute(att.getIdAsString(), att.getTypeAsString()));
    }

    public UmlOpertaion getOperation(String methodId){
        return operations.get(methodId);
    }



}
