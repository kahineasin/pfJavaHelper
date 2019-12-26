package pf.java.pfHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PFDataColumn {
    String key;
    Object value;
    private Class _type;
 
    public PFDataColumn(String k, Object v) {
        key = k;
        value = v;
    }
    public PFDataColumn(String k, Class type) {
        key = k;
        _type = type;
    }
 
    public String getKey() {
        return key;
    }
 
    public Object getValue() {
        return value;
    }
 
    public void setKey(String key) {
        this.key = key;
    }
 
    public void setValue(Object value) {
        this.value = value;
    }
}
