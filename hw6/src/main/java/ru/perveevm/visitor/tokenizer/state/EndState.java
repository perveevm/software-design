package ru.perveevm.visitor.tokenizer.state;

import ru.perveevm.visitor.token.Token;
import ru.perveevm.visitor.tokenizer.Tokenizer;

public class EndState extends State {
    @Override
    public Token nextToken(final Tokenizer tokenizer) {
        throw new IllegalStateException("Trying to get next token from the end state");
    }

    @Override
    public State nextState(final Tokenizer tokenizer) {
        return States.endState();
    }
}
