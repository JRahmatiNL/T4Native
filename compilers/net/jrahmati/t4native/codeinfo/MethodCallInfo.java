package net.jrahmati.t4native.codeinfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jafar
 */
public class MethodCallInfo {
    public String name;
    public boolean hasKeywordThis;
    public boolean isConstructorCall;
    public List<StatementInfo> passedArguments = new ArrayList<>();
}
