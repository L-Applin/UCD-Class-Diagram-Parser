package parsing;

import syntaxTree.exceptions.ExceptionCheckProvider;
import utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parsing.Delims.NEW_LINE_TOKEN;

public class UcdFileReader implements ExceptionCheckProvider{

    String path;

    public UcdFileReader(String path){
        this.path = path;
    }

    /**
     * Reads and clean up the .ucd file. The method will modify the file in different ways.<
     * New lines token will be replaced by {@link Delims#NEW_LINE_TOKEN}.
     * @return the String that represent the content of the ucd file with modifications.
     * @throws IOException
     */
    public String readAndCleanFile() throws IOException {

        if (!path.endsWith(".ucd")){
            throw new IOException(
                    String.format("File %s does not have .ucd extension",
                            path.substring(path.lastIndexOf(File.separator)+1, path.length())));
        }

        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        while((line = bufferedReader.readLine()) != null) {

            checkIllegalChar(line, path); // from ExceptionCheckProvider interface

            Matcher matcher;

            // remove spaces at beginning and end of a new line
            matcher = Pattern.compile("(^ +)|($ +)").matcher(line);
            if (matcher.find()){
                line = matcher.replaceAll("");
                // Utils.Log.test(line);
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

            // new line token
            sb.append(NEW_LINE_TOKEN);

        }

        bufferedReader.close();

        String firstPass = sb.toString();

        // remove new line after commas
        Matcher matcher = Pattern.compile(","+Delims.NEW_LINE_TOKEN).matcher(firstPass);
        String result = matcher.replaceAll(",");


        return result;
    }


}
