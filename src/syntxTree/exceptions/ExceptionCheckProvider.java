package syntxTree.exceptions;

import parsing.GrammarModel;

/**
 * Provides default methods for checking different types of possible error while parsing the ucd file.
 */
public interface ExceptionCheckProvider {

    /**
     * Verify that a specified tag is actually at the begining of the text.
     * @param txt
     * @param delim
     * @throws IncompatibleTagException when the actuall tag and the expected valu does not matche.
     */
    default void checkTagEqual(String txt, String delim){

        String[] splits = txt.split(" ", 2);

        if (!splits[0].equals(delim)){
            throw new IncompatibleTagException(splits[0], delim, splits[1]);
        }
    }


    /**
     * Makes sur that class declaration contains OPERATION and ATTRIGUTES
     * @param classContent
     */
    default void checkClassContent(String clazz, String classContent){

        if (!classContent.contains(GrammarModel.ClassContent.OPERATIONS)){
            throw new MissingClassTagException(clazz, classContent, GrammarModel.ClassContent.OPERATIONS);
        }

        if (!classContent.contains(GrammarModel.ClassContent.ATTRIBUTES)){
            throw new MissingClassTagException(clazz, classContent, GrammarModel.ClassContent.ATTRIBUTES);
        }

    }

    /**
     * Makes sur the txt doesnot conatains illegal character
     * @param txt the text to verify
     */
    default void checkIllegalChar(String txt){
        for (String illegal : GrammarModel.illegalChar){
            if (txt.contains(illegal)){
                throw new IllegalCharacterException(txt, illegal);
            }
        }
    }

    default void checkIllegalChar(String txt, String filePath){
        for (String illegal : GrammarModel.illegalChar){
            if (txt.contains(illegal)){
                throw new IllegalCharacterException(txt, illegal, filePath);
            }
        }
    }


    default void checkTagPresent(String txt, String tag, String classId, String content) {
        if (!txt.contains(tag)) {
            throw new MissingClassTagException(classId, content, GrammarModel.ClassContent.OPERATIONS);
        }
    }

    default void checkNoDuplicateTag(String txt, String tag, String classId, String content){
        // check there is only one <attributes> tag
        if (txt.indexOf(tag) != txt.lastIndexOf(tag)){
            MalformedClassException mce = new MalformedClassException("Class \'" + classId + "\' cannot conatains two sets of attributes.");
            mce.setClazz(classId);
            mce.setContent(content);
            throw mce;
        }

    }


}
