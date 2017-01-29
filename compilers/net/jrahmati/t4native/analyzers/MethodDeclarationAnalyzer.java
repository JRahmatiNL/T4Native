package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.codeinfo.AccessModifier;
import net.jrahmati.t4native.codeinfo.MethodDeclaration;
import net.jrahmati.t4native.codeinfo.MethodType;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;
import net.jrahmati.t4native.codeinfo.VariableDeclaration;

/**
 *
 * @author jafar
 */
public class MethodDeclarationAnalyzer extends TSStatementAnalyzer {

    enum RegexGroup implements IRegexGroup {
        MethodName, StaticModifier, MethodArguments, ReturnType, StartBrace,
        Semicolon, AbstractModifier, AccessModifier

    }

    public MethodDeclarationAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }

    @Override
    public StatementInfo analyzeStatement(String line, String fileName, int lineNumber)
            throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(line).find()) {
            statementInfo.statementType = StatementType.MethodDeclaration;
            statementInfo.methodDeclaration = new MethodDeclaration();
            Matcher m = pattern.matcher(line);
            m.find();
            //TODO: Replacae group names with constants
            if (m.group(RegexGroup.MethodName.name()) != null) {
                statementInfo.methodDeclaration.isStatic = m.group(RegexGroup.StaticModifier.name()) != null;
                statementInfo.methodDeclaration.isConstructorMethod
                        = m.group(RegexGroup.MethodName.name()).equals("constructor")
                        && m.group(RegexGroup.ReturnType.name()) == null
                        && !statementInfo.methodDeclaration.isStatic;
                statementInfo.methodDeclaration.methodName = m.group(RegexGroup.MethodName.name());
                if (m.group(RegexGroup.MethodArguments.name()) != null) {
                    String argumentStrings[] = m.group(RegexGroup.MethodArguments.name()).split(",");
                    for (String argumentString : argumentStrings) {
                        VariableDeclaration varDeclaration = new VariableDeclaration();
                        varDeclaration.VariableName = argumentString.split(":")[0].trim();
                        varDeclaration.VariableType = argumentString.split(":")[1].trim();
                        statementInfo.methodDeclaration.declaredMethodArguments.add(varDeclaration);
                    }
                }
                if (m.group(RegexGroup.ReturnType.name()) != null) {
                    statementInfo.methodDeclaration.returnType = m.group(RegexGroup.ReturnType.name());
                }
                if(m.group(RegexGroup.AccessModifier.name()) != null) {
                    String strModifier = m.group(RegexGroup.AccessModifier.name());
                    if(strModifier.equalsIgnoreCase(AccessModifier.Private.name())) {
                        statementInfo.methodDeclaration.accessModifier = AccessModifier.Private;
                    }
                    else if(strModifier.equalsIgnoreCase(AccessModifier.Protected.name())) {
                        statementInfo.methodDeclaration.accessModifier = AccessModifier.Protected;
                    }
                    else if(strModifier.equalsIgnoreCase(AccessModifier.Public.name())) {
                        statementInfo.methodDeclaration.accessModifier = AccessModifier.Public;
                    }
                }
                if (m.group(RegexGroup.StartBrace.name()) != null) {
                    statementInfo.methodDeclaration.methodType = MethodType.ConcreteMethod;
                } else if (m.group(RegexGroup.Semicolon.name()) != null) {
                    if (m.group(RegexGroup.AbstractModifier.name()) != null) {
                        statementInfo.methodDeclaration.methodType = MethodType.AbstractMethod;
                    } else {
                        statementInfo.methodDeclaration.methodType = MethodType.InterfaceMethod;
                    }
                }
            }
        }
        return statementInfo;
    }
}
