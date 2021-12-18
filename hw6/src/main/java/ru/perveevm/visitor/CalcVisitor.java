package ru.perveevm.visitor;

import ru.perveevm.visitor.token.BraceToken;
import ru.perveevm.visitor.token.NumberToken;
import ru.perveevm.visitor.token.OperationToken;
import ru.perveevm.visitor.token.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class CalcVisitor implements TokenVisitor {
    private final Deque<Integer> stack = new ArrayDeque<>();

    public int calculate(final List<Token> tokens) {
        stack.clear();

        for (Token token : tokens) {
            token.accept(this);
        }

        if (stack.size() != 1) {
            throw new IllegalStateException("Input is an incorrect Polish-notation expression: invalid number of" +
                    "numbers and operations");
        }
        return stack.removeLast();
    }

    @Override
    public void visit(final NumberToken token) {
        stack.addLast(token.getValue());
    }

    @Override
    public void visit(final BraceToken token) {
        throw new IllegalStateException("Input is an incorrect Polish-notation expression: BRACE token was found");
    }

    @Override
    public void visit(final OperationToken token) {
        if (stack.size() < 2) {
            throw new IllegalStateException("Input is an incorrect Polish-notation expression: invalid number of" +
                    "numbers and operations");
        }

        int right = stack.removeLast();
        int left = stack.removeLast();
        stack.addLast(token.apply(left, right));
    }
}
