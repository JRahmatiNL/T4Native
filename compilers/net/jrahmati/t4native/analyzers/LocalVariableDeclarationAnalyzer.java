package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.codeinfo.LocalVariableDeclaration;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;

/**
 *
 * @author jafar
 */
public class LocalVariableDeclarationAnalyzer extends TSStatementAnalyzer {

    enum RegexGroup implements IRegexGroup {
        VariableName,
        VariableType,
        AssignedStatement
    }

    public LocalVariableDeclarationAnalyzer(Pattern pattern) {
        super(pattern, RegexGroup.values());
    }

    @Override
    public StatementInfo analyzeStatement(String line, String fileName, int lineNumber)
            throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(line).find()) {
            statementInfo.statementType = StatementType.LocalVariableDeclaration;
            statementInfo.localVariableDeclaration = new LocalVariableDeclaration();
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                statementInfo.localVariableDeclaration.VariableName = m.group(RegexGroup.VariableName.name());
                statementInfo.localVariableDeclaration.VariableType = m.group(RegexGroup.VariableType.name());
                String statement = m.group(FieldDeclarationAnalyzer.RegexGroup.AssignedStatement.name());
                if (statement != null) {
                    statementInfo.localVariableDeclaration.assignment
                            = tsAnalyzer.analyzeAssignedLiteral(statement, fileName, lineNumber);
                }
            }
        }
        return statementInfo;
    }
}
