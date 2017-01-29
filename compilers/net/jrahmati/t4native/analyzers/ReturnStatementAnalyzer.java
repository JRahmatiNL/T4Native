package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;

/**
 *
 * @author jafar
 */
public class ReturnStatementAnalyzer extends TSStatementAnalyzer {

    enum RegexGroup implements IRegexGroup {
        ReturnedStatement
    }
    
    public ReturnStatementAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }

    @Override
    public StatementInfo analyzeStatement(String line, String fileName, int lineNumber) 
            throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(line).find()) {
            statementInfo.statementType = StatementType.ReturnStatement;
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                String returnStatement = m.group(RegexGroup.ReturnedStatement.name());
                statementInfo.returnStatementInfo = 
                        tsAnalyzer.analyzeLine(returnStatement, fileName, lineNumber);
            }
        }
        return statementInfo;
    }   
}