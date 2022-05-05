package ru.perveevm.actor.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedAbstractActor;
import ru.perveevm.actor.model.SearchEngine;
import ru.perveevm.actor.model.SearchExecutor;
import ru.perveevm.actor.model.SearchRequest;
import ru.perveevm.actor.model.SearchResponse;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicActor extends UntypedAbstractActor {
    private final SearchExecutor executor;
    private final Map<SearchEngine, List<String>> result = new HashMap<>();
    private ActorRef parent;

    public BasicActor(final SearchExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void onReceive(final Object message) {
        if (message instanceof ReceiveTimeout) {
            stop();
        } else if (message instanceof SearchResponse response) {
            result.put(response.getEngine(), response.getResult());
            if (result.size() == SearchEngine.values().length) {
                stop();
            }
        } else if (message instanceof String requestString) {
            parent = getSender();
            for (SearchEngine engine : SearchEngine.values()) {
                ActorRef childActor = getContext().actorOf(Props.create(EngineActor.class, executor));
                childActor.tell(new SearchRequest(engine, requestString), self());
            }
            getContext().setReceiveTimeout(Duration.of(1, ChronoUnit.SECONDS));
        } else {
            throw new IllegalArgumentException("Unexpected message type");
        }
    }

    private void stop() {
        parent.tell(result, self());
        getContext().stop(self());
    }
}
