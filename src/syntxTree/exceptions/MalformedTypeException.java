package syntxTree.exceptions;

public class MalformedTypeException extends RuntimeException {
    private String txt, parentId;

    public MalformedTypeException(String txt, String parentId) {
        super("Cannot parse type \'" + txt + "\' in " + parentId);
        this.txt = txt; this.parentId = parentId;
    }

    public MalformedTypeException(String message, String txt, String parentId) {
        super(message);
        this.txt = txt; this.parentId = parentId;
    }
}
