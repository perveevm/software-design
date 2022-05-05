package ru.perveevm.actor.model;

import java.util.List;

public class SearchResponse {
    private final SearchEngine engine;
    private final List<String> result;

    public SearchResponse(final SearchEngine engine, final List<String> result) {
        this.engine = engine;
        this.result = result;
    }

    public SearchEngine getEngine() {
        return engine;
    }

    public List<String> getResult() {
        return result;
    }
}
