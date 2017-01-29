package net.jrahmati.t4native.codeinfo;

/**
 *
 * @author jafar
 */
public enum StatementType {
    Unknown,
    EmptyLine,
    Comment,
    StringLiteral,
    InstanceLiteral,
    MethodCall,
    ImportDeclaration,
    InterfaceDeclaration,
    ClassDeclaration,
    MethodDeclaration,
    LocalVariableDeclaration,
    FieldDeclaration,
    /**
     * Refers to '-' , '=', '+', '/' & '*' operators
     */
    MathOperation,
    StringConcatenation,
    ReturnStatement,
    /**
     * Refers to if and while statements, use this in combination with equality operator
     */
    ConditionalOperator,
    /**
     * Refers to '==' & '!=' operators 
     */
    EqualityOperator,
    EndingBrace
}
