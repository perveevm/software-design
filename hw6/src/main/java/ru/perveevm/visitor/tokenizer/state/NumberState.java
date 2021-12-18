package ru.perveevm.visitor.tokenizer.state;

import ru.perveevm.visitor.token.NumberToken;
import ru.perveevm.visitor.token.Token;
import ru.perveevm.visitor.tokenizer.Tokenizer;

import java.io.IOException;

public class NumberState extends State {
    @Override
    public Token nextToken(final Tokenizer tokenizer) throws IOException {
        StringBuilder s = new StringBuilder();
        while (tokenizer.isDigit()) {
            s.append(tokenizer.nextChar());
        }
        return new NumberToken(Integer.parseInt(s.toString()));
    }

    @Override
    public State nextState(final Tokenizer tokenizer) throws IOException {
        tokenizer.skipWhitespaces();
        if (tokenizer.isEnd()) {
            return States.endState();
        } else {
            return States.startState();
        }
    }
}
