package net.jrahmati.t4native;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author jafar see https://regex101.com/r/qeLbBB/3 for testing don't forget to
 * remove escape characters
 */
public class CodePatterns {

    private static final HashMap<RegexCategory, Pattern> REGEX = new HashMap<>();

    public static final Pattern Get(RegexCategory category) {
        loadRegexMap();
        return REGEX.get(category);
    }

    private static void loadRegexMap() {
        if (!REGEX.isEmpty()) {
            return;
        }

        Properties REGEX_PROPERTIES = new Properties();
        try {
            URL url = CodePatterns.class.getResource("regex.properties");
            File file = new File(url.toURI());
            FileReader fileReader = new FileReader(file);
            REGEX_PROPERTIES.load(fileReader);
            for (RegexCategory regexPattern : RegexCategory.values()) {
                String patternToCompile
                        = REGEX_PROPERTIES.getProperty(regexPattern.name()).replaceAll("\\s*", "");
                try {
                    Pattern pattern = Pattern.compile(patternToCompile);
                    REGEX.put(regexPattern, pattern);
                }
                catch(Exception ex) {
                    throw ex;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CodePatterns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CodePatterns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(CodePatterns.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
