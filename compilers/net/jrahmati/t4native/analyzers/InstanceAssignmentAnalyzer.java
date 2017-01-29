package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.CodePatterns;
import net.jrahmati.t4native.RegexCategory;
import net.jrahmati.t4native.codeinfo.InstanceLiteralInfo;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;

/**
 * Refers to instance literals that are assigned to other instances
 * @author jafar
 */
public class InstanceAssignmentAnalyzer extends TSStatementAnalyzer {
    
    enum RegexGroup implements IRegexGroup {
        ThisModifier, InstanceName
        
    }

    public InstanceAssignmentAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }

    @Override
    public StatementInfo analyzeStatement(String operatedInstance, String fileName, int lineNumber) 
            throws UnsupportedOperationException, Exception {
        if (CodePatterns.Get(RegexCategory.RETURN_STATEMENT).matcher(operatedInstance).find()) {
            throw new Exception(
                    "a return statment can not be used in minus, plus, divide, multiply and assigment operators"
            );
        }
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(operatedInstance).find()) {
            Matcher m = pattern.matcher(operatedInstance);
            if (m.find()) {
                statementInfo.statementType = StatementType.InstanceLiteral;
                statementInfo.instanceLiteralInfo = new InstanceLiteralInfo();
                statementInfo.instanceLiteralInfo.hasKeywordThis = 
                        m.group(RegexGroup.ThisModifier.name()) != null;
                statementInfo.instanceLiteralInfo.value = m.group(RegexGroup.InstanceName.name());
            }
        } else {
            return tsAnalyzer.analyzeLine(operatedInstance, fileName, lineNumber);
        }
        return statementInfo;
    }
    
}