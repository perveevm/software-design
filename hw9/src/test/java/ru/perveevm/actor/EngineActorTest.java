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
import ru.perveevm.actor.actors.EngineActor;
import ru.perveevm.actor.model.SearchEngine;
import ru.perveevm.actor.model.SearchRequest;
import ru.perveevm.actor.model.SearchResponse;
import ru.perveevm.actor.model.UrlSearchExecutor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EngineActorTest extends AbstractActorTest {
    @BeforeEach
    public void prepare() {
        actorSystem = ActorSystem.create("SearchEngineTest");
        basicActor = actorSystem.actorOf(Props.create(EngineActor.class,
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

            SearchResponse response = (SearchResponse) PatternsCS.ask(basicActor,
                    new SearchRequest(SearchEngine.GOOGLE, "qwerty123"), 5000).toCompletableFuture().join();

            Assertions.assertThat(response.getEngine()).isEqualTo(SearchEngine.GOOGLE);
            Assertions.assertThat(response.getResult()).isEqualTo(
                    Arrays.stream(googleArray).collect(Collectors.toList()));
        });
    }
}
