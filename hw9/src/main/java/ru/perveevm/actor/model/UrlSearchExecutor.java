package ru.perveevm.actor.model;

import ru.perveevm.actor.http.RawUrlReader;
import ru.perveevm.actor.http.UrlReader;

public class UrlSearchExecutor implements SearchExecutor {
    private final UrlReader reader = new RawUrlReader();
    private final ResponseParser parser = new ResponseParser();
    private final String endpoint;

    public UrlSearchExecutor(final String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public SearchResponse execute(final SearchRequest request) {
        String engine = request.getEngine().toString().toLowerCase();
        String response = reader.readAsText(String.format("%s/%s/%s", endpoint, engine, request.getRequest()));
        return new SearchResponse(request.getEngine(), parser.parse(response));
    }
}
