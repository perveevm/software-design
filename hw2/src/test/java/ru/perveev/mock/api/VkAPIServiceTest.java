package ru.perveev.mock.api;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.perveevm.mock.api.VkAPIClient;
import ru.perveevm.mock.api.VkAPIService;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

public class VkAPIServiceTest {
    private VkAPIService service;
    @Mock
    private VkAPIClient client;
    private AutoCloseable closeable;

    @BeforeEach
    public void initTest() {
        closeable = MockitoAnnotations.openMocks(this);
        service = new VkAPIService(client);
    }

    @AfterEach
    public void finishTest() throws Exception {
        closeable.close();
    }

    private void invalidHoursTest(final int hours) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> service.countPosts("#образование", hours));
    }

    @Test
    public void invalidHoursTest() {
        invalidHoursTest(-1);
        invalidHoursTest(25);
        invalidHoursTest(0);
    }

    @Test
    public void countPostsTest() {
        when(client.findPosts("#образование", Instant.ofEpochSecond(-1), Instant.ofEpochSecond(24 * 60 * 60 - 1)))
                .thenReturn(List.of(
                        Instant.ofEpochSecond(0L),
                        Instant.ofEpochSecond(12 * 60 * 60 - 1),
                        Instant.ofEpochSecond(12 * 60 * 60 - 1),
                        Instant.ofEpochSecond(13 * 60 * 60 - 1),
                        Instant.ofEpochSecond(23 * 60 * 60 - 1)
                ));

        int[] expectedAnswer = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
        assertThat(service.countPosts("#образование", 24, Instant.ofEpochSecond(24 * 60 * 60 - 1)))
                .isEqualTo(expectedAnswer);
    }
}
