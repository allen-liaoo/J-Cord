package org.alienideology.jcord.internal.exception;

/**
 * @author AlienIdeology
 */
public class HigherHierarchyException extends RuntimeException {

    private HierarchyType type;

    public HigherHierarchyException(String message) {
        super(message);
        this.type = HierarchyType.UNKNOWN;
    }

    public HigherHierarchyException(HierarchyType type) {
        super(type.meaning);
        this.type = type;
    }

    public HierarchyType getType() {
        return type;
    }

    public enum HierarchyType {
        MEMBER ("Cannot modify a member that has higher hierarchy than the identity!"),
        ROLE ("Cannot modify a role that has higher hierarchy than the identity!"),
        OWNER ("Cannot modify the server owner!"),
        UNKNOWN ("");

        public String meaning;

        HierarchyType(String meaning) {
            this.meaning = meaning;
        }
    }

}
