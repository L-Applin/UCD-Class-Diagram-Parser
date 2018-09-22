package parsing;

import parsing.Delims;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parsing.Delims.NEW_LINE_TOKEN;

public class UcdFileReader {

    String path;

    public UcdFileReader(String path){
        this.path = path;
    }

    /**
     * Reads and cleand up the .ucd file
     * @return
     * @throws IOException
     */
    public String readAndCleanFile() throws IOException {

        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        while((line = bufferedReader.readLine()) != null) {

            Matcher matcher;

            // remove spaces at beginning and end of a new line
            matcher = Pattern.compile("(^ +)|($ +)").matcher(line);
            if (matcher.find()){
                line = matcher.replaceAll("");
            }

            // remove duplicate splaces
            matcher = Pattern.compile(" +").matcher(line);
            if (matcher.find()){
                line = matcher.replaceAll(" ");
            }

            // remove spaces between ":"
            matcher = Pattern.compile(" +: +").matcher(line);
            if (matcher.find()){
                line = matcher.replaceAll(":");
            }

            sb.append(line);

            // new line token^
            sb.append(NEW_LINE_TOKEN);

        }

        bufferedReader.close();

        String firstPass = sb.toString();

        // remove new line after commas
        Matcher matcher = Pattern.compile(","+Delims.NEW_LINE_TOKEN).matcher(firstPass);
        return matcher.replaceAll(",");
    }

}
