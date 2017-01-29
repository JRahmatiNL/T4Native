package net.jrahmati.t4native.codeinfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jafar
 */
public class MethodDeclaration {

    public boolean isStatic;
    public MethodType methodType;
    public AccessModifier accessModifier;
    public boolean isOverride;
    public String methodName;
    public String returnType;
    public boolean isConstructorMethod;
    public List<VariableDeclaration> declaredMethodArguments = new ArrayList<>();
    /**
     * refers to the file name (without extension) where the statement occurs
     */
    public String attachedFile;
}
