package io.github.teodordyakov;

import java.util.HashMap;
import java.util.Map;

public class JSONobject {
    Map<Object, Object> children = new HashMap<>();
    JSONobject parent;
    boolean isArray = false;
    int arrIdx = 0;

    public int getSize() {
        return children.size();
    }

    public String getStringFromArray(int index) {
        return (String) children.get(index);
    }

    public JSONobject getObjectFromArray(int index) {
        return (JSONobject) children.get(index);
    }

    public Double getDoubleFromArray(int index) {
        return (Double) children.get(index);
    }

    public Integer getIntFromArray(int index) {
        return (Integer) children.get(index);
    }

    public Boolean getBooleanFromArray(int index) {
        return (Boolean) children.get(index);
    }

    public String getString(String key) {
        return (String) children.get(key);
    }

    public Integer getInt(String key) {
        return (Integer) children.get(key);
    }

    public Double getDouble(String key) {
        return (Double) children.get(key);
    }

    public Boolean getBoolean(String key) {
        return (Boolean) children.get(key);
    }

    public JSONobject getObject(String key) {
        return (JSONobject) children.get(key);
    }

    @Override
    public String toString() {
        return "JSONobject{" +
                "children=" + children +
                ", isArray=" + isArray +
                ", arrIdx=" + arrIdx +
                '}';
    }

    public static void print(String offset, JSONobject root) {

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
            if (++idx < size) {
                System.out.println(",");
            }
        }
        System.out.print("\n" + offset + (root.isArray ? "]" : "}"));
    }

    public void print() {
        print("", this);
    }
}
