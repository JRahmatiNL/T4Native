package net.jrahmati.t4native.codeinfo;

/**
 *
 * @author jafar
 */
public class EqualityOperator {
    
    private String operator = "";
    public String getOperator() {
        return operator;
    }

    public EqualityOperator(String operator) {
        this.operator = operator;
    }
    
    public StatementInfo operand1;
    public StatementInfo operand2;
}
