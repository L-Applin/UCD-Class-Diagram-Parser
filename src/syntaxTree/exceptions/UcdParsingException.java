package syntaxTree.exceptions;

/**
 * Simple wrapper around all parsing errors
 */
public abstract class UcdParsingException extends RuntimeException {
    public UcdParsingException(String message) {
        super(message);
    }
}
