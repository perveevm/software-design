package ru.perveevm.actor.model;

public class SearchRequest {
    private final SearchEngine engine;
    private final String request;

    public SearchRequest(final SearchEngine engine, final String request) {
        this.engine = engine;
        this.request = request;
    }

    public SearchEngine getEngine() {
        return engine;
    }

    public String getRequest() {
        return request;
    }
}
