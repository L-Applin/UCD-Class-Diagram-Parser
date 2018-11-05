package parsing;

import parsing.syntaxTree.exceptions.ExceptionCheckProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parsing.GrammarModel.NEW_LINE_TOKEN;

/**
 * The class provides different way to read, open and clean up a .ucd File.
 */
public class UcdFileReader implements ExceptionCheckProvider{

    /**
     * The path of the file to open
     */
    String path;

    /**
     * The constructor only needs to know te path of the file to open. Minimum operation is done
     * on object creation. The hard work is done in the {@link UcdFileReader#readAndCleanFile()} method
     * provided by this class.
     * @param path the path of the File to open
     */
    public UcdFileReader(String path){
        this.path = path;
    }


    /**
     * Reads and cleans up the .ucd file. The method will modify the file in different ways.
     * First, it removes all spaces from the beginning and end of the file. Also, it will remove all
     * superflous duplicate splaces and replace them with a single space.
     * Then, it removes spaces between ":". Finally, it removes all newLine token that follows a comma.
     * This last step is executed to better analyse the different part of the file.
     *
     * @return the String that represents the content of the ucd file with modifications.
     * @throws IOException
     */
    public String readAndCleanFile() throws IOException {

        if (!path.endsWith(".ucd")){
            throw new IOException(
                    String.format("File %s does not have .ucd extension",
                            path.substring(path.lastIndexOf(File.separator)+1)));
        }

        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        while((line = bufferedReader.readLine()) != null) {

            checkIllegalChar(line); // from ExceptionCheckProvider interface

            Matcher matcher;

            // remove spaces at beginning and end of a new line
            matcher = Pattern.compile("(^ +)|($ +)").matcher(line);
            if (matcher.find()){
                line = matcher.replaceAll("");
            }

            // remove duplicates places
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
        Matcher matcher = Pattern.compile(","+NEW_LINE_TOKEN).matcher(firstPass);
        String result = matcher.replaceAll(",");


        return result;
    }

    /**
     * Simply returns the text as-is of a file
     * @return String representation of the content of the file
     */
    public String simpleReader() {
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

            while((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();

        } catch (IOException ioe){
            ioe.printStackTrace();
            return "";
        }

    }


}
