package ru.perveevm.bridge.graph;

public class Edge {
    private final int from;
    private final int to;

    public Edge(final int from, final int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
