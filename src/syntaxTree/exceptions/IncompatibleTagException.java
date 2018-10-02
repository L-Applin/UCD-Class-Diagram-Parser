package syntaxTree.exceptions;

public class IncompatibleTagException extends UcdParsingException {

    /**
     * The tag that caused the error.
     */
    private String tag;
    public String getTag() { return tag; }

    /**
     * The content of the tag that caused the error.
     */
    private String content;
    public String getContent() { return tag + " " + content; }

    /**
     * Represente an error that is caused by a malformed tag based on the BNF syntax for .ucd file.<p></p>
     * Example : CLESS, ClASS, MODAL, mOdAlm tags written in the .ucd file would raise this exception
     * @param tag
     * @param content
     */
    public IncompatibleTagException(String tag, String expected, String content){
        super(tag + " is not a valide tag for " + expected);
        this.tag = tag;
        this.content = content;
    }

    public IncompatibleTagException(String tag, String content){
        super(tag + " is not a valide tag.");
        this.tag = tag;
        this.content = content;
    }

}
