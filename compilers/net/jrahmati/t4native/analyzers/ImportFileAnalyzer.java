package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;

/**
 *
 * @author jafar
 */
public class ImportFileAnalyzer extends TSStatementAnalyzer {

    enum RegexGroup implements IRegexGroup {
        ImportedPackage
    }

    public ImportFileAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }

    @Override
    public StatementInfo analyzeStatement(String line, String fileName, int lineNumber)
            throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(line).find()) {
            statementInfo.statementType = StatementType.ImportDeclaration;
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                String packageName = m.group(RegexGroup.ImportedPackage.name()).replace("/", ".");
                statementInfo.importedPackage = packageName;
            }
        }
        return statementInfo;
    }
}
