package token;

import screenDisplay.ScreenController;
import token.visitor.UmlVisitor;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class UmlMetric extends UmlToken implements Displayable {

    /**
     * Single instance of valueFormatter used by all instance to format the value
     */
    private static NumberFormat valueFormatter = new DecimalFormat("#0.00");

    private MetricType type;
    private double value;

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
     * [x] 5. ETC(ci) : Nombre de fois où ci apparaît comme type des arguments dans les méthodesdes autres classes du diagramme.
     * [] 6.  CAC(ci) : Nombre d’associations (incluant les agrégations) locales/héritées auxquellesparticipe une classe ci.
     * [] 7.  DIT(ci) : Taille du chemin le plus long reliant une classe ci à une classe racine dans le graphe d’héritage.
     * [] 8.  CLD(ci) : Taille du chemin le plus long reliant une classe ci à une classe feuille dans le graphe d’héritage.
     * [] 9.  NOC(ci) : Nombre de sous-classes directes de ci.
     * [] 10. NOD(ci) : Nombre de sous-classes directes et indirectes de ci.
     */
    public enum MetricType { ANA, NOM, NOA, ITC, ETC, CAC, DIT, CLD, NOC, NOD }

    public static final String ANA_DESC =
            "Nombre moyen d’arguments des méthodes locale";
    public static final String NOM_DESC =
            "Nombre de méthodes locales/héritées. Dans le cas où une méthode est héritée " +
            " et redéfinie localement (même nom, même ordre et types desarguments et même type de retour)," +
            " elle ne compte qu’une seul fois.";
    public static final String ITC_DESC =
            "Nombre de fois où d’autres classes du diagramme apparaissent comme types des arguments des méthodes";
    public static final String NOA_DESC =
            "Nombre d’attributs locaux/hérités";
    public static final String ETC_DESC =
            "Nombre de fois où la classe apparaît comme type des arguments dans les méthodesdes autres classes du diagramme.";
    public static final String CAC_DESC =
            "Nombre d’associations (incluant les agrégations) locales/héritées";
    public static final String DIT_DESC =
            "Taille du chemin le plus long à une classe racine dans le graphe d’héritage.";
    public static final String CLD_DESC =
            "Taille du chemin le plus long à une classe feuille dans le graphe d’héritage.";
    public static final String NOC_DESC =
            "Nombre de sous-classes directes";
    public static final String NOD_DESC =
            "Nombre de sous-classes directes et indirectes";

}
