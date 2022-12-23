package io.github.teodordyakov;

public class StringStateMachine {

    enum State {
        BEGIN,
        ESCAPE_CHARACTER,
        U,
        HEX_DIGIT,
        END,
        BROKEN
    }

    State state = State.BEGIN;

    public boolean needEscape(char c) {
        return "bfnrt\"\\/".indexOf(c) != -1;
    }

    public boolean isHexDigit(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f');
    }

    public void feedChar(char c) {
        int hexCount = 0;
        if (state == State.BEGIN) {
            if (c == '\\') {
                state = State.ESCAPE_CHARACTER;
            } else if (c < 32) {
                state = State.BROKEN;
            } else if (c == '"') {
                state = State.END;
            } else {
                //normal character
                state = State.BEGIN;
            }
        } else if (state == State.ESCAPE_CHARACTER) {
            if (c == 'u') {
                state = State.U;
            } else if (needEscape(c)) {
                state = State.BEGIN;
            } else {
                state = State.BROKEN;
            }
        } else if (state == State.U) {
            if (isHexDigit(c)) {
                state = State.HEX_DIGIT;
                hexCount++;
            } else {
                state = State.BROKEN;
            }
        } else if (state == State.HEX_DIGIT) {
            if (isHexDigit(c)) {
                hexCount++;
            } else {
                state = State.BROKEN;
            }
            if (hexCount == 4) {
                state = State.BEGIN;
            }
        }
    }

    public void reset(){
        state = State.BEGIN;
    }

    public State getState() {
        return state;
    }

    public static void main(String[] args) {
        StringStateMachine stateMachine = new StringStateMachine();
        String s = "ab\\\"bc\\b\\f\\\\\"";
        s = "55";
        for (char c : s.toCharArray()) {
            stateMachine.feedChar(c);
            System.out.println(stateMachine.getState());
        }
    }

}
