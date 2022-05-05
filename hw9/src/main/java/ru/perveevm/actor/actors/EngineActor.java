package ru.perveevm.actor.actors;

import akka.actor.UntypedAbstractActor;
import ru.perveevm.actor.model.SearchExecutor;
import ru.perveevm.actor.model.SearchRequest;

public class EngineActor extends UntypedAbstractActor {
    private final SearchExecutor executor;

    public EngineActor(final SearchExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void onReceive(final Object message) {
        if (message instanceof SearchRequest request) {
            getSender().tell(executor.execute(request), self());
            getContext().stop(self());
        } else {
            throw new IllegalArgumentException("Unexpected message type");
        }
    }
}
