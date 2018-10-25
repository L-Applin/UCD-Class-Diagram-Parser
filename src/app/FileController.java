package app;

import csv.CsvFormatter;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import screenDisplay.MainDisplay;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class FileController {

    // private static final String default_file_path = "res";
    private static final String csv_title = "Chemin, nom, taille, NLOC, CLOC, ANA, NOM, NOA, ITC, ETC, CAC, DIT, CLD, NOC, NOD";

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

    public File createCsvFile(String fileName, Collection<? extends CsvFormatter> elements) throws IOException{

        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Veuillez choisir une destination");

        File directory = dirChooser.showDialog(new Stage());
        File toSave = new File(directory.getAbsoluteFile() + File.separator + fileName + ".csv");
        BufferedWriter writer = new BufferedWriter(new FileWriter(toSave));
        writer.write(csv_title + "\n");
        elements.forEach(row -> {
            try {
                writer.write(row.csvFormat());
            } catch (IOException ioe){

            }
        });
        writer.close();

        return toSave;

    }

    public int[] countLines(Path path) throws IOException {
        int nLOC = 0;
        int total = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
        String line;
        while((line = bufferedReader.readLine()) != null) {
            String currentLine = line.trim();
            if (!currentLine.equals("")){
                total++;
            }
            if ((currentLine.startsWith("//") || currentLine.startsWith("//*"))|| currentLine.startsWith("*")){
                nLOC++;
            }
        }

        return new int[]{nLOC, total - nLOC};
    }



}
