package syntxTree;

public enum Primitives {

    CLASS("CLASS"),
    MODEL("MODEL"),
    ATTRIBUTES("ATTRIBUTES"),
    OPERATIONS("OPERATIONS");

    private String value;
    Primitives(String value){
        this.value = value;
    }

}
