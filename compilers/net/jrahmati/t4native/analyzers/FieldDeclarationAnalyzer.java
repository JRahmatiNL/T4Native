package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.codeinfo.AccessModifier;
import net.jrahmati.t4native.codeinfo.FieldDeclaration;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;

/**
 *
 * @author jafar
 */
public class FieldDeclarationAnalyzer extends TSStatementAnalyzer {

    public FieldDeclarationAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }

    enum RegexGroup implements IRegexGroup {
        AccessModifier, StaticModifier, VariableName, VariableType, AssignedStatement        
    }

    @Override
    public StatementInfo analyzeStatement(String line, String fileName, int lineNumber)
            throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(line).find()) {
            statementInfo.statementType = StatementType.FieldDeclaration;
            statementInfo.fieldDeclaration = new FieldDeclaration();
            Matcher m = pattern.matcher(line);
            if(m.find()){
                if (m.group(RegexGroup.AccessModifier.name()) != null) {
                    for (AccessModifier modifier : AccessModifier.values()) {
                        if (modifier.name().equals(m.group(RegexGroup.AccessModifier.name()))) {
                            statementInfo.fieldDeclaration.accessModifier = modifier;
                            break;
                        }
                    }
                }
                //TODO: Replacae group indexes with name constants
                if (m.group(RegexGroup.StaticModifier.name()) != null) {
                    statementInfo.fieldDeclaration.isStatic = true;
                }
                statementInfo.fieldDeclaration.VariableName = m.group(RegexGroup.VariableName.name());
                statementInfo.fieldDeclaration.VariableType = m.group(RegexGroup.VariableType.name());
                String statement = m.group(RegexGroup.AssignedStatement.name());
                if (statement != null) {
                    statementInfo.fieldDeclaration.assignment = 
                            tsAnalyzer.analyzeAssignedLiteral(statement, fileName, lineNumber);
                }
            }
        }
        return statementInfo;
    }

}
