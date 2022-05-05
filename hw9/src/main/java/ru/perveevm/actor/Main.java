package ru.perveevm.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.PatternsCS;
import ru.perveevm.actor.actors.BasicActor;
import ru.perveevm.actor.model.UrlSearchExecutor;

public class Main {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("SearchEngine");
        ActorRef basicActor = actorSystem.actorOf(Props.create(BasicActor.class,
                new UrlSearchExecutor("https://my-search-api.com")));
        String request = "qwerty123";

        Object response = PatternsCS.ask(basicActor, request, 5000).toCompletableFuture().join();
        System.out.println(response);

        actorSystem.terminate();
    }
}
