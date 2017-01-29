package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.codeinfo.MathOperator;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;

/**
 *
 * @author jafar
 */
public class InstanceOperatorAnalyzer extends TSStatementAnalyzer {

    enum RegexGroup implements IRegexGroup {
        OperatorSymbol, ThisModifier, Statement, AssignmentTarget
    }
    
    public InstanceOperatorAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }

    @Override
    public StatementInfo analyzeStatement(String line, String fileName, int lineNumber) 
            throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(line).find()) {
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                statementInfo.statementType = StatementType.MathOperation;
                statementInfo.mathOperation = new MathOperator(m.group(RegexGroup.OperatorSymbol.name()));
                statementInfo.mathOperation.hasKeywordThis = m.group(RegexGroup.ThisModifier.name()) != null;
                statementInfo.mathOperation.operatedValue = tsAnalyzer.analyzeAssignedLiteral(
                        m.group(RegexGroup.Statement.name()), fileName, lineNumber
                );
                statementInfo.mathOperation.assignmentTarget = m.group(RegexGroup.AssignmentTarget.name());
            }
        }
        return statementInfo;
    }
}
