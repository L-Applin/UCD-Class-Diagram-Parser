package token;

import token.visitor.UmlVisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Serves as an entry point for operation on the class diagram representation.
 * Manages the links between the {@link parsing.syntaxTree.SyntaxTree} representation of the Context-Free Grammar and the display
 * elements that JavaFX requires.
 */
public class UmlContext {

    /**
     * The model id as defined in the .ucd file
     */
    private String modelId;
    public String getModelId() { return modelId; }
    public void setModelId(String modelId) { this.modelId = modelId; }

    // private Map<String, Expression> tokens;

    /**
     * All UmlClasses that were parsed represented in a more usable way for display
     */
    private Map<String, UmlToken> classes;
    public Map<String, UmlToken> getClasses() { return classes; }


    // default use HashMap
    public UmlContext(){
        this(true);
    }

    public UmlContext(boolean useHashing){
        if (useHashing){
            classes = new HashMap<>();
        } else {
            classes = new TreeMap<>();
        }
    }

    public UmlClass getUmlClass(String id){
        return (UmlClass) classes.get(id);
    }

    public void createClass(String id, String content){
        classes.put(id, new UmlClass(id, content));
    }

    public void addArgumentToMethod(String classId, String methodId, String argId, String argType){
        getUmlClass(classId).getOperation(methodId).addArgument(argId, argType);
    }

    public void addAttributeToMethod(String classId, String attrId, String attrType, String attrContent){
        getUmlClass(classId).createAttributes(attrId, attrType, attrContent);

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
