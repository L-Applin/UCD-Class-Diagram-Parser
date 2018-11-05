package token.visitor;

/**
 * An element that can be visited by a {@link UmlVisitor}
 */
public interface UmlVisitorElement {

    /**
     * This method is used to redirect the operations to be completed upon visiting to the
     * corresponding method in {@link UmlVisitor}. Basic implementation should look like :
     * <pre>
     *     public void accept(UmlVisitor visitor) {
     *         visitor.visit(this);
     *     }
     * </pre>
     * As such, the appropriate method in the {@link UmlVisitor} will be called
     * @param visitor the visitor on which the operation to be performered is defined.
     */
    void accept(UmlVisitor visitor);

}
