package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;
import net.jrahmati.t4native.codeinfo.StringConcatenation;

/**
 *
 * @author jafar
 */
public class StringConcatenationAnalyzer extends TSStatementAnalyzer {

    enum RegexGroup implements IRegexGroup {
        StringValue,
        OperatorSymbol,
        ConcatenatedValue
    }

    public StringConcatenationAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }

    @Override
    public StatementInfo analyzeStatement(String line, String fileName, int lineNumber)
            throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(line).find()) {
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                statementInfo.statementType = StatementType.StringConcatenation;
                statementInfo.stringConcatenation = new StringConcatenation();
                statementInfo.stringConcatenation.stringValue = m.group(RegexGroup.StringValue.name());
                
                String concatenatedString = m.group(RegexGroup.ConcatenatedValue.name());
                
                if(concatenatedString.contains("(") || concatenatedString.contains("\"")) {
                    throw new Exception("Method calls and string literals inside string concatenations are not yet supported");
                }
                statementInfo.stringConcatenation.concatenatedValue = tsAnalyzer.analyzeAssignedLiteral(
                        concatenatedString, fileName, lineNumber
                );
            }
        }
        return statementInfo;
    }

}
