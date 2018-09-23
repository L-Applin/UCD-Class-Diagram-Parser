package syntxTree.entries;

/**
 * A simple wrapper around informations contained in a Declaration tag.
 */
public class DeclarationEntry {

    private String decType;
    private String id;
    private String content;

    public DeclarationEntry(String decType, String id, String content) {
        this.decType = decType;
        this.id = id;
        this.content = content;
    }

    public String getDecType() {
        return decType;
    }

    public void setDecType(String decType) {
        this.decType = decType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
