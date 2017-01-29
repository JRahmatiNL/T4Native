package net.jrahmati.t4native.codeinfo;

/**
 *
 * @author jafar
 */
public class ConditionOperator {
    
    private String operator = "";
    public String getOperator() {
        return operator;
    }
    
    public StatementInfo condition;
    
    public ConditionOperator(String operator) {
        this.operator = operator;
    }
}
