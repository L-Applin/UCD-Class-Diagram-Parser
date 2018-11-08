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


    public File createCsvFile(String fileName, Collection<? extends CsvFormatter> elements) throws IOException {

        // filename is model name
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Veuillez choisir une destination");

        // append date to filename
        File directory = dirChooser.showDialog(new Stage());
        String currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis()).toString();
        File toSave = new File(directory.getAbsoluteFile() + File.separator + fileName + "_" + currentTime.substring(0, currentTime.length() - 13)  + ".csv");
        BufferedWriter writer = new BufferedWriter(new FileWriter(toSave));

        boolean create_self_file_ucd = Main.CREATE_SELF_UCD && fileName.equals(Main.SELF_MODEL_NAME);

        writer.write((create_self_file_ucd ? csv_title_long : csv_title_short) + "\n");

        elements.forEach(row -> {
            try {
                writer.write(row.csvFormat(create_self_file_ucd));
            } catch (IOException ioe){
                // todo
            }
        });
        writer.close();

        return toSave;

    }

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
