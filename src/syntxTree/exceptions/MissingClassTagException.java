package syntxTree.exceptions;

public class MissingClassTagException extends MalformedClassException {

    public MissingClassTagException(String clazz, String content, String missingTag){
        super("Cannot parse class \'" + clazz + "\'. Missing \'" + missingTag + "\' declaration.");
        this.clazz = clazz; this.content = content; this.missingTag = missingTag;
    }

}
