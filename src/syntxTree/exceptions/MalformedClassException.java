package syntxTree.exceptions;

public class MalformedClassException extends UcdParsingException {

    protected String content, clazz;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }


    public MalformedClassException(String clazz, String content){
        super("Cannot parse class " + clazz + ".");
        this.clazz = clazz; this.content = content;
    }

    public MalformedClassException(String message){
        super(message);
    }

}
