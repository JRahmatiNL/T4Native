package net.jrahmati.t4native.analyzers;

import java.util.regex.Pattern;
import net.jrahmati.t4native.TSAnalyzer;
import net.jrahmati.t4native.codeinfo.StatementInfo;

/**
 *
 * @author jafar
 */
public abstract class TSStatementAnalyzer {
    
    Pattern pattern;
    private IRegexGroup[] regexGroups = null;
    static TSAnalyzer tsAnalyzer = TSAnalyzer.getInstance();
    public TSStatementAnalyzer(Pattern regexPattern, IRegexGroup[] regexGroups) {
        this.pattern = regexPattern;
        this.regexGroups = regexGroups;
    }

    public abstract StatementInfo analyzeStatement(String line, String fileName, int lineNumber)
            throws UnsupportedOperationException, Exception;

    public void validateRegex() throws Exception {
        for (IRegexGroup regexGroup : regexGroups) {
            if(!pattern.pattern().contains(String.format("?<%s>", regexGroup.name()))) {
                Exception ex = new Exception("Expected regex group not found in pattern: " + regexGroup.name());
                System.err.println("Error in analyzer class: " + this.getClass().getName());
                System.err.println(ex.getMessage());
                throw ex;
            }
        }
    }
}
