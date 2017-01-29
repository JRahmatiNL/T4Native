package net.jrahmati.t4native.compilers;

import java.util.ArrayList;
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
import net.jrahmati.t4native.codeinfo.MethodType;
import net.jrahmati.t4native.codeinfo.StatementInfo;
import net.jrahmati.t4native.codeinfo.StringConcatenation;
import net.jrahmati.t4native.codeinfo.StringLiteralInfo;
import net.jrahmati.t4native.codeinfo.VariableDeclaration;

/**
 *
 * @author jafar
 */
public class TSToJavaCompiler extends TSCompiler {

    @Override
    protected String CompileClassDeclaration(ClassDeclaration classDeclaration, ParentChildRelation parentChildRelation) {
        String returnString = "public class " + classDeclaration.Name;
        if (classDeclaration.extendedClass != null && !classDeclaration.extendedClass.isEmpty()) {
            returnString += " extends " + classDeclaration.extendedClass;
        }
        ArrayList<String> colonParts = new ArrayList<>();
        colonParts.add(String.join(", ", classDeclaration.implementedInterfaces));
        colonParts.removeIf((String t) -> t == null || t.isEmpty());
        if (colonParts.size() > 0) {
            returnString += " implements " + String.join(", ", colonParts);
        }
        returnString += " {";
        return returnString;
    }

    @Override
    protected String CompileComment(String Comment, ParentChildRelation parentChildRelation) {
        return Comment;
    }

    @Override
    protected String CompileInterfaceDeclaration(InterfaceDeclaration interfaceDeclaration, ParentChildRelation parentChildRelation) {
        ArrayList<String> colonParts = new ArrayList<>();
        colonParts.add(interfaceDeclaration.Name);
        ArrayList<String> extensions = new ArrayList<>();
        extensions.addAll(interfaceDeclaration.implementedInterfaces);
        extensions.removeIf((String t) -> t == null || t.isEmpty());
        colonParts.add(String.join(", ", extensions));
        colonParts.removeIf((String t) -> t == null || t.isEmpty());
        return String.format("public interface %s {", String.join(" extends ", colonParts));
    }

    @Override
    protected String CompileImportDeclaration(String importedPackage, ParentChildRelation parentChildRelation) {
        return String.format("import %s;", importedPackage);
    }

    @Override
    protected String CompileMethodDeclaration(MethodDeclaration methodDeclaration, ParentChildRelation parentChildRelation) {
        String returnString = "";
        if (methodDeclaration.accessModifier != null) {
            returnString += methodDeclaration.accessModifier.name().toLowerCase() + " ";
        }
        if (methodDeclaration.isStatic) {
            returnString += "static ";
        }
        if(!methodDeclaration.isConstructorMethod) {
            returnString += methodDeclaration.returnType + " " + methodDeclaration.methodName;
        }
        else {
            returnString += methodDeclaration.attachedFile;
        }
        if (methodDeclaration.declaredMethodArguments.size() > 0) {
            ArrayList<String> argParts = new ArrayList<>();
            for (VariableDeclaration declaredMethodArgument : methodDeclaration.declaredMethodArguments) {
                argParts.add(declaredMethodArgument.VariableType + " " + declaredMethodArgument.VariableName);
            }
            returnString += "(" + String.join(", ", argParts) + ")";
        } else {
            returnString += "()";
        }
        boolean isAbstractMethod;
        isAbstractMethod = methodDeclaration.methodType == MethodType.AbstractMethod;
        isAbstractMethod = isAbstractMethod || methodDeclaration.methodType == MethodType.InterfaceMethod;
        if(!isAbstractMethod) {
            returnString += " {";
        }
        else {
            returnString += ";";
        }
        return returnString;
    }

    @Override
    protected String CompileFieldDeclaration(FieldDeclaration fieldDeclaration, ParentChildRelation parentChildRelation) throws Exception {
        String returnString = "";
        if (fieldDeclaration.accessModifier != null) {
            returnString += fieldDeclaration.accessModifier.name().toLowerCase() + " ";
        }
        if (fieldDeclaration.isStatic) {
            returnString += "static ";
        }
        returnString += fieldDeclaration.VariableType + " " + fieldDeclaration.VariableName;
        if(fieldDeclaration.assignment != null) {
            returnString += CompileStatement(fieldDeclaration.assignment, ParentChildRelation.Child);
        }
        else {
            returnString += ";";
        }
        return returnString;
    }

