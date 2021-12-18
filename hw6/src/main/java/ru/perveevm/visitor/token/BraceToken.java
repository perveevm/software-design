package ru.perveevm.visitor.token;

import ru.perveevm.visitor.TokenVisitor;

public class BraceToken implements Token {
    @Override
    public void accept(final TokenVisitor visitor) {
        visitor.visit(this);
    }
}
