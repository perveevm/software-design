package ru.perveevm.actor;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.PatternsCS;
import com.xebialabs.restito.builder.stub.StubHttp;
import com.xebialabs.restito.semantics.Action;
import com.xebialabs.restito.semantics.Condition;
import org.assertj.core.api.Assertions;
import org.glassfish.grizzly.http.Method;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.perveevm.actor.actors.BasicActor;
import ru.perveevm.actor.model.SearchEngine;
import ru.perveevm.actor.model.UrlSearchExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicActorTest extends AbstractActorTest {
    @BeforeEach
    public void prepare() {
        actorSystem = ActorSystem.create("SearchEngineTest");
        basicActor = actorSystem.actorOf(Props.create(BasicActor.class,
                new UrlSearchExecutor("http://localhost:" + PORT)));
    }

    @AfterEach
    public void terminate() {
        actorSystem.terminate();
    }

    @Test
    public void successfulTest() {
        enableStubServer(request -> {
            StubHttp.whenHttp(request).match(Condition.method(Method.GET), Condition.startsWithUri("/google"))
                    .then(Action.stringContent(toJson(googleArray)));
            StubHttp.whenHttp(request).match(Condition.method(Method.GET), Condition.startsWithUri("/yandex"))
                    .then(Action.stringContent(toJson(yandexArray)));
            StubHttp.whenHttp(request).match(Condition.method(Method.GET), Condition.startsWithUri("/bing"))
                    .then(Action.stringContent(toJson(bingArray)));

            @SuppressWarnings("unchecked")
            Map<SearchEngine, List<String>> response = (Map<SearchEngine, List<String>>) PatternsCS.ask(basicActor,
                    "qwerty123", 5000).toCompletableFuture().join();

            Assertions.assertThat(response).size().isEqualTo(3);
            Assertions.assertThat(response).containsEntry(SearchEngine.GOOGLE,
                    Arrays.stream(googleArray).collect(Collectors.toList()));
            Assertions.assertThat(response).containsEntry(SearchEngine.YANDEX,
                    Arrays.stream(yandexArray).collect(Collectors.toList()));
            Assertions.assertThat(response).containsEntry(SearchEngine.BING,
                    Arrays.stream(bingArray).collect(Collectors.toList()));
        });
    }

    @Test
    public void delayTest() {
        enableStubServer(request -> {
            StubHttp.whenHttp(request).match(Condition.method(Method.GET), Condition.startsWithUri("/google"))
                    .then(Action.delay(15000), Action.stringContent(toJson(googleArray)));
            StubHttp.whenHttp(request).match(Condition.method(Method.GET), Condition.startsWithUri("/yandex"))
                    .then(Action.delay(500), Action.stringContent(toJson(yandexArray)));
            StubHttp.whenHttp(request).match(Condition.method(Method.GET), Condition.startsWithUri("/bing"))
                    .then(Action.stringContent(toJson(bingArray)));

            @SuppressWarnings("unchecked")
            Map<SearchEngine, List<String>> response = (Map<SearchEngine, List<String>>) PatternsCS.ask(basicActor,
                    "qwerty123", 5000).toCompletableFuture().join();

            Assertions.assertThat(response).size().isEqualTo(2);
            Assertions.assertThat(response).containsEntry(SearchEngine.YANDEX,
                    Arrays.stream(yandexArray).collect(Collectors.toList()));
            Assertions.assertThat(response).containsEntry(SearchEngine.BING,
                    Arrays.stream(bingArray).collect(Collectors.toList()));
        });
    }
}
