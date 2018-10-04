package syntaxTree.exceptions;

public class IllegalCharacterException extends UcdParsingException {
    private String content, illegalChar, filePath;
    public IllegalCharacterException(String content, String illegalChar){
        super("File cannot contain character \'" + illegalChar + "\' at line : " + content);
        this.content = content; this.illegalChar = illegalChar;
    }

    public IllegalCharacterException(String content, String illegalChar, String filePath){
        super(filePath +" file cannot contain character \'" + illegalChar + "\' at line : " + content);
        this.content = content; this.illegalChar = illegalChar;
    }


}
