package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;
import net.jrahmati.t4native.codeinfo.StringLiteralInfo;

/**
 *
 * @author jafar
 */
public class StringLiteralAnalyzer extends TSStatementAnalyzer {

    enum RegexGroup implements IRegexGroup {
        StringValue
    }

    public StringLiteralAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }

    @Override
    public StatementInfo analyzeStatement(String line, String fileName, int lineNumber)
            throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(line).find()) {
            statementInfo.statementType = StatementType.StringLiteral;
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                statementInfo.stringLiteralInfo = new StringLiteralInfo();
                statementInfo.stringLiteralInfo.value = m.group(RegexGroup.StringValue.name());
            }
        }
        return statementInfo;
    }

}
