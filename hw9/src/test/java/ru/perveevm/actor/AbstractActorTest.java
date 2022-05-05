package ru.perveevm.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.xebialabs.restito.server.StubServer;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class AbstractActorTest {
    protected ActorSystem actorSystem;
    protected ActorRef basicActor;

    protected static final int PORT = 8080;

    protected static final String[] googleArray = new String[]{"google1", "google2", "google3", "google4", "google5"};
    protected static final String[] yandexArray = new String[]{"yandex1", "yandex2", "yandex3", "yandex4", "yandex5"};
    protected static final String[] bingArray = new String[]{"bing1", "bing2", "bing3", "bing4", "bing5"};

    protected void enableStubServer(final Consumer<StubServer> callback) {
        StubServer stubServer = new StubServer(PORT).run();
        callback.accept(stubServer);
        if (stubServer != null) {
            stubServer.stop();
        }
    }

    protected String toJson(String[] strings) {
        return Arrays.stream(strings)
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(",", "[", "]"));
    }
}
