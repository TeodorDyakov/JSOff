package io.github.teodordyakov;

public class JSOff {

    //VAL and KEY represent literal values and keys
    enum STATE {
        VALUE,
        KEY,
        AFTER_VAL,
        AFTER_KEY,
        BEFORE_VAL,
        BEFORE_KEY,
    }

    static JSONobject parse(String s) {
        JSONobject curr = new JSONobject();
        STATE state = STATE.BEFORE_VAL;

        String currKey = "";
        String currVal = "";

        boolean isString = false;

        for (char c : s.toCharArray()) {
            if (state == STATE.BEFORE_KEY) {
                if (c == '"') {
                    state = STATE.KEY;
                }
            } else if (state == STATE.KEY) {
                if (c == '"') {
                    state = STATE.AFTER_KEY;
                } else {
                    currKey += c;
                }
            } else if (state == STATE.AFTER_KEY) {
                if (c == ':') {
                    state = STATE.BEFORE_VAL;
                }
            } else if (state == STATE.BEFORE_VAL) {
                if (Character.isWhitespace(c)) {
                    continue;
                } else if (c == '{' || c == '[') {
                    JSONobject child = new JSONobject();
                    child.parent = curr;

                    if (!curr.isArray) {
                        curr.children.put(currKey, child);
                    } else {
                        curr.children.put(curr.arrIdx++, child);
                    }

                    curr = child;
                    currKey = "";

                    if (c == '[') {
                        curr.isArray = true;
                        state = STATE.BEFORE_VAL;
                    } else {
                        state = STATE.BEFORE_KEY;
                    }
                } else if (c == '"') {
                    state = STATE.VALUE;
                    isString = true;
                } else if (c == '-' || c == 't' || c == 'f' || c == 'n' || (c <= '9' && c >= '0')) {
                    currVal += c;
                    state = STATE.VALUE;
                }
            } else if (state == STATE.VALUE) {
                if (c == '"' || c == '}' || c == ']' || Character.isWhitespace(c) || c == ',') {
                    Object val = null;
                    if (isString) {
                        val = currVal;
                    } else if (currVal.equals("false") || currVal.equals("true")) {
                        val = Boolean.parseBoolean(currVal);
                    } else if (currVal.indexOf('.') != -1) {
                        val = Double.parseDouble(currVal);
                    } else if (currVal.charAt(0) != 'n') {
                        val = Integer.parseInt(currVal);
                    }
                    if (!curr.isArray) {
                        curr.children.put(currKey, val);
                    } else {
                        curr.children.put(curr.arrIdx++, val);
                    }

                    isString = false;
                    currKey = "";
                    currVal = "";

                    if (c == '"' || Character.isWhitespace(c) || c == ',') {
                        state = STATE.AFTER_VAL;
                        if (c == ',') {
                            if (curr.isArray) {
                                state = STATE.BEFORE_VAL;
                            } else {
                                state = STATE.BEFORE_KEY;
                            }
                        }
                    } else {
                        curr = curr.parent;
                        state = STATE.BEFORE_VAL;
                    }
                } else {
                    currVal += c;
                }
            } else if (state == STATE.AFTER_VAL) {
                if (c == '}' || c == ']') {
                    curr = curr.parent;
                } else if (c == ',') {
                    if (!curr.isArray) {
                        state = STATE.BEFORE_KEY;
                    } else {
                        state = STATE.BEFORE_VAL;
                    }
                }
            }
        }
        return (JSONobject) curr.children.get("");
    }

}
