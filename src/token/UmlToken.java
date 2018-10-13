package token;

import parsing.Delims;
import parsing.UcdParser;
import token.visitor.UmlVisitorElement;
import utils.Utils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parsing.GrammarModel.*;
import static parsing.GrammarModel.ClassContent.ATTRIBUTES;
import static parsing.GrammarModel.ClassContent.OPERATIONS;
import static parsing.GrammarModel.Decs.*;

/**
 * General representation of a uml parameter.
 */
public abstract class UmlToken implements Displayable, UmlVisitorElement {

    /**
     * The name (or tag) of the token. This is the key that is used in the various map attributes
     * of classes that extend UmlToken. Example: {@link UmlClass#attributes}.
     */
    protected String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    /**
     * The String content of the class, as declared by the .ucd file that was parsed. This text, however,
     * is formated to be easier to parse so it contains some character that is not proper for display.
     * Use {@link UmlToken#formatContent()} to get a nice String that is formatted for display.
     */
    protected String content;
    public void setContent(String content) { this.content = content; }

    public UmlToken(String content, String name) {
        this.content = content;
        String clean = UcdParser.removeSpaces(name);
        this.name = clean;
    }

    /**
     * Formats the String content of the token for display purpose.
     * @return the String ready to be displayed,
     */
    public String formatContent() {
        UcdParser parser = new UcdParser(content);
        String tmp = parser.replaceNewLineToken();

        BufferedReader bufferedReader = new BufferedReader(new StringReader(tmp));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {

                parser.setTxt(line);
                parser.splitList().forEach(elem -> {

                    // remove custom line breaks
                    String cleaned = parser.setTxt(elem).replaceCustomListSeperator();

                    // add indent for cleaner display
                    if (!Utils.containsAny(cleaned,
                            GENERALIZATION, ASSOCIATION, AGGREGATION, ATTRIBUTES, OPERATIONS, MODEL_TAG,
                            SUBCLASSES_TAG, CONTAINER_TAG, ROLES_TAG, PARTS_TAG)
                        && Utils.containsAny(content,
                                GENERALIZATION, ASSOCIATION, AGGREGATION, ATTRIBUTES, OPERATIONS, MODEL_TAG,
                                SUBCLASSES_TAG, CONTAINER_TAG, ROLES_TAG, PARTS_TAG)) {
                        sb.append("\t").append(cleaned).append("\n");
                    } else {
                        sb.append(elem).append("\n");
                    }
                });
            }
            return formatTypeSeperator(sb.toString());
        } catch (IOException ioe) {
            return formatTypeSeperator(tmp);
        }

    }

    /**
     * replaces custom type separator with regular separator and space
     * @param txt the text to convert
     * @return the text with its custom separator removed
     */
    private String formatTypeSeperator(String txt){
        Matcher matcher = Pattern.compile(Delims.TYPE_SEPARATOR).matcher(txt);
        return matcher.replaceAll(Delims.SPACE + Delims.TYPE_SEPARATOR + Delims.SPACE);
    }
}
