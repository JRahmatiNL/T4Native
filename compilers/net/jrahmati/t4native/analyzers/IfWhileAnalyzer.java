package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.codeinfo.ConditionOperator;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;

/**
 *
 * @author jafar
 */
public class IfWhileAnalyzer extends TSStatementAnalyzer {

    enum RegexGroup implements IRegexGroup {
        IfWhile,
        Condition
    }

    public IfWhileAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }

    @Override
    public StatementInfo analyzeStatement(String line, String fileName, int lineNumber)
            throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(line).find()) {
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                statementInfo.statementType = StatementType.ConditionalOperator;
                statementInfo.conditionOperator = new ConditionOperator(m.group(RegexGroup.IfWhile.name()));
                String conditionLine = m.group(RegexGroup.Condition.name());
                if(conditionLine.contains("(") || conditionLine.contains("\"")) {
                    throw new Exception("Method calls and string literals inside while and if statements are not yet supported");
                }
                statementInfo.conditionOperator.condition = tsAnalyzer.analyzeEqualityOperation(
                        m.group(RegexGroup.Condition.name()), fileName, lineNumber
                );
            }
        }
        return statementInfo;
    }

}
