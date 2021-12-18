package ru.perveevm.visitor.token;

import ru.perveevm.visitor.TokenVisitor;

public interface Token {
    void accept(final TokenVisitor visitor);
}
