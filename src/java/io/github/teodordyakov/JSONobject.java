package io.github.teodordyakov;

import java.util.HashMap;
import java.util.Map;

public class JSONobject {
    Map<Object, Object> children = new HashMap<>();
    JSONobject parent;
    boolean isArray = false;
    int arrIdx = 0;

    public Object getValue(String key) {
        return children.get(key);
    }

    public Object getValue(Integer index){
        return children.get(index);
    }

    @Override
    public String toString() {
        return "JSONobject{" +
                "children=" + children +
                ", isArray=" + isArray +
                '}';
    }
}

