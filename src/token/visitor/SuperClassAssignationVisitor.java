package token.visitor;

import token.*;

/**
 * Sets the super class.
 */
public class SuperClassAssignationVisitor extends UmlClassVisitor {

    public SuperClassAssignationVisitor() {
        super(clazz -> clazz.getSubClasses().values().forEach(
                child -> ((UmlClass)child).setSuperClass(clazz)));
    }

}
