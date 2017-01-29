package net.jrahmati.t4native.codeinfo;

/**
 *
 * @author jafar
 */
public class StatementInfo {
    
    /**
     * refers to the file name (without extension) where the statement occurs
     */
    public String attachedFile;
    
    public String leadingSpace;
    public StatementType statementType = StatementType.Unknown;
    
    public String Comment;
    public String importedPackage;
    public StringLiteralInfo stringLiteralInfo;
    public InstanceLiteralInfo instanceLiteralInfo;
    public MethodCallInfo methodCallInfo;
    public InterfaceDeclaration interfaceDeclaration;
    public ClassDeclaration classDeclaration;
    public MethodDeclaration methodDeclaration;
    public FieldDeclaration fieldDeclaration;
    public LocalVariableDeclaration localVariableDeclaration;
    public MathOperator mathOperation;
    public StringConcatenation stringConcatenation;
    public StatementInfo returnStatementInfo;
    public ConditionOperator conditionOperator;
    public EqualityOperator equalityOperator;
}
