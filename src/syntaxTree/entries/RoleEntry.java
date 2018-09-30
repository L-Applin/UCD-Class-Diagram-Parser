package syntaxTree.entries;

/**
 * A simple wrapper around informations contained in a Declaration tag.
 */
public class RoleEntry {

    private String tag, classId, multiplicity;

    public RoleEntry(String tag, String classId, String multiplicity) {
        this.tag = tag;
        this.classId = classId;
        this.multiplicity = multiplicity;
    }

    public RoleEntry(String[] entries) {
        this.tag = entries[0];
        this.classId = entries[1];
        this.multiplicity = entries[2];
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(String multiplicity) {
        this.multiplicity = multiplicity;
    }
}
