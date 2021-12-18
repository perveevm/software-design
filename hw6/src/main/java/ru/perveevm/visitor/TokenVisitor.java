package ru.perveevm.visitor;

import ru.perveevm.visitor.token.BraceToken;
import ru.perveevm.visitor.token.NumberToken;
import ru.perveevm.visitor.token.OperationToken;

public interface TokenVisitor {
    void visit(final NumberToken token);

    void visit(final BraceToken token);

    void visit(final OperationToken token);
}
