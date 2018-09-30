package syntaxTree;

import syntaxTree.expressions.Expression;
import token.UmlClass;
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
    private Map<String, UmlToken> classes;
    public Map<String, UmlToken> getClasses() { return classes; }

    /**
     * The original syntax tree containing the parsed element
     */
    private SyntaxTree tree;
    public SyntaxTree getTree() { return tree; }
    public void setTree(SyntaxTree tree) { this.tree = tree; }

    public UmlContext(Map<String, Expression> map){
        this.tokens = map;
    }

    // default use TreeMap
    public UmlContext(){
        this.tokens = new TreeMap<>();
        classes = new HashMap<>();
    }

    public UmlClass getUmlClass(String id){
        return (UmlClass) classes.get(id);
    }

    public void createClass(String id, String content){
        classes.put(id, new UmlClass(id, content));
    }


    public void initializeSubclasses(){
        classes.forEach((className, umlClass) ->{

        });
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
