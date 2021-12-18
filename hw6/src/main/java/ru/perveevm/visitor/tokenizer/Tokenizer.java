package ru.perveevm.visitor.tokenizer;

import ru.perveevm.visitor.token.Token;
import ru.perveevm.visitor.tokenizer.state.StartState;
import ru.perveevm.visitor.tokenizer.state.State;
import ru.perveevm.visitor.tokenizer.state.States;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private static final char EOF = (char) -1;
    private final PushbackReader reader;
    private State state = States.startState();

    public Tokenizer(final InputStream in) {
        reader = new PushbackReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public List<Token> tokenize() throws IOException {
        List<Token> tokens = new ArrayList<>();

        skipWhitespaces();
        state = state.nextState(this);
        while (!state.equals(States.endState()) && !state.equals(States.errorState())) {
            tokens.add(state.nextToken(this));
            state = state.nextState(this);
        }

        if (state.equals(States.errorState())) {
            throw new IllegalStateException("Input cannot be tokenized: ERROR state reached");
        }

        return tokens;
    }

    public void skipWhitespaces() throws IOException {
        while (!isEnd() && isWhitespace()) {
            nextChar();
        }
    }

    public char nextChar() throws IOException {
        return readChar(false);
    }

    public char peekChar() throws IOException {
        return readChar(true);
    }

    public boolean isDigit() throws IOException {
        return Character.isDigit(peekChar());
    }

    public boolean isBrace() throws IOException {
        char c = peekChar();
        return c == '(' || c == ')';
    }

    public boolean isOperation() throws IOException {
        char c = peekChar();
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public boolean isWhitespace() throws IOException {
        return Character.isWhitespace(peekChar());
    }

    public boolean isEnd() throws IOException {
        return peekChar() == EOF;
    }

    private char readChar(final boolean needUnread) throws IOException {
        int next = reader.read();
        char nextChar;
        if (next < 0) {
            nextChar = EOF;
        } else {
            nextChar = (char) next;
        }

        if (needUnread) {
            reader.unread(next);
        }

        return nextChar;
    }
}
