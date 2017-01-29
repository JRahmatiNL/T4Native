package net.jrahmati.t4native;

import net.jrahmati.t4native.analyzers.EqualityOperationAnalyzer;
import net.jrahmati.t4native.analyzers.LocalVariableDeclarationAnalyzer;
import net.jrahmati.t4native.analyzers.InstanceAssignmentAnalyzer;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StatementType;
import java.util.ArrayList;
import net.jrahmati.t4native.analyzers.ClassDeclarationAnalyzer;
import net.jrahmati.t4native.analyzers.FieldDeclarationAnalyzer;
import net.jrahmati.t4native.analyzers.IfWhileAnalyzer;
import net.jrahmati.t4native.analyzers.ImportFileAnalyzer;
import net.jrahmati.t4native.analyzers.InterfaceDeclarationAnalyzer;
import net.jrahmati.t4native.analyzers.TSStatementAnalyzer;
import net.jrahmati.t4native.analyzers.InstanceOperatorAnalyzer;
import net.jrahmati.t4native.analyzers.MethodCallAnalyzer;
import net.jrahmati.t4native.analyzers.MethodDeclarationAnalyzer;
import net.jrahmati.t4native.analyzers.ReturnStatementAnalyzer;
import net.jrahmati.t4native.analyzers.StringConcatenationAnalyzer;
import net.jrahmati.t4native.analyzers.StringLiteralAnalyzer;

/**
 *
 * @author jafar
 */
public class TSAnalyzer {

    private static TSAnalyzer INSTANCE;

    public synchronized static TSAnalyzer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TSAnalyzer();
        }
        return INSTANCE;
    }

    private static final ArrayList<TSStatementAnalyzer> ANALYZERS = new ArrayList<>();
    private static final InstanceAssignmentAnalyzer INSTANCE_ASSIGNMENT_ANALYZER
            = new InstanceAssignmentAnalyzer(CodePatterns.Get(RegexCategory.INSTANCE_LITERAL));
    private static final EqualityOperationAnalyzer EQUALITY_OPERATION_ANALYZER
            = new EqualityOperationAnalyzer(CodePatterns.Get(RegexCategory.EQAULITY_OPERATOR));

    private TSAnalyzer() {
        if (!ANALYZERS.isEmpty()) {
            return;
        }

        ANALYZERS.add(new ImportFileAnalyzer(CodePatterns.Get(RegexCategory.IMPORT_FILE)));
        ANALYZERS.add(new InterfaceDeclarationAnalyzer(CodePatterns.Get(RegexCategory.INTERFACE_DECLARATION)));
        ANALYZERS.add(new ClassDeclarationAnalyzer(CodePatterns.Get(RegexCategory.CLASS_DECLARATION)));
        ANALYZERS.add(new MethodDeclarationAnalyzer(CodePatterns.Get(RegexCategory.METHOD_DECLARATION)));
        ANALYZERS.add(new FieldDeclarationAnalyzer(CodePatterns.Get(RegexCategory.FIELD_DECLARATION)));
        ANALYZERS.add(new LocalVariableDeclarationAnalyzer(CodePatterns.Get(RegexCategory.VARIABLE_DECLARATION)));
        ANALYZERS.add(new StringLiteralAnalyzer(CodePatterns.Get(RegexCategory.STRING_LITERAL)));
        ANALYZERS.add(new MethodCallAnalyzer(CodePatterns.Get(RegexCategory.METHOD_CALL)));
        ANALYZERS.add(new InstanceOperatorAnalyzer(CodePatterns.Get(RegexCategory.INSTANCE_TO_STATEMENT_OPERATOR)));
        ANALYZERS.add(new StringConcatenationAnalyzer(CodePatterns.Get(RegexCategory.STRING_CONCATENATION_OPERATOR)));
        ANALYZERS.add(new ReturnStatementAnalyzer(CodePatterns.Get(RegexCategory.RETURN_STATEMENT)));
        ANALYZERS.add(new IfWhileAnalyzer(CodePatterns.Get(RegexCategory.IF_WHILE_OPERATOR)));
    }

    public StatementInfo analyzeLine(String line, String fileName, int lineNumber)
            throws UnsupportedOperationException, Exception {

        StatementInfo statementInfo = new StatementInfo();
        statementInfo.leadingSpace = line.replaceAll("^(\\s+).+", "$1");
        if (line.trim().startsWith("//")) {
            statementInfo.statementType = StatementType.Comment;
            statementInfo.Comment = line;
        }
        if (line.trim().isEmpty()) {
            statementInfo.statementType = StatementType.EmptyLine;
            statementInfo.leadingSpace = line;
        } else if (line.trim().equals("}")) {
            statementInfo.statementType = StatementType.EndingBrace;
            statementInfo.leadingSpace = line.substring(0, line.lastIndexOf("}"));
        }

        for (int i = 0; i < ANALYZERS.size() && statementInfo.statementType == StatementType.Unknown; i++) {
            TSStatementAnalyzer analyzer = ANALYZERS.get(i);
            analyzer.validateRegex();
            statementInfo = analyzer.analyzeStatement(line, fileName, lineNumber);
        }

        if (statementInfo.statementType == StatementType.Unknown) {
            throw new UnsupportedOperationException("Could not parse: " + line.trim());
        } else {
            //System.out.println(lineNumber + "> " + statementInfo.statementType.name() + " found: " + line);
        }
        return statementInfo;
    }

    public StatementInfo analyzeAssignedLiteral(String operatedInstance, String fileName, int lineNumber)
            throws Exception {
        return INSTANCE_ASSIGNMENT_ANALYZER.analyzeStatement(operatedInstance, fileName, lineNumber);
    }

    public StatementInfo analyzeEqualityOperation(String operation, String fileName, int lineNumber)
            throws Exception {
        return EQUALITY_OPERATION_ANALYZER.analyzeStatement(operation, fileName, lineNumber);
    }
}
