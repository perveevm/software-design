package ru.perveev.mock.api;

import org.glassfish.grizzly.http.Method;
import org.junit.Before;
import org.junit.Test;
import ru.perveevm.mock.api.VkAPIClient;

import java.time.Instant;
import java.util.List;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.builder.verify.VerifyHttp.verifyHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.*;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.perveev.mock.TestUtils.withStubServer;

public class VkAPIClientWithStubServerTest {
    private static final int PORT = 32453;
    private static final String HOST = "http://localhost:" + PORT;
    private static final String ACCESS_TOKEN = "TOKEN";
    private VkAPIClient client;

    private static final String HANDMADE_RESPONSE = """
            {
            "response": {
            "items": [
            {"date": 123},
            {"date": 456},
            {"date": 789}
            ]
            }
            }
            """;

    @Before
    public void initTest() {
        client = new VkAPIClient(HOST, ACCESS_TOKEN);
    }

    @Test
    public void findPostsTest() {
        withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/method"))
                    .then(stringContent(HANDMADE_RESPONSE));

            assertThat(client.findPosts("#образование", Instant.ofEpochSecond(0),
                    Instant.ofEpochSecond(24 * 60 * 60 - 1))).isEqualTo(List.of(
                            Instant.ofEpochSecond(123L),
                            Instant.ofEpochSecond(456L),
                            Instant.ofEpochSecond(789L)));

            verifyHttp(s).never(
                    method(Method.GET),
                    startsWithUri("/method/newsfeed.search"),
                    parameter("q", "#образование"),
                    parameter("access_token", ACCESS_TOKEN),
                    parameter("start_time", String.valueOf(1)),
                    parameter("end_time", String.valueOf(24 * 60 * 60 - 1))
            );
        });
    }
}
