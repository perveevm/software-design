package ru.perveevm.reactive.dao;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import org.bson.Document;
import ru.perveevm.reactive.model.Product;
import ru.perveevm.reactive.model.User;
import rx.Observable;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 */
public class StoreDao {
    private final MongoCollection<Document> users;
    private final MongoCollection<Document> products;

    public StoreDao(final MongoCollection<Document> users, final MongoCollection<Document> products) {
        this.users = users;
        this.products = products;
    }

    public Observable<User> findUserById(final long id) {
        return users.find(Filters.eq("id", id)).first().map(User::new);
    }

    public Observable<Product> findProductById(final long id) {
        return products.find(Filters.eq("id", id)).first().map(Product::new);
    }

    public Observable<Product> findProducts() {
        return products.find().toObservable().map(Product::new);
    }

    public Observable<String> createUser(final User user) {
        return users.insertOne(user.toDocument())
                .map(result -> result != null ? "OK!" : "Error happened while creating user");
    }

    public Observable<String> createProduct(final Product product) {
        return products.insertOne(product.toDocument())
                .map(result -> result != null ? "OK!" : "Error happened while creating product");
    }
}
