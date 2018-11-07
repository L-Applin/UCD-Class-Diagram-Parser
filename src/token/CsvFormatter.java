package token;

import java.util.Collection;

/**
 * This Interface propose a way to decouple specific objects from the {@link app.FileController} class
 * by providing an specific method that all classes that needs to be converted into a csv row can implement.
 */
public interface CsvFormatter {

    /**
     * the seperator for this type of csv file
     */
    String SEPERATOR = ",";

    /**
     * This method defines how the class should be converted into a csv row. If multiple class implement
     * this interface and wants to be written to a same .csv file, they must make sure that
     *
     * <ol>
     *     <li>They provide the same number of column</li>
     *     <li>The columns have the same semantics</li>
     *     <li>they provide a line return for each row</li>
     *     <li>They should not insert more than onw row</li>
     *     <li>The class must use {@link CsvFormatter#SEPERATOR} to seperate columns on the same row</li>
     * </ol>
     *
     * If those condition are satisfied, {@link app.FileController#createCsvFile(String, Collection)} can be
     * used without fearing any problems.
     * @param projectUcd tells {@link app.FileController#createCsvFile(String, Collection)} if it should try to
     *                   create the csvfile for the current project or a ganeral file. Should usually be
     *                   set to false to create a general csv file.
     * @return The string representation of the row to be added to the csv file.
     */
    //todo : remove boolean argument before handing in
    String csvFormat(boolean projectUcd);
}
