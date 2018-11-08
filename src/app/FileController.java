package app;

import parsing.UcdFileReader;
import token.CsvFormatter;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import screenDisplay.MainDisplay;

import java.io.*;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;

public class FileController {

    // private static final String default_file_path = "res";
    private static final String csv_title_long = "Chemin, nom, taille, NLOC, CLOC, ANA, NOM, NOA, ITC, ETC, CAC, DIT, CLD, NOC, NOD";
    private static final String csv_title_short = "Nom, ANA, NOM, NOA, ITC, ETC, CAC, DIT, CLD, NOC, NOD";

    private static FileChooser.ExtensionFilter ucd_extension_filer =
            new FileChooser.ExtensionFilter("UCD", "*.ucd");


    /**
     * Allows a .ucd to be opened from a "popup" that ask tu user to choose a File. Only .ucd file
     * will be considered because a filter is used to black othe type of files.
     * @param main the mainDisplay on which to display the current model
     */
    public void openUcdFileFromSystemExplorer(MainDisplay main){
        String title = "Choose .ucd file";
        FileChooser fc = new FileChooser();
        fc.setTitle(title);
        fc.getExtensionFilters().add(ucd_extension_filer);
        // fc.setInitialDirectory(new File(default_file_path));

        File file = fc.showOpenDialog(main.getPrimaryStage());

        if (file != null && file.exists()) {
            main.getController().lauchUcdActivity(main, file);
        }

    }

    /**
     * Entry point for the creation of the csv file from a Collection of elements that can be transformed
     * into a csv element.
     * @param fileName the name of the file to save
     * @param elements This is the collections of element that will be saved in the csv file.
     *                 Each element will be representedas a row in the csv file
     * @return the file that represent the location on disk where the csv file was created
     * @throws IOException
     */
    public File createCsvFile(String fileName, Collection<? extends CsvFormatter> elements) throws IOException {

        // filename is model name
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Veuillez choisir une destination");

        // append date to filename
        File directory = dirChooser.showDialog(new Stage());
        String currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis()).toString();

        if (directory!= null) {
            
            File toSave = new File(directory.getAbsoluteFile() + File.separator + fileName + "_" + currentTime.substring(0, currentTime.length() - 13)  + ".csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(toSave));

            boolean create_self_file_ucd = Main.CREATE_SELF_UCD && fileName.equals(Main.SELF_MODEL_NAME);

            writer.write((create_self_file_ucd ? csv_title_long : csv_title_short) + "\n");

            elements.forEach(row -> {
                try {
                    writer.write(row.csvFormat(create_self_file_ucd));
                } catch (IOException ioe) {
                    // todo
                }
            });
            writer.close();

            return toSave;
        } else return null;

    }

    /**
     * used to create a ucd file based on the content of the java files of the current project.
     * Should only be used when a new .ucd file is required. {@link Main#CREATE_SELF_UCD} is used to
     * control if this method is executed or not. It will generate all CLASS tag automatically then append
     * manual.txt (provided it is in the same folder) content to the created file.
     * @param path the path to where the new file will be saved. Also where manual.txt needs to be.
     * @return the file that represent the location on disk where the ucd file was created
     * @throws IOException
     */
    public static File createUcdFileFromJavaClass(String path) throws IOException {

        String ucdContent = JavaAnalyzer.toUcdFile();

        String manual = new UcdFileReader(path + "manual.txt").simpleReader();
        File toSave = new File(path + "parser_auto2.ucd");

        BufferedWriter writer = new BufferedWriter(new FileWriter(toSave));
        writer.write("MODEL Parser");
        writer.write(ucdContent);
        for (int i = 0; i < 5; i++) {
            writer.newLine();
        }
        writer.write(manual);
        writer.close();
        return toSave;
    }

    /**
     * Counts NLOC and CLOC of a file
     * @param path path of the file to count the lines
     * @return
     * @throws IOException if someting happends during file opening
     */
    public LineCount countLines(Path path) throws IOException {
        int cLOC = 0;
        int total = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
        String line;
        while((line = bufferedReader.readLine()) != null) {
            String currentLine = line.trim();
            if (!currentLine.equals("")){
                total++;
            }
            if ((currentLine.startsWith("//") || currentLine.startsWith("/*")) || currentLine.startsWith("*")){
                cLOC++;
            }
        }

        return new LineCount(total - cLOC, cLOC);
    }

    public static class LineCount {
        public final int nLoc, cLoc;
        public LineCount(int nLoc, int cLoc) { this.nLoc = nLoc; this.cLoc = cLoc; }
    }

    public void calculateTotal(File file){

    }

}
