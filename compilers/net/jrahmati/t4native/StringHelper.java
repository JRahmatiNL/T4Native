package net.jrahmati.t4native;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 *
 * @author jafar
 */
public class StringHelper {
    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }
    
    public static String fixedLengthString(String string, int length) {
        if(length == 0){
            return string;
        }
        return String.format("%1$"+length+ "s", string);
    }

    public static String addPrefixAfterLeadingSpaces(String prefix, String line) {
        int spacesToAdd = line.length() - line.trim().length();
        return StringHelper.fixedLengthString(" ", spacesToAdd) + prefix + line.trim();
    }

    static String convertSingleQutoedValuesToDoubleQuotedValues(String compiledLine) {
        String singleQuoteRegex = "'[^'\"]*'(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        Pattern singleQuotePattern = Pattern.compile(singleQuoteRegex);
        if (singleQuotePattern.matcher(compiledLine).find()) {
            compiledLine = compiledLine.replaceAll("\"", "\\\"");
            compiledLine = compiledLine.replace("\\'", "'");
            compiledLine = compiledLine.replaceFirst("'", "\"");
            compiledLine = StringHelper.replaceLast(compiledLine, "'", "\"");
        }
        return compiledLine;
    }

    static ArrayList<String> getCommentLinesFromClassByTag(Class aClass, String tag) {
        File fileEntry = new File(String.format("test/%s.java", aClass.getName().replace(".", "/")));
        ArrayList<String> values = new ArrayList<>();
        boolean startFilling = false;
        try (final BufferedReader br = new BufferedReader(new FileReader(fileEntry.getPath()))) {
                for (String line; (line = br.readLine()) != null;) {
                    if(!startFilling && line.trim().startsWith(String.format("* %s:", tag))) {
                        startFilling = true;
                    }
                    else if(startFilling) {
                        if(!line.trim().equals("* :end")) {
                            values.add(line.replaceFirst("\\*", ""));
                        }
                        else {
                            return values;
                        }
                    }
                }
        }
        catch(Exception ex) {
            System.out.println("error: " + ex.getLocalizedMessage());
        }
        return values;
    }
}
