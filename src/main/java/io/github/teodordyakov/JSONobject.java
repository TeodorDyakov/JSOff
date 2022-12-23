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

    public Object getValue(Integer index) {
        return children.get(index);
    }

    @Override
    public String toString() {
        return "JSONobject{" +
                "children=" + children +
                ", isArray=" + isArray +
                ", arrIdx=" + arrIdx +
                '}';
    }

    public void print(String offset, JSONobject root) {

        System.out.print((root.isArray ? "[" : "{") + "\n");
        int size = root.children.entrySet().size();
        int idx = 0;
        for (Map.Entry<Object, Object> entry : root.children.entrySet()) {
            System.out.print(offset + "    ");
            if (!root.isArray) {
                System.out.print("\"" + entry.getKey() + "\"" + " : ");
            }
            Object child = entry.getValue();
            if (child instanceof JSONobject) {
                print(offset + "    ", (JSONobject) child);
            } else {
                if (child instanceof String) {
                    System.out.print('"' + child.toString() + '"');
                } else {
                    System.out.print(child);
                }
            }
            if(++idx < size) {
                System.out.println(",");
            }
        }
        System.out.print("\n" + offset + (root.isArray ? "]" : "}"));
    }

    public void print(){
        print("", this);
    }
}

