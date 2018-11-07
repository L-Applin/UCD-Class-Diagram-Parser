package token;

import screenDisplay.ScreenController;
import token.visitor.UmlVisitor;

public class UmlAssociation extends UmlToken implements AggAssoc{

    private UmlClass firstClass, secondClasse;
    private String association, firstClassMultiplicity, secondClassMultiplicity;
    public UmlClass getFirstClass() { return firstClass; }
    public UmlClass getSecondClasse() { return secondClasse; }

    public UmlAssociation(String association, UmlClass firstClass, String firstClassMultiplicity, UmlClass secondClasse, String secondClassMultiplicity, String content) {
        super(content, association);
        this.firstClass = firstClass;
        this.secondClasse = secondClasse;
        this.association = association;
        this.firstClassMultiplicity = firstClassMultiplicity;
        this.secondClassMultiplicity = secondClassMultiplicity;
    }

    @Override
    public String display() {
        return String.format("(R) %s : %s (%s) %s (%s)",
                association, firstClass.name, firstClassMultiplicity, secondClasse.name, secondClassMultiplicity);
    }

    @Override
    public void updateScreen(ScreenController controller) {
        controller.updateSelection(this);
    }

    @Override
    public void accept(UmlVisitor visitor) {
        visitor.visit(this);
    }


    @Override
    public String toString() {
        return "UmlAssociation{" +
                "firstClass=" + firstClass +
                ", secondClasse=" + secondClasse +
                ", association='" + association + '\'' +
                ", firstClassMultiplicity='" + firstClassMultiplicity + '\'' +
                ", secondClassMultiplicity='" + secondClassMultiplicity + '\'' +
                '}';
    }

    @Override
    public String getFirstName() {
        return firstClass.getName();
    }

    @Override
    public String getSecondName() {
        return secondClasse.getName();
    }
}
