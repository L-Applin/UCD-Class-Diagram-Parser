package syntxTree;

import syntxTree.expressions.Expression;
import token.UmlAttribute;
import token.UmlClass;
import token.UmlOpertaion;
import token.UmlToken;

import java.util.*;

/**
 * Manages the links between the {@link SyntaxTree} representation of the Context-Free Grammar and the display
 * elements that JavaFX requires.
 */
public class UmlContext implements Map<String, Expression> {

    /**
     * The model id as definedin the .ucd file
     */
    private String modelId;
    public String getModelId() { return modelId; }
    public void setModelId(String modelId) { this.modelId = modelId; }

    private Map<String, Expression> tokens;

    /**
     * All UmlClasses that were parsed represented in a more usable way for display
     */
    private Map<String, UmlClass> classes;
    public Map<String, UmlClass> getClasses() { return classes; }

    public UmlContext(Map<String, Expression> map){
        this.tokens = map;
    }

    // default use TreeMap
    public UmlContext(){
        this.tokens = new TreeMap<>();
        classes = new HashMap<>();
    }

    public UmlClass getUmlClass(String id){
        return classes.get(id);
    }

    public void createClass(String id){
        classes.put(id, new UmlClass(id));
    }

    @Override
    public String toString() {
        return "UmlContext{" +
                "modelId='" + modelId + '\'' +
                ", tokens=" + tokens +
                ", classes=" + classes +
                '}';
    }

    // MAP IMPLEMENTATION :)
    @Override
    public int size() {
        return tokens.size();
    }

    @Override
    public boolean isEmpty() {
        return tokens.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return tokens.containsKey((String) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return tokens.containsValue((Expression) value);
    }

    @Override
    public Expression get(Object key) {
        return tokens.get((String) key);
    }

    @Override
    public Expression put(String key, Expression value) {
        return tokens.put(key, value);
    }

    @Override
    public Expression remove(Object key) {
        return tokens.remove((String) key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Expression> m) {
        tokens.putAll(m);
    }

    @Override
    public void clear() {
        tokens.clear();
    }

    @Override
    public Set<String> keySet() {
        return tokens.keySet();
    }

    @Override
    public Collection<Expression> values() {
        return tokens.values();
    }

    @Override
    public Set<Entry<String, Expression>> entrySet() {
        return tokens.entrySet();
    }

}
