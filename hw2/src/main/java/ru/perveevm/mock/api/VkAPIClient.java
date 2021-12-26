package ru.perveevm.mock.api;

import ru.perveevm.mock.http.UrlReader;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

public class VkAPIClient {
    private static final String REQUEST_TEMPLATE = "%s/method/newsfeed.search?q=%s&access_token=%s" +
            "&count=200&start_time=%d&end_time=%d&v=5.131";

    private final String host;
    private final String accessToken;

    private final UrlReader reader = new UrlReader();
    private final VkAPIResponseParser parser = new VkAPIResponseParser();

    public VkAPIClient(final String host, final String accessToken) {
        this.host = host;
        this.accessToken = accessToken;
    }

    public VkAPIClient(final String accessToken) {
        this("https://api.vk.com/", accessToken);
    }

    public static VkAPIClient fromEnvironment(final String variableName) {
        return new VkAPIClient(System.getenv(variableName));
    }

    public List<Instant> findPosts(final String hashtag, final Instant startTime, final Instant endTime) {
        return parser.parseResponse(reader.readAsText(createRequest(hashtag, startTime, endTime)));
    }

    private String createRequest(final String hashtag, final Instant startTime, final Instant endTime) {
        return String.format(REQUEST_TEMPLATE, host, URLEncoder.encode(hashtag, StandardCharsets.UTF_8), accessToken,
                startTime.getEpochSecond() + 1, endTime.getEpochSecond());
    }
}
