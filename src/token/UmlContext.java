package token;

import syntaxTree.SyntaxTree;
import syntaxTree.expressions.Expression;
import token.UmlClass;
import token.UmlToken;
import token.visitor.UmlVisitor;

import java.util.*;

/**
 * Manages the links between the {@link SyntaxTree} representation of the Context-Free Grammar and the display
 * elements that JavaFX requires.
 */
public class UmlContext {

    /**
     * The model id as defined in the .ucd file
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
            (((UmlClass) umlClass).getSubClasses()).forEach((sbClassName, subClass)-> {
                ((UmlClass) subClass).setSuperClass((UmlClass) umlClass);
            });
        });
    }

    public void visitClasses(UmlVisitor visitor){
        classes.values().forEach(clazz -> clazz.accept(visitor));
    }

}
