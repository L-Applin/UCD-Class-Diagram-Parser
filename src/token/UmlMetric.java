package token;

import screenDisplay.ScreenController;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class UmlMetric implements Displayable {

    private MetricType type;
    private double value;

    public UmlMetric(MetricType type, double value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Single instance of valueFormatter used by all instance to format the value
     */
    private static NumberFormat valueFormatter = new DecimalFormat("#0.00");

    @Override
    public String display() {
        return String.format("%s = %s", type.name(), valueFormatter.format(value));
    }

    @Override
    public void updateScreen(ScreenController controller) {
        controller.updateSelection(this);
    }

    /**
     * Metrics used.
     1. ANA(ci) : Nombre moyen d’arguments des méthodes locales pour la classe ci.
     2. NOM(ci) : Nombre de méthodes locales/héritées de la classe ci. Dans le cas où uneméthode est héritée et redéfinie localement (même nom, même ordre et types desarguments et même type de retour), elle ne compte qu’une fois.
     3. NOA(ci) : Nombre d’attributs locaux/hérités de la classe ci.
     4. ITC(ci) : Nombre de fois où d’autres classes du diagramme apparaissent comme typesdes arguments des méthodes de ci.
     5. ETC(ci) : Nombre de fois où ci apparaît comme type des arguments dans les méthodesdes autres classes du diagramme.
     6. CAC(ci) : Nombre d’associations (incluant les agrégations) locales/héritées auxquellesparticipe une classe ci.
     7. DIT(ci) : Taille du chemin le plus long reliant une classe ci à une classe racine dans legraphe d’héritage.
     8. CLD(ci) : Taille du chemin le plus long reliant une classe ci à une classe feuille dans legraphe d’héritage.
     9. NOC(ci) : Nombre de sous-classes directes de ci.
    10. NOD(ci) : Nombre de sous-classes directes et indirectes de ci.
     */
    public enum MetricType { ANA, NUM, NOA, ITC, ETC, CAC, DIT, CLD, NOC, COD }

}
