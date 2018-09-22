package sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parsing.Delims.NEW_LINE_TOKEN;

public class AppController {

    /**
     * Reads and cleand up the .ucd file
     * @param path path of the file to open, read and clean
     * @return
     * @throws IOException
     */
    public String openUCDFile(String path) throws IOException {

        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        while((line = bufferedReader.readLine()) != null) {

            // remove spaces at beginning of a new linee
            Matcher matcher = Pattern.compile("^ +").matcher(line);
            if (matcher.find()){
                line = matcher.replaceAll("");
            }

            sb.append(line);

            // new line token^
            sb.append(NEW_LINE_TOKEN);

        }

        bufferedReader.close();

        return sb.toString();
    }


}
