package parsing.syntaxTree.entries;

/**
 * A simple wrapper around informations contained in a Declaration tag.
 */
public class RoleEntry {
    public final String tag, classId, multiplicity;
    public RoleEntry(String tag, String classId, String multiplicity) {
        this.tag = tag.trim();
        this.classId = classId.trim();
        this.multiplicity = multiplicity.trim();
    }
    public RoleEntry(String[] entries) {
        this(entries[0].trim(), entries[1].trim(), entries[2].trim());
    }

}
