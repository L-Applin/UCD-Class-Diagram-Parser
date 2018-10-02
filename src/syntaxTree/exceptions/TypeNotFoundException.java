package syntaxTree.exceptions;

public class TypeNotFoundException extends UcdParsingException {

    private String content, parentId;

    public TypeNotFoundException(String content){
        super("Type could not be found in \'" + content + "\'");
        this.content = content;
    }

    public TypeNotFoundException(String content, String parentId){
        this(content);
        this.parentId = parentId;
    }


}
