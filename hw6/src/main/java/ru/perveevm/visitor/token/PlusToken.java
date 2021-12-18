package ru.perveevm.visitor.token;

public class PlusToken extends OperationToken {
    @Override
    public int priority() {
        return 0;
    }

    @Override
    public int apply(int left, int right) {
        return left + right;
    }

    @Override
    public String toString() {
        return "PLUS";
    }
}
