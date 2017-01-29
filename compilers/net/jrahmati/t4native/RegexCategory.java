package net.jrahmati.t4native;

/**
 *
 * @author jafar
 */
public enum RegexCategory {
    CSV_SYMBOL,
    IMPORT_FILE,
    INTERFACE_DECLARATION,
    CLASS_DECLARATION,
    FIELD_DECLARATION,
    VARIABLE_DECLARATION,
    METHOD_DECLARATION,
    /**
     * this.assigmentTarget = statement;
     */
    INSTANCE_TO_STATEMENT_OPERATOR,
    /**
     * "stringLiteral"
     */
    STRING_LITERAL,
    INSTANCE_LITERAL,
    METHOD_CALL,
    RETURN_STATEMENT,
    STRING_CONCATENATION_OPERATOR,
    IF_WHILE_OPERATOR,
    LOGICAL_OPERATOR,
    EQAULITY_OPERATOR
}
