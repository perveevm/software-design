package ru.perveevm.visitor.token;

import ru.perveevm.visitor.TokenVisitor;

public class NumberToken implements Token {
    private final int value;

    public NumberToken(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void accept(final TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("NUMBER(%d)", value);
    }
}
