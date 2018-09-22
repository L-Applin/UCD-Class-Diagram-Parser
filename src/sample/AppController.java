package sample;

import parsing.UcdFileReader;

import java.io.IOException;

public class AppController {

    /**
     * Reads and cleand up the .ucd file
     * @param path path of the file to open, read and clean
     * @return
     * @throws IOException
     */
    public String openUCDFile(String path) throws IOException {

        UcdFileReader ucdFile = new UcdFileReader(path);
        return ucdFile.readAndCleanFile();

    }

}