    @Override
    protected String CompileLocalVariableDeclaration(LocalVariableDeclaration localVariableDeclaration, ParentChildRelation parentChildRelation) throws Exception {
        String returnString = localVariableDeclaration.VariableType;
        returnString += " ";
        returnString += localVariableDeclaration.VariableName;
        returnString += " = " + CompileStatement(localVariableDeclaration.assignment, ParentChildRelation.Child);
        if(parentChildRelation == ParentChildRelation.Parent) {
            returnString += ";";
        }
        return returnString;
    }

    @Override
    protected String CompileStringLiteral(StringLiteralInfo stringLiteralInfo, ParentChildRelation parentChildRelation) {
        return String.format("\"%s\"", stringLiteralInfo.value);
    }

    @Override
    protected String CompileMethodCall(MethodCallInfo methodCallInfo, ParentChildRelation parentChildRelation) throws Exception {
        String returnString = "";
        if (methodCallInfo.hasKeywordThis) {
            returnString += "this.";
        }
        returnString += methodCallInfo.name;
        if (methodCallInfo.passedArguments.size() > 0) {
            ArrayList<String> argParts = new ArrayList<>();
            for (StatementInfo passedArgument : methodCallInfo.passedArguments) {
                argParts.add(CompileStatement(passedArgument, ParentChildRelation.Child));
            }
            returnString += "(" + String.join(", ", argParts) + ")";
        } else {
            returnString += "()";
        }
        if(parentChildRelation == ParentChildRelation.Parent) {
            returnString += ";";
        }
        return returnString;
    }

    @Override
    protected String CompileMathOperation(MathOperator mathOperation, ParentChildRelation parentChildRelation) throws Exception {
        String returnString = "";
        if (mathOperation.hasKeywordThis) {
            returnString += "this.";
        }
        returnString += mathOperation.assignmentTarget;
        returnString += " ";
        returnString += mathOperation.getOperatorSymbol();
        returnString += " ";
        returnString += CompileStatement(mathOperation.operatedValue, ParentChildRelation.Child);
        if(parentChildRelation == ParentChildRelation.Parent) {
            returnString += ";";
        }
        return returnString;
    }

    @Override
    protected String CompileStringConcatenation(StringConcatenation stringConcatenation, ParentChildRelation parentChildRelation) throws Exception {
        String returnString = "\"" + stringConcatenation.stringValue + "\"";
        returnString += " + ";
        returnString += CompileStatement(stringConcatenation.concatenatedValue, ParentChildRelation.Child);
        return returnString;
    }

    @Override
    protected String CompileReturnStatement(StatementInfo returnStatementInfo, ParentChildRelation parentChildRelation) throws Exception {
        return "return " + CompileStatement(returnStatementInfo, ParentChildRelation.Child);
    }

    @Override
    protected String CompileConditionalOperator(ConditionOperator conditionOperator, ParentChildRelation parentChildRelation) throws Exception {
        String returnString = conditionOperator.getOperator();
        returnString += "(" + CompileStatement(conditionOperator.condition, ParentChildRelation.Child) + ") {";
        return returnString;
    }

    @Override
    protected String CompileInstanceLiteral(InstanceLiteralInfo instanceLiteralInfo, ParentChildRelation parentChildRelation) {
        if (instanceLiteralInfo.hasKeywordThis) {
            return String.format("this.%s", instanceLiteralInfo.value);
        } else {
            return instanceLiteralInfo.value;
        }
    }

    @Override
    protected String CompileEquality(EqualityOperator equalityOperator, ParentChildRelation parentChildRelation) throws Exception {
        String returnString = CompileStatement(equalityOperator.operand1, ParentChildRelation.Child);
        returnString += " ";
        returnString += equalityOperator.getOperator();
        returnString += " ";
        returnString += CompileStatement(equalityOperator.operand2, ParentChildRelation.Child);
        return returnString;
    }

}
