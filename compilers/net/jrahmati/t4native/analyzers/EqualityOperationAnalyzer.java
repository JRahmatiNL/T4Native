package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.codeinfo.EqualityOperator;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;

/**
 *
 * @author jafar
 */
public class EqualityOperationAnalyzer extends TSStatementAnalyzer {
    
    enum RegexGroup implements IRegexGroup {
        EqualityOperator, Operand1, Operand2
        
    }

    public EqualityOperationAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }

    @Override
    public StatementInfo analyzeStatement(String operation, String fileName, int lineNumber) 
            throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(operation).find()) {
            Matcher m = pattern.matcher(operation);
            if (m.find()) {
                statementInfo.statementType = StatementType.EqualityOperator;
                statementInfo.equalityOperator = 
                        new EqualityOperator(m.group(RegexGroup.EqualityOperator.name()));
                String strOperand1 = m.group(RegexGroup.Operand1.name());
                String strOperand2 = m.group(RegexGroup.Operand2.name());
                statementInfo.equalityOperator.operand1 = 
                        tsAnalyzer.analyzeAssignedLiteral(strOperand1, fileName, lineNumber);
                statementInfo.equalityOperator.operand2 = 
                        tsAnalyzer.analyzeAssignedLiteral(strOperand2, fileName, lineNumber);
            }
        }
        return statementInfo;
    }
    
}
