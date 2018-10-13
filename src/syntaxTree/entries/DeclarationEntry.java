package syntaxTree.entries;

/**
 * A simple wrapper around informations contained in a Declaration tag.
 */
public class DeclarationEntry {

    public final String decType, id, content;

    public DeclarationEntry(String decType, String id, String content) {
        this.decType = decType.trim();
        this.id = id.trim();
        this.content = content;
    }

}
