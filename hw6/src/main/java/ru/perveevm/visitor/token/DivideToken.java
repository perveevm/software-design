package ru.perveevm.visitor.token;

public class DivideToken extends OperationToken {
    @Override
    public int priority() {
        return 1;
    }

    @Override
    public int apply(int left, int right) {
        return left / right;
    }

    @Override
    public String toString() {
        return "DIVIDE";
    }
}
