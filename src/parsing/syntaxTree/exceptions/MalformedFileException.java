package parsing.syntaxTree.exceptions;

public class MalformedFileException extends RuntimeException {
    public static final String PARSING_ERROR_MESSAGE = "Fichier corrompu";
    public MalformedFileException(String mess) {
        super(mess);
    }

    public MalformedFileException() {
        super(PARSING_ERROR_MESSAGE);
    }

}
