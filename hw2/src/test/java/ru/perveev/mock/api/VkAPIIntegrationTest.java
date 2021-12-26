package ru.perveev.mock.api;

import org.junit.jupiter.api.Test;
import ru.perveevm.mock.api.VkAPIClient;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VkAPIIntegrationTest {
    private final VkAPIClient client = VkAPIClient.fromEnvironment("ACCESS_TOKEN");

    @Test
    public void findPostsTest() {
        Instant endTime = Instant.now();
        Instant startTime = endTime.minus(24, ChronoUnit.HOURS);
        List<Instant> response = client.findPosts("#образование", startTime, endTime);
        assertThat(response).allMatch(timing -> timing.isAfter(startTime)
                && (timing.isBefore(endTime) || timing.equals(endTime)));
    }
}
