package ru.perveevm.visitor.tokenizer.state;

public class States {
    private static final State startState = new StartState();
    private static final State endState = new EndState();
    private static final State errorState = new ErrorState();
    private static final State numberState = new NumberState();

    public static State startState() {
        return startState;
    }

    public static State endState() {
        return endState;
    }

    public static State errorState() {
        return errorState;
    }

    public static State numberState() {
        return numberState;
    }
}
