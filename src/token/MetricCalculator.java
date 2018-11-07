package token;

import app.Utils;
import token.visitor.UmlMetricVisitor;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static token.UmlMetric.MetricType.*;


/**
 * This class provides methods for calculating different metrics related to a {@link UmlClass}.
 * Every metric declared in {@link UmlMetric.MetricType} should have a corresponding method in this class
 * corresponding the the actual calculation process of that metric.
 *
 * This class relies heavily on the use of the {@link token.visitor.UmlVisitor} and {@link token.visitor.UmlVisitorElement}
 * interfaces to help the algorithm that measure the different metri values.
 */
class MetricCalculator {

    /**
     * The class on which the metric will be calculated
     */
    private UmlClass umlClass;

    /**
     * All other classes that the model defines that are kept in a {@link UmlContext}.
     */
    private Set<String> allClasses;

    private UmlContext ctx;

    /**
     * @param umlClass the class on which the metric will be calculated
     * @param ctx the context containing all other related information of the model.
     */
    MetricCalculator(UmlClass umlClass, UmlContext ctx) {
        this.umlClass = umlClass;
        allClasses = ctx.getClasses().keySet();
        this.ctx = ctx;
    }

    /**
     * Helper method to calculate all metrics of the provided class in the constructor in it's context.
     */
    void calculateAllMetrics(){
        calculateANA();
        calculateCAC();
        calculateCLD();
        calculateDIT();
        calculateETC();
        calculateITC();
        calculateNOA();
        calculateNOC();
        calculateNOD();
        calculateNOM();
    }

