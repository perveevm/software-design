package ru.perveevm.visitor;

import ru.perveevm.visitor.token.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ParseVisitor implements TokenVisitor {
    private final Deque<Token> stack = new ArrayDeque<>();
    private final List<Token> result = new ArrayList<>();

    public List<Token> convert(final List<Token> tokens) {
        stack.clear();
        result.clear();

        for (Token token : tokens) {
            token.accept(this);
        }

        while (!stack.isEmpty()) {
            if (!(stack.peekLast() instanceof OperationToken)) {
                throw new IllegalStateException("Input expression is incorrect: non-zero brace balance");
            }
            result.add(stack.removeLast());
        }

        return List.copyOf(result);
    }

    @Override
    public void visit(final NumberToken token) {
        result.add(token);
    }

    @Override
    public void visit(final BraceToken token) {
        if (token instanceof LeftBraceToken) {
            stack.addLast(token);
            return;
        }

        while (!stack.isEmpty() && (!(stack.peekLast() instanceof LeftBraceToken))) {
            result.add(stack.removeLast());
        }
        if (stack.isEmpty() || !(stack.peekLast() instanceof LeftBraceToken)) {
            throw new IllegalStateException("Input expression is incorrect: negative brace balance");
        }
        stack.removeLast();
    }

    @Override
    public void visit(final OperationToken token) {
        while (!stack.isEmpty()
                && (stack.peekLast() instanceof OperationToken)
                && ((OperationToken) stack.peekLast()).priority() >= token.priority()) {
            result.add(stack.removeLast());
        }
        stack.addLast(token);
    }
}
