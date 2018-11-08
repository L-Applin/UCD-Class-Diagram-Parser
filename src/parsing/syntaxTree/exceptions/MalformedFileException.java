package parsing.syntaxTree.exceptions;

/**
 * Exception for parsing error in a .ucd File
 */
public class MalformedFileException extends RuntimeException {

    public static final String PARSING_ERROR_MESSAGE = "Fichier corrompu";

    private String textCause;
    public String getTextCause() { return textCause; }

    public MalformedFileException() {
        super(PARSING_ERROR_MESSAGE);
    }

    public MalformedFileException(String txt) {
        super(PARSING_ERROR_MESSAGE);
        this.textCause = txt;
    }


}