    /**
     * ANA : Nombre moyen d’arguments des méthodes locales pour la classe.
     */
    void calculateANA(){

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

            umlClass.addMetric(ANA, avg_agr_amout);
        });

        umlClass.accept(anaMetricVisitor);

    }

    /**
     * NOM : Nombre de méthodes locales/héritées de la classe. Dans le cas où une méthode est héritée
     *         et redéfinie localement (même nom, même ordre et types des arguments et même type de retour),
     *         elle ne compte qu’une seule fois.
     */
    void calculateNOM(){

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
        umlClass.addMetric(NOM, nomMetricVisitor.getValue());

    }

    /**
     * NOA : Nombre d’attributs locaux/hérités de la classe.
     */
    void calculateNOA(){

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
        umlClass.addMetric(NOA, noaMetricVisitor.getValue());


    }

    /**
     * ITC : Nombre de fois où d’autres classes du diagramme apparaissent
     * comme types des arguments des méthodes de la classe.
     */
    void calculateITC(){

        UmlMetricVisitor ditMetricVisitor = new UmlMetricVisitor();

        ditMetricVisitor.setClassVisitor(clazz -> {
            clazz.getOperations().values().forEach(method -> {
                method.getArguments().forEach(arg -> {
                    // is this arg a class that exist in the diagram ?
                    if (allClasses.contains(arg.type)){
                        ditMetricVisitor.incrementValue();
                    }
                });
            });
        });

        umlClass.accept(ditMetricVisitor);
        umlClass.addMetric(ITC, ditMetricVisitor.getValue());


    }

    /**
     * ETC : Nombre de fois où la classe apparaît comme type des arguments
     * dans les méthodesdes autres classes du diagramme.
     *
     */
    void calculateETC(){

        UmlMetricVisitor etcMetricVisitor = new UmlMetricVisitor();
        String className = umlClass.getName();

        etcMetricVisitor.setClassVisitor(clazz ->{
            clazz.getOperations().values().forEach(op ->{
                op.getArguments().forEach(arg ->{
                    if (!clazz.getName().equals(className) && arg.type.equals(className)){
                        etcMetricVisitor.incrementValue();
                    }
                });
            });
        });

        ctx.visitClasses(etcMetricVisitor);
        umlClass.addMetric(ETC, etcMetricVisitor.getValue());

    }

    /**
     * CAC : Nombre d’associations (incluant les agrégations) locales/héritées auxquelles participe la classe.
     */
    public Integer calculateCAC(){

        AtomicInteger i = new AtomicInteger();
        String className = umlClass.getName();

        ctx.getAllAggAssoc().forEach(aggAssoc -> {

            Utils.Log.test(aggAssoc.toString());
            String firstName = aggAssoc.getFirstName();
            String secondName = aggAssoc.getSecondName();

            // Utils.Log.test(String.format("checking : %s\nfirst : %s, second : %s", className, firstName, secondName));
            // Utils.Log.test((firstName.equals(className) || secondName.equals(className))+"");
            if (firstName.equals(className) || secondName.equals(className)){
                i.incrementAndGet();
               //  Utils.Log.test("increment");
            }
            // Utils.Log.test(i.doubleValue()+"");
            // Utils.Log.test("\n");
        });

        if (umlClass.getSuperClass() != null){
            i.addAndGet(new MetricCalculator(umlClass.getSuperClass(), ctx).calculateCAC());
        }

        umlClass.addMetric(CAC, i.doubleValue());
        return i.intValue();

/*

        UmlMetricVisitor cacMetricVisitor = new UmlMetricVisitor();

        cacMetricVisitor.setClassVisitor(clazz ->{

            // cacMetricVisitor.incrementValue(clazz.getAggAssocList().size());
            // visiter les assoc et aggreg de la classe courante :
            clazz.getAggAssocList().forEach(aggAss -> aggAss.accept(cacMetricVisitor));

            // visiter les assoc / aggreg de la classe parente
            if (clazz.getSuperClass() != null) {
                clazz.getSuperClass().accept(cacMetricVisitor);
            }

        }).setAggregationVisitor(aggregation -> {

            String partName = aggregation.getPart().clazz.getName();
            String contName = aggregation.getContainer().getName();
            String className = umlClass.getName();

            if (className.equals(partName) || className.equals(contName)){
                cacMetricVisitor.incrementValue();
            }

        }).setAssociationVisitor(assoc -> {

            String firstAssocName = assoc.getFirstClass().getName();
            String secondAssocName = assoc.getSecondClasse().getName();
            String className = umlClass.getName();

            if (className.equals(firstAssocName) || className.equals(secondAssocName)){
                cacMetricVisitor.incrementValue();
            }

        });

        ctx.visitClasses(cacMetricVisitor);
        umlClass.addMetric(CAC, cacMetricVisitor.getValue());
*/

    }

    /**
     * DIT : Taille du chemin le plus long reliant une classe ci à une classe racine
     * dans le graphe d’héritage.
     */
    void calculateDIT(){

        UmlMetricVisitor ditMetricVisitor = new UmlMetricVisitor();

        ditMetricVisitor.setClassVisitor(clazz -> {
           UmlClass parent = clazz.getSuperClass();
           while (parent != null){
               ditMetricVisitor.incrementValue();
               parent = parent.getSuperClass();
           }
        });

        umlClass.accept(ditMetricVisitor);
        umlClass.addMetric(DIT, ditMetricVisitor.getValue());

    }

    /**
     *  CLD : Taille du chemin le plus long reliant une classe ci à une classe feuille dans le graphe d’héritage.
     */
    public void calculateCLD(){
        //todo
        umlClass.addMetric(CLD, recursiveCLD(umlClass));
    }

    /**
     * NOC(ci) : Nombre de sous-classes directes de ci.
     */
    void calculateNOC(){
        umlClass.addMetric(NOC, umlClass.getSubClasses().size());
    }

    /**
     * NOD(ci) : Nombre de sous-classes directes et indirectes de ci.
     */
    void calculateNOD(){
        // todo : test
        umlClass.addMetric(NOD, recursiveNOD(umlClass));
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

    private Integer recursiveCLD(UmlClass clazz){

        if (clazz.getSubClasses().size() == 0){
            return 0;
        }
        ArrayList<Integer> values = new ArrayList<>();

        for (UmlClass child : clazz.getSubClasses().values()){
            values.add(1 + recursiveCLD(child));
        }
        return Collections.max(values);
    }

}
