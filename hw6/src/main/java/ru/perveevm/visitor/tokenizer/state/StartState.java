package ru.perveevm.visitor.tokenizer.state;

import ru.perveevm.visitor.token.*;
import ru.perveevm.visitor.tokenizer.Tokenizer;

import java.io.IOException;

public class StartState extends State {
    @Override
    public Token nextToken(final Tokenizer tokenizer) throws IOException {
        return switch (tokenizer.nextChar()) {
            case '(' -> new LeftBraceToken();
            case ')' -> new RightBraceToken();
            case '+' -> new PlusToken();
            case '-' -> new MinusToken();
            case '*' -> new MultiplyToken();
            case '/' -> new DivideToken();
            default -> throw new IllegalStateException("Input cannot be tokenized: not [()+-*/] in START state");
        };
    }

    @Override
    public State nextState(final Tokenizer tokenizer) throws IOException {
        tokenizer.skipWhitespaces();
        if (tokenizer.isDigit()) {
            return States.numberState();
        } else if (tokenizer.isBrace() || tokenizer.isOperation()) {
            return States.startState();
        } else if (tokenizer.isEnd()) {
            return States.endState();
        } else {
            return States.errorState();
        }
    }
}
