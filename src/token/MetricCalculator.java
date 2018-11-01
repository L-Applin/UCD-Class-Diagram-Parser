package token;

import token.visitor.UmlMetricVisitor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides methods for calculating different metrics related to a {@link UmlClass}.
 * Every metric declared in {@link UmlMetric.MetricType} should have a corresponding method in this class
 * corresponding the the actual calculation process of that metric.
 *
 * This class relies heavily on the use of the {@link token.visitor.UmlVisitor} and {@link token.visitor.UmlVisitorElement}
 * interfaces to help the algorithm that measure the different metri values.
 */
public class MetricCalculator {

    /**
     * The class on which the metric will be calculated
     */
    private UmlClass umlClass;

    /**
     * All other classes that the model defines that are kept in a {@link UmlContext}.
     */
    private List<String> allClasses;

    /**
     * @param umlClass the class on which the metric will be calculated
     * @param ctx the context containing all other related information of the model.
     */
    MetricCalculator(UmlClass umlClass, UmlContext ctx) {
        this.umlClass = umlClass;

        // convert classes Map to list of String class name
        Collection<UmlClass> tmpClassesCollection = ctx.getClasses().values();
        allClasses = tmpClassesCollection.stream().map(UmlClass::getName).collect(Collectors.toList());
    }


    /**
     * 1. ANA(ci) : Nombre moyen d’arguments des méthodes locales pour la classe ci.
     */
    public void calculateANA(){

        UmlMetricVisitor anaMetricVisitor = new UmlMetricVisitor();
        anaMetricVisitor.setClassVisitor(clazz -> {

            int amount_of_methods = clazz.getOperations().size();

            // total amount of arguments
            clazz.getOperations().values().forEach(op -> {
                anaMetricVisitor.incrementValue(op.getArguments().size());
            });

            // divided by the amount of methods
            // prevent division by 0
            double avg_agr_amout = amount_of_methods > 0 ?
                    anaMetricVisitor.getValue() / amount_of_methods:
                    0;

            umlClass.addMetric(UmlMetric.MetricType.ANA, avg_agr_amout);
        });

        umlClass.accept(anaMetricVisitor);

    }

    /**
     * 2. NOM(ci) : Nombre de méthodes locales/héritées de la classe ci. Dans le cas où une méthode est héritée
     *         et redéfinie localement (même nom, même ordre et types des arguments et même type de retour),
     *         elle ne compte qu’une fois.
     */
    public void calculateNOM(){

        UmlMetricVisitor nomMetricVisitor = new UmlMetricVisitor();
        nomMetricVisitor.setClassVisitor(clazz -> {

            // amount of local methods
            nomMetricVisitor.setValue(clazz.getOperations().size());

            //amount of inherited methods
            UmlClass parent = clazz.getSuperClass();
            while (parent != null){
                parent.getOperations().values().forEach(op -> {
                    if(!clazz.hasOperation(op)){
                        nomMetricVisitor.incrementValue();
                    }
                });
                parent = parent.getSuperClass();
            }
        });

        umlClass.accept(nomMetricVisitor);
        umlClass.addMetric(UmlMetric.MetricType.NOM, nomMetricVisitor.getValue());

    }

    /**
     * 3. NOA(ci) : Nombre d’attributs locaux/hérités de la classe ci.
     */
    public void calculateNOA(){

        UmlMetricVisitor noaMetricVisitor = new UmlMetricVisitor();
        noaMetricVisitor.setClassVisitor(clazz -> {
            // amount of local arguments
            noaMetricVisitor.setValue(clazz.getAttributes().size());

            // amount of inherited arguments
            UmlClass parent = clazz.getSuperClass();
            while (parent != null){
                noaMetricVisitor.incrementValue(parent.getAttributes().size());
                parent = parent.getSuperClass();
            }
        });

        umlClass.accept(noaMetricVisitor);
        umlClass.addMetric(UmlMetric.MetricType.NOA, noaMetricVisitor.getValue());


    }

    /**
     * 4. ITC(ci) : Nombre de fois où d’autres classes du diagramme apparaissent
     * comme types des arguments des méthodes de ci.
     */
    public void calculateITC(){
        UmlMetricVisitor noaMetricVisitor = new UmlMetricVisitor();

        noaMetricVisitor.setClassVisitor(clazz -> {
            clazz.getOperations().values().forEach(method -> {
                method.getArguments().forEach(arg -> {
                    // is this arg a class that exist in the diagram ?
                    if (allClasses.contains(arg.name)){
                        noaMetricVisitor.incrementValue();
                    }
                });
            });
        });

        umlClass.accept(noaMetricVisitor);
        umlClass.addMetric(UmlMetric.MetricType.ITC, noaMetricVisitor.getValue());


    }

    /**
     * 5. ETC(ci) : Nombre de fois où ci apparaît comme type des arguments
     * dans les méthodesdes autres classes du diagramme.
     *
     */
    public void calculateETC(){

        UmlMetricVisitor etcMetricVisitor = new UmlMetricVisitor();
        etcMetricVisitor.setClassVisitor(clazz ->{
            String className = umlClass.getName();

            allClasses.forEach(cls -> {
                if(className.equals(clazz)){
                    etcMetricVisitor.incrementValue();
                }
            });
        });

        umlClass.accept(etcMetricVisitor);
        umlClass.addMetric(UmlMetric.MetricType.ETC, etcMetricVisitor.getValue());

    }


    public void calculateCAC(){
        //todo
        umlClass.addMetric(UmlMetric.MetricType.CAC, 0.0);
    }

    /**
     * DIT(ci) : Taille du chemin le plus long reliant une classe ci à une classe racine
     * dans le graphe d’héritage.
     */
    public void calculateDIT(){
        UmlMetricVisitor ditMetricVisitor = new UmlMetricVisitor();
        ditMetricVisitor.setClassVisitor(clazz -> {
           UmlClass parent = clazz.getSuperClass();
           while (parent != null){
               ditMetricVisitor.incrementValue();
               parent = clazz.getSuperClass();
           }
        });
        umlClass.accept(ditMetricVisitor);
        umlClass.addMetric(UmlMetric.MetricType.DIT, ditMetricVisitor.getValue());

    }

    public void calculateCLD(){
        //todo
        umlClass.addMetric(UmlMetric.MetricType.CLD, 0.0);
    }

    /**
     * NOC(ci) : Nombre de sous-classes directes de ci.
     */
    public void calculateNOC(){
        umlClass.addMetric(UmlMetric.MetricType.NOC, umlClass.getSubClasses().size());
    }

    /**
     * NOD(ci) : Nombre de sous-classes directes et indirectes de ci.
     */
    public void calculateNOD(){
        // todo : test
        umlClass.addMetric(UmlMetric.MetricType.NOD, recursiveNOD(umlClass));
    }

    private double recursiveNOD(UmlClass clazz){
        double total = 0;
        if (clazz.getSubClasses().size() == 0){
            return total;
        }
        for (UmlClass cls : clazz.getSubClasses().values()){
            total += 1 + recursiveNOD(cls);
        }
        return total;
    }

}
