package syntxTree;

import token.UmlClass;
import token.UmlToken;

import java.util.Map;
import java.util.TreeMap;


/**
 * Manages the links between the {@link SyntaxTree} representation of the Context-Free Grammar and the display
 * elements that JavaFX requires. Serves as a link between {@link syntxTree.expressions.Expression}
 * and {@link UmlToken}.
 */
public class UmlContext {

    /**
     * The root model id as definedin the .ucd file.
     */
    private String modelId;
    public String getModelId() { return modelId; }
    public void setModelId(String modelId) { this.modelId = modelId; }

    /**
     * All UmlClasses that were parsed represented in a more freindly way for display.
     */
    private Map<String, UmlToken> classes;
    public Map<String, UmlToken> getClasses() { return classes; }

    /**
     * The original syntax tree containing the parsed element
     */
    private SyntaxTree tree;
    public SyntaxTree getTree() { return tree; }
    public void setTree(SyntaxTree tree) { this.tree = tree; }

    public UmlContext(){
        classes = new TreeMap<>();
    }

    public UmlClass getUmlClass(String id){
        return (UmlClass) classes.get(id);
    }

    public void createClass(String id, String content){
        classes.put(id, new UmlClass(id, content));
    }

}
