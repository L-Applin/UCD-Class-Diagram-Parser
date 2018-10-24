package token;

import token.visitor.UmlVisitorElement;

/**
 * General representation of a uml parameter.
 */
public abstract class UmlToken implements Displayable, UmlVisitorElement {

    /**
     * The name (or tag) of the token. This is the key that is used in the various map attributes
     * of classes that extend UmlToken. Example: {@link UmlClass#attributes}.
     */
    protected String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    /**
     * The String content of the class, as declared by the .ucd file that was parsed. This text, however,
     * is formated to be easier to parse so it contains some character that is not proper for display.
     */
    protected String content;
    public void setContent(String content) { this.content = content; }
    public String getContent() { return content; }

    public UmlToken(String content, String name) {
        this.content = content;
        this.name = name;
    }


}
