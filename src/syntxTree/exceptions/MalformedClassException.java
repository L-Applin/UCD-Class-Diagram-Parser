package syntxTree.exceptions;

public class MalformedClassException extends IllegalArgumentException {

    String content, clazz, missingTag;

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

    public String getMissingTag() {
        return missingTag;
    }

    public void setMissingTag(String missingTag) {
        this.missingTag = missingTag;
    }


    public MalformedClassException(String clazz, String content){
        super("Cannot parse class " + clazz + ".");
        this.clazz = clazz; this.content = content;
    }

    public MalformedClassException(String message){
        super(message);
    }

}
