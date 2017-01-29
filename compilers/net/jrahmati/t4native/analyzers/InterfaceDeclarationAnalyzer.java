package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.codeinfo.InterfaceDeclaration;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;

/**
 *
 * @author jafar
 */
public class InterfaceDeclarationAnalyzer extends TSStatementAnalyzer {

    enum RegexGroup implements IRegexGroup {
        InterfaceName, ImplementedInterfaces
        
    }
    
    public InterfaceDeclarationAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }

    @Override
    public StatementInfo analyzeStatement(String line, String fileName, int lineNumber) throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(line).find()) {
            statementInfo.statementType = StatementType.InterfaceDeclaration;
            statementInfo.interfaceDeclaration = new InterfaceDeclaration();
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                statementInfo.interfaceDeclaration.Name = m.group(RegexGroup.InterfaceName.name());
                if (!fileName.equals(statementInfo.interfaceDeclaration.Name + ".ts")) {
                    throw new Exception(
                            statementInfo.interfaceDeclaration.Name
                            + " should be declared in a file named "
                            + statementInfo.interfaceDeclaration.Name
                    );
                }
                if (m.group("ImplementedInterfaces") != null) {
                    String[] interfaces = m.group(RegexGroup.ImplementedInterfaces.name()).split(",");
                    for (String $interface : interfaces) {
                        statementInfo.interfaceDeclaration.implementedInterfaces.add($interface.trim());
                    }
                }
            }
        }
        return statementInfo;
    }

}
