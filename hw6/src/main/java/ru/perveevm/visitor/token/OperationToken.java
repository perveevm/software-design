package ru.perveevm.visitor.token;

import ru.perveevm.visitor.TokenVisitor;

public abstract class OperationToken implements Token {
    public abstract int priority();

    public abstract int apply(final int left, final int right);

    @Override
    public void accept(final TokenVisitor visitor) {
        visitor.visit(this);
    }
}
