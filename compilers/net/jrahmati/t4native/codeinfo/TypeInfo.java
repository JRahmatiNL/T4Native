package net.jrahmati.t4native.codeinfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jafar
 * A type can be a class or an interface
 */
public abstract class TypeInfo {
    /**
     * Represents class or interface name
     */
    public String Name;
    public List<String> implementedInterfaces = new ArrayList<>();
}
