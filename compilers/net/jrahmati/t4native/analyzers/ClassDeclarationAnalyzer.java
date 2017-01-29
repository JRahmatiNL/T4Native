package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.codeinfo.ClassDeclaration;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;

/**
 *
 * @author jafar
 */
public class ClassDeclarationAnalyzer extends TSStatementAnalyzer {

    public ClassDeclarationAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }
    
    enum RegexGroup implements IRegexGroup {
        ClassName,
        ExtendedClass,
        ImplementedInterfaces
    }

    @Override
    public StatementInfo analyzeStatement(String line, String fileName, int lineNumber)
            throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(line).find()) {
            statementInfo.statementType = StatementType.ClassDeclaration;
            statementInfo.classDeclaration = new ClassDeclaration();
            Matcher m = pattern.matcher(line);
            m.find();
            statementInfo.classDeclaration.Name = m.group(RegexGroup.ClassName.name());
            if (!fileName.equals(statementInfo.classDeclaration.Name + ".ts")) {
                throw new Exception(
                        statementInfo.classDeclaration.Name
                        + " should be declared in a file named "
                        + statementInfo.classDeclaration.Name
                );
            }
            if (m.group(RegexGroup.ExtendedClass.name()) != null) {
                statementInfo.classDeclaration.extendedClass = m.group(RegexGroup.ExtendedClass.name());
            }
            if (m.group(RegexGroup.ImplementedInterfaces.name()) != null) {
                String[] interfaces = m.group(RegexGroup.ImplementedInterfaces.name()).split(",");
                for (String $interface : interfaces) {
                    statementInfo.classDeclaration.implementedInterfaces.add($interface.trim());
                }
            }
        }
        return statementInfo;
    }
}
