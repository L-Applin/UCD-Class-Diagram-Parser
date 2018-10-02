package syntaxTree.exceptions;

public class MalformedOperationException extends MalformedClassException {

    public MalformedOperationException(String classId, String content){
        super("Cannot parse class " + classId + " because of method " + content);
        this.clazz = classId; this.content = content;
    }

    public MalformedOperationException(String message, String classId, String content){
        super(message);
        this.clazz = classId; this.content = content;
    }

}
