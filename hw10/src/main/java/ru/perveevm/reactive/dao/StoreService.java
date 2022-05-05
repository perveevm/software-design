package ru.perveevm.reactive.dao;

import ru.perveevm.reactive.model.Currency;
import ru.perveevm.reactive.model.Product;
import ru.perveevm.reactive.model.User;
import rx.Observable;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 */
public class StoreService {
    private final StoreDao storeDao;

    public StoreService(final StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    public Observable<String> createUser(final long userId, final String name, final Currency currency) {
        return storeDao.createUser(new User(userId, name, currency));
    }

    public Observable<String> createProduct(final long productId, final String name, final double price) {
        return storeDao.createProduct(new Product(productId, name, price));
    }

    public Observable<String> getProductsForUser(final long userId) {
        Observable<Currency> userCurrency = storeDao.findUserById(userId).map(User::getCurrency);
        Observable<Product> products = storeDao.findProducts();
        return Observable.combineLatest(userCurrency, products, (currency, product) -> product.toString(currency));
    }
}
