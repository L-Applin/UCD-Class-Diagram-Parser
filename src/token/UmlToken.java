package token;

import com.oracle.tools.packager.IOUtils;
import parsing.Delims;
import parsing.UcdParser;
import utils.Utils;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static parsing.GrammarModel.*;
import static parsing.GrammarModel.ClassContent.ATTRIBUTES;
import static parsing.GrammarModel.ClassContent.OPERATIONS;
import static parsing.GrammarModel.Decs.*;

public abstract class UmlToken implements Displayable {

    protected String content, name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public UmlToken(String content, String name) {
        this.content = content;
        this.name = name;
    }

    public String formatContent() {
        UcdParser parser = new UcdParser(content);
        String tmp = parser.replaceNewLineToken();

        BufferedReader bufferedReader = new BufferedReader(new StringReader(tmp));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {

                parser.setTxt(line);
                List<String> elems = parser.splitList();
                elems.forEach(elem -> {
                    String cleaned = parser.setTxt(elem).replaceCustomListSeperator();
                    if (!Utils.containsAny(cleaned,
                            GENERALIZATION, ASSOCIATION, AGGREGATION, ATTRIBUTES, OPERATIONS, MODEL_TAG,
                            SUBCLASSES_TAG, CONTAINER_TAG, ROLES_TAG, PARTS_TAG)
                        && Utils.containsAny(content,
                                GENERALIZATION, ASSOCIATION, AGGREGATION, ATTRIBUTES, OPERATIONS, MODEL_TAG,
                                SUBCLASSES_TAG, CONTAINER_TAG, ROLES_TAG, PARTS_TAG)) {
                        String tabbed = "\t" + cleaned;
                        sb.append(tabbed).append("\n");
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


    private String formatTypeSeperator(String txt){
        Matcher matcher = Pattern.compile(Delims.TYPE_SEPARATOR).matcher(txt);
        return matcher.replaceAll(Delims.SPACE + Delims.TYPE_SEPARATOR + Delims.SPACE);

    }
}
