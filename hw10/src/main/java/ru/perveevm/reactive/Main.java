package ru.perveevm.reactive;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import io.reactivex.netty.protocol.http.server.HttpServer;
import ru.perveevm.reactive.dao.StoreDao;
import ru.perveevm.reactive.dao.StoreService;
import ru.perveevm.reactive.http.StoreController;
import rx.Observable;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 */
public class Main {
    public static void main(String[] args) {
        MongoDatabase database = createMongoClient().getDatabase("reactive");
        StoreController controller = new StoreController(new StoreService(new StoreDao(
                database.getCollection("users"), database.getCollection("products"))));

        HttpServer.newServer(8080).start((input, output) -> {
            Observable<String> response = controller.handle(input);
            return output.writeString(response);
        }).awaitShutdown();
    }

    private static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }
}
