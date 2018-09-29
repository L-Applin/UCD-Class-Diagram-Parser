package parsing;

import javafx.stage.FileChooser;
import app.AppController;
import screenDisplay.MainDisplay;

import java.io.File;

public class FileController {

    // private static final String default_file_path = "res";

    private static FileChooser.ExtensionFilter ucd_extension_filer =
            new FileChooser.ExtensionFilter("UCD", "*.ucd");


    public void openUcdFIleFromSystemExplorer(MainDisplay main){
        String title = "Choose .ucd file";
        AppController controller = new AppController();
        FileChooser fc = new FileChooser();
        fc.setTitle(title);
        fc.getExtensionFilters().add(ucd_extension_filer);
        // fc.setInitialDirectory(new File(default_file_path));

        File file = fc.showOpenDialog(main.getPrimaryStage());

        if (file != null && file.exists()) {
            controller.lauchUcdActivity(main, file);
        }

    }

}
