package ru.perveevm.mock.api;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class VkAPIService {
    private final VkAPIClient client;

    public VkAPIService(final VkAPIClient client) {
        this.client = client;
    }

    public int[] countPosts(final String hashtag, final int hours, final Instant endTime) {
        if (hours < 1 || hours > 24) {
            throw new IllegalArgumentException("Hours number should be integer number in [1, 24]");
        }

        Instant startTime = endTime.minus(hours, ChronoUnit.HOURS);
        List<Instant> response = client.findPosts(hashtag, startTime, endTime);

        int[] result = new int[hours];
        for (Instant postTime : response) {
            result[hours - (int) postTime.until(endTime, ChronoUnit.HOURS) - 1]++;
        }

        return result;
    }

    public int[] countPosts(final String hashtag, final int hours) {
        return countPosts(hashtag, hours, Instant.now());
    }
}
