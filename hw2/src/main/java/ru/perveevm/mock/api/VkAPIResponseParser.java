package ru.perveevm.mock.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class VkAPIResponseParser {
    public List<Instant> parseResponse(final String response) {
        JsonElement json = JsonParser.parseString(response);
        JsonElement responseObject = json.getAsJsonObject().get("response").getAsJsonObject();
        JsonArray responseItems = responseObject.getAsJsonObject().get("items").getAsJsonArray();

        List<Instant> result = new ArrayList<>();
        for (JsonElement item : responseItems) {
            result.add(Instant.ofEpochSecond(item.getAsJsonObject().get("date").getAsInt()));
        }

        return result;
    }
}
