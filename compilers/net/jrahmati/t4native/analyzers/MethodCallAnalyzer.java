package net.jrahmati.t4native.analyzers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.jrahmati.t4native.CodePatterns;
import net.jrahmati.t4native.RegexCategory;
import net.jrahmati.t4native.TSAnalyzer;
import net.jrahmati.t4native.codeinfo.MethodCallInfo;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;

/**
 *
 * @author jafar
 */
public class MethodCallAnalyzer extends TSStatementAnalyzer {

    enum RegexGroup implements IRegexGroup {
        ThisModifier,
        InstanceName,
        Arguments,
        NewModifier
    }

    public MethodCallAnalyzer(Pattern regexPattern) {
        super(regexPattern, RegexGroup.values());
    }

    @Override
    public StatementInfo analyzeStatement(String line, String fileName, int lineNumber)
            throws UnsupportedOperationException, Exception {
        StatementInfo statementInfo = new StatementInfo();
        if (pattern.matcher(line).find()) {
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                statementInfo.statementType = StatementType.MethodCall;
                statementInfo.methodCallInfo = new MethodCallInfo();
                statementInfo.methodCallInfo.hasKeywordThis = m.group(RegexGroup.ThisModifier.name()) != null;
                statementInfo.methodCallInfo.isConstructorCall = m.group(RegexGroup.NewModifier.name()) != null;
                statementInfo.methodCallInfo.name = m.group(RegexGroup.InstanceName.name());
                String argumentsString = m.group(RegexGroup.Arguments.name());
                if (argumentsString != null) {
                    String argumentStrings[]
                            = argumentsString.split(CodePatterns.Get(RegexCategory.CSV_SYMBOL).pattern());
                    for (String argumentString : argumentStrings) {
                        statementInfo.methodCallInfo.passedArguments.add(
                            tsAnalyzer.analyzeLine(argumentString, fileName, lineNumber)
                        );
                    }
                }
            }
        }
        return statementInfo;
    }

}
