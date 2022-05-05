package ru.perveevm.actor.model;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseParser {
    private final Gson gson = new Gson();

    public List<String> parse(final String jsonResponse) {
        String[] array = gson.fromJson(jsonResponse, String[].class);
        return Arrays.stream(array).collect(Collectors.toList());
    }
}
