package net.jrahmati.t4native.codeinfo;

/**
 *
 * @author jafar
 */
public class MathOperator {
    
    private String operatorSymbol = "";
    public String getOperatorSymbol() {
        return operatorSymbol;
    }
    
    public MathOperator(String operationSymbol) {
        this.operatorSymbol = operationSymbol;
    }
    
    public String assignmentTarget;
    public StatementInfo operatedValue;
    public boolean hasKeywordThis;
}
