package ru.perveevm.visitor.tokenizer.state;

import ru.perveevm.visitor.token.Token;
import ru.perveevm.visitor.tokenizer.Tokenizer;

import java.io.IOException;

public abstract class State {
    public abstract Token nextToken(final Tokenizer tokenizer) throws IOException;

    public abstract State nextState(final Tokenizer tokenizer) throws IOException;
}
