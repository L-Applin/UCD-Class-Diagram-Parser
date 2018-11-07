package token;

import app.Utils;
import parsing.syntaxTree.exceptions.MalformedFileException;
import token.visitor.UmlVisitor;

import java.util.*;

/**
 * Serves as an entry point for operation on the class diagram representation.
 * Manages the links between the {@link parsing.syntaxTree.SyntaxTree} representation of the Context-Free Grammar and the display
 * elements that JavaFX requires.
 */
public class UmlContext {

    private boolean metricCalculated = false;
    private boolean immutable = false;
    public boolean isImmutable() { return immutable; }
    public void setImmutable() { this.immutable = true; }

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
    private Map<String, UmlClass> classes;
    public Map<String, UmlClass> getClasses() { return classes; }


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
        return classes.get(id);
    }

    public void createClass(String id, String content){
        if (classes.containsKey(id)){
            throw new MalformedFileException(String.format("Class %s already defined", id));
        }
        classes.put(id, new UmlClass(id, content));
    }

    /**
     * Allows for Class modification before the end of parsing execution
     * @param classId
     * @param methodId
     * @param argId
     * @param argType
     */
    public void addArgumentToMethod(String classId, String methodId, String argId, String argType) throws IllegalAccessException {
        if (!immutable) {
            getUmlClass(classId).getOperation(methodId).addArgument(argId, argType);
        } else {
            throw new IllegalAccessException("Cannot modify Context after parsing complete");
        }
    }

    public void addAttributeToMethod(String classId, String attrId, String attrType, String attrContent) throws IllegalAccessException {
        if (!immutable) {
            getUmlClass(classId).createAttributes(attrId, attrType, attrContent);
        } else {
            throw new IllegalAccessException("Cannot modify Context after parsing complete");
        }

    }


    /**
     * Visitt all classes with the designated visitor
     * @param visitor
     */
    public void visitClasses(UmlVisitor visitor){
        classes.values().forEach(clazz -> clazz.accept(visitor));
    }


    public void calculateMetrics(){

        if (metricCalculated) return;

        classes.values().forEach(clazz -> {
            MetricCalculator calculator = new MetricCalculator(clazz, this);
            calculator.calculateAllMetrics();
        });

        metricCalculated = true;

    }

    public void logMetrics(){
        if (metricCalculated) {

            List<UmlClass> allClasses = new ArrayList<>(classes.values());
            allClasses.sort(Comparator.comparing(UmlClass::getName, String.CASE_INSENSITIVE_ORDER));

            allClasses.forEach(umlClass -> {
                System.out.println("\n"+umlClass.getName());
                umlClass.getMetrics().forEach((type, umlMetric) -> {
                    System.out.printf("%s : %s\n", type.name(), umlMetric.getValue());
                });
                System.out.println("\n");
                metricCalculated = true;
            });
        } else {
            Utils.Log.log("Les métriques n'ont pas été calculée");
        }
    }

    public List<AggAssoc> getAllAggAssoc(){
        Set<AggAssoc> set = new HashSet<>();
        classes.values().forEach(clazz ->{
            set.addAll(clazz.getAggAssocListAsInterface());
        });
        return new ArrayList<>(set);
    }
}
