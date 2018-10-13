package token;

import screenDisplay.ScreenController;
import token.visitor.UmlVisitor;

public class UmlAssociation extends UmlToken {

    private UmlClass firstClass, secondClasse;
    private String association, firstClassMultiplicity, secondClassMultiplicity;

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
        firstClass.accept(visitor);
        secondClasse.accept(visitor);
        visitor.visit(this);
    }
}
