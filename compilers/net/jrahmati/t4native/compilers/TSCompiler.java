package net.jrahmati.t4native.compilers;

import net.jrahmati.t4native.TSAnalyzer;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.ClassDeclaration;
import net.jrahmati.t4native.codeinfo.ConditionOperator;
import net.jrahmati.t4native.codeinfo.EqualityOperator;
import net.jrahmati.t4native.codeinfo.FieldDeclaration;
import net.jrahmati.t4native.codeinfo.InstanceLiteralInfo;
import net.jrahmati.t4native.codeinfo.InterfaceDeclaration;
import net.jrahmati.t4native.codeinfo.LocalVariableDeclaration;
import net.jrahmati.t4native.codeinfo.MathOperator;
import net.jrahmati.t4native.codeinfo.MethodCallInfo;
import net.jrahmati.t4native.codeinfo.MethodDeclaration;
import net.jrahmati.t4native.codeinfo.StringConcatenation;
import net.jrahmati.t4native.codeinfo.StringLiteralInfo;

/**
 *
 * @author jafar
 */
public abstract class TSCompiler {
    private static TSAnalyzer tsAnalyzer = TSAnalyzer.getInstance();
    
    public String CompileLine(String line, String fileName, int lineNumber) throws Exception {
        try {
            StatementInfo statement = tsAnalyzer.analyzeLine(line, fileName, lineNumber);
            statement.attachedFile = fileName.replaceFirst("[.][^.]+$", "");
            if(statement.leadingSpace == null) {
                statement.leadingSpace = "";
            }
            return statement.leadingSpace + CompileStatement(statement, ParentChildRelation.Parent);
        } catch (Exception ex) {
            throw new Exception(String.format(
                "Exception occured in %s on line %d while parsing: %s\nException details: %s",
                fileName, lineNumber, line, ex.getMessage()
            ));
        }
    }
    
    public String CompileStatement(StatementInfo statementInfo, ParentChildRelation parentChildRelation) throws Exception {
        switch(statementInfo.statementType) {
            case Comment:
                return CompileComment(statementInfo.Comment, parentChildRelation);
            case ImportDeclaration:
                return CompileImportDeclaration(statementInfo.importedPackage, parentChildRelation);
            case ClassDeclaration:
                return CompileClassDeclaration(statementInfo.classDeclaration, parentChildRelation);
            case InterfaceDeclaration:
                return CompileInterfaceDeclaration(statementInfo.interfaceDeclaration, parentChildRelation);
            case MethodDeclaration:
                statementInfo.methodDeclaration.attachedFile = statementInfo.attachedFile;
                return CompileMethodDeclaration(statementInfo.methodDeclaration, parentChildRelation);
            case FieldDeclaration:
                return CompileFieldDeclaration(statementInfo.fieldDeclaration, parentChildRelation);
            case LocalVariableDeclaration:
                return CompileLocalVariableDeclaration(statementInfo.localVariableDeclaration, parentChildRelation);
            case StringLiteral:
                return CompileStringLiteral(statementInfo.stringLiteralInfo, parentChildRelation);
            case MethodCall:
                return CompileMethodCall(statementInfo.methodCallInfo, parentChildRelation);
            case MathOperation:
                return CompileMathOperation(statementInfo.mathOperation, parentChildRelation);
            case StringConcatenation:
                return CompileStringConcatenation(statementInfo.stringConcatenation, parentChildRelation);
            case ReturnStatement:
                return CompileReturnStatement(statementInfo.returnStatementInfo, parentChildRelation);
            case ConditionalOperator:
                return CompileConditionalOperator(statementInfo.conditionOperator, parentChildRelation);
            case InstanceLiteral:
                return CompileInstanceLiteral(statementInfo.instanceLiteralInfo, parentChildRelation);
            case EqualityOperator:
                return CompileEquality(statementInfo.equalityOperator, parentChildRelation);
            case EndingBrace:
                return "}";
            case EmptyLine:
                return "";
            case Unknown:
                throw new Exception("Unsupported syntax");
            default:
                throw new Exception("Unsupported by compiler");
        }
    }
    
    protected abstract String CompileClassDeclaration(ClassDeclaration classDeclaration, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileComment(String Comment, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileInterfaceDeclaration(InterfaceDeclaration interfaceDeclaration, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileImportDeclaration(String importedPackage, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileMethodDeclaration(MethodDeclaration methodDeclaration, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileFieldDeclaration(FieldDeclaration fieldDeclaration, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileLocalVariableDeclaration(LocalVariableDeclaration localVariableDeclaration, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileStringLiteral(StringLiteralInfo stringLiteralInfo, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileMethodCall(MethodCallInfo methodCallInfo, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileMathOperation(MathOperator mathOperation, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileStringConcatenation(StringConcatenation stringConcatenation, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileReturnStatement(StatementInfo returnStatementInfo, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileConditionalOperator(ConditionOperator conditionOperator, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileInstanceLiteral(InstanceLiteralInfo instanceLiteralInfo, ParentChildRelation parentChildRelation) throws Exception;

    protected abstract String CompileEquality(EqualityOperator equalityOperator, ParentChildRelation parentChildRelation) throws Exception;
}
