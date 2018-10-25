package token.metrics;

import token.UmlClass;
import token.UmlContext;
import token.UmlMetric;
import token.visitor.UmlMetricVisitor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class MetricCalculator {

    private UmlClass umlClass;
    private List<String> allClasses;

    public MetricCalculator(UmlClass umlClass, UmlContext ctx) {
        this.umlClass = umlClass;

        // convert classes Map to list of class name
        Collection<UmlClass> tmpClassesCollection = ctx.getClasses().values();
        allClasses = tmpClassesCollection.stream().map(UmlClass::getName).collect(Collectors.toList());
    }

    public MetricCalculator(UmlClass umlClass, List<String> allClasses) {
        this.umlClass = umlClass;
        this.allClasses = allClasses;
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

    }

    public void calculateDIT(){

    }

    public void calculateCLD(){

    }

    /**
     * 9. NOC(ci) : Nombre de sous-classes directes de ci.
     */
    public void calculateNOC(){
        umlClass.addMetric(UmlMetric.MetricType.NOC, umlClass.getSubClasses().size());
    }

    /**
     * 10. NOD(ci) : Nombre de sous-classes directes et indirectes de ci.
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
