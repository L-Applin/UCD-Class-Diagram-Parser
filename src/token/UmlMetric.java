package token;

import screenDisplay.ScreenController;
import token.visitor.UmlVisitor;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * A representation of a numerical value for a UmlClass metric.
 */
public class UmlMetric extends UmlToken implements Displayable {

    /**
     * Single instance of valueFormatter used by all instance to format the value
     */
    private static NumberFormat valueFormatter = new DecimalFormat("#0.00");

    /**
     * The type category of the metric as defined by the assignement.
     */
    private MetricType type;

    /**
     * The numerical value of the metric
     */
    private double value;
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    /**
     *
     * @param type the type of the metric.
     * @param value it'S value
     * @param desc a text description of the metric. Use to display extra information
     */
    public UmlMetric(MetricType type, double value, String desc) {
        super(desc, type.name());
        this.type = type;
        this.value = value;
    }

    @Override
    public String display() {
        return String.format("%s = %s", type.name(), valueFormatter.format(value));
    }

    @Override
    public void updateScreen(ScreenController controller) {
        controller.updateSelection(this);
    }

    @Override
    public void accept(UmlVisitor visitor) {
        visitor.visit(this);
    }


    /**
     * Metrics used.
     * [x] 1. ANA(ci) : Nombre moyen d’arguments des méthodes locales pour la classe ci.
     * [x] 2. NOM(ci) : Nombre de méthodes locales/héritées de la classe ci. Dans le cas où une méthode est héritée
     *         et redéfinie localement (même nom, même ordre et types desarguments et même type de retour), elle ne compte qu’une fois.
     * [x] 3. NOA(ci) : Nombre d’attributs locaux/hérités de la classe ci.
     * [x] 4. ITC(ci) : Nombre de fois où d’autres classes du diagramme apparaissent comme types des arguments des méthodes de ci.
     * [x] 5. ETC(ci) : Nombre de fois où ci apparaît comme type des arguments dans les méthodes des autres classes du diagramme.
     * [x] 6.  CAC(ci) : Nombre d’associations (incluant les agrégations) locales/héritées auxquellesparticipe une classe ci.
     * [x] 7.  DIT(ci) : Taille du chemin le plus long reliant une classe ci à une classe racine dans le graphe d’héritage.
     * [x] 8.  CLD(ci) : Taille du chemin le plus long reliant une classe ci à une classe feuille dans le graphe d’héritage.
     * [x] 9.  NOC(ci) : Nombre de sous-classes directes de ci.
     * [x] 10. NOD(ci) : Nombre de sous-classes directes et indirectes de ci.
     */
    public enum MetricType { ANA, NOM, NOA, ITC, ETC, CAC, DIT, CLD, NOC, NOD }

    static final String ANA_DESC =
        "Nombre moyen d’arguments des méthodes locale";
    static final String NOM_DESC =
        "Nombre de méthodes locales/héritées. Dans le cas où une méthode est héritée \n" +
        " et redéfinie localement (même nom, même ordre et types desarguments et même type de retour),\n" +
        " elle ne compte qu’une seul fois.";
    static final String ITC_DESC =
        "Nombre de fois où d’autres classes du diagramme apparaissent comme types des arguments des méthodes";
    static final String NOA_DESC =
        "Nombre d’attributs locaux/hérités";
    static final String ETC_DESC =
        "Nombre de fois où la classe apparaît comme type des arguments dans les méthodesdes autres classes du diagramme.";
    static final String CAC_DESC =
        "Nombre d’associations (incluant les agrégations) locales/héritées";
    static final String DIT_DESC =
        "Taille du chemin le plus long à une classe racine dans le graphe d’héritage.";
    static final String CLD_DESC =
        "Taille du chemin le plus long à une classe feuille dans le graphe d’héritage.";
    static final String NOC_DESC =
        "Nombre de sous-classes directes";
    static final String NOD_DESC =
        "Nombre de sous-classes directes et indirectes";

}
