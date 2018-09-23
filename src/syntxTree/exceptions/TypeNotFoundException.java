package syntxTree.exceptions;

public class TypeNotFoundException extends RuntimeException {

    private String type;

    public TypeNotFoundException(String type){
        super(type + " is not a valid type");
        this.type = type;
    }

}
