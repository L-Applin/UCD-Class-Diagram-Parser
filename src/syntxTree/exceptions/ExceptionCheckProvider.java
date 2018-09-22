package syntxTree.exceptions;

public interface ExceptionCheckProvider {

    /**
     * Verify
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
}
