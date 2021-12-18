package ru.perveevm.visitor;

import ru.perveevm.visitor.token.BraceToken;
import ru.perveevm.visitor.token.NumberToken;
import ru.perveevm.visitor.token.OperationToken;
import ru.perveevm.visitor.token.Token;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class PrintVisitor implements TokenVisitor {
    private PrintStream out;

    public void print(final List<Token> tokens, final PrintStream out) {
        this.out = out;

        for (Token token : tokens) {
            token.accept(this);
        }
        out.println();
    }

    @Override
    public void visit(NumberToken token) {
        out.print(token + " ");
    }

    @Override
    public void visit(BraceToken token) {
        out.print(token + " ");
    }

    @Override
    public void visit(OperationToken token) {
        out.print(token + " ");
    }
}
