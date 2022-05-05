package ru.perveevm.reactive.http;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.perveevm.reactive.dao.StoreService;
import ru.perveevm.reactive.model.Currency;
import rx.Observable;

import java.util.List;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 */
public class StoreController {
    private final StoreService service;

    public StoreController(final StoreService service) {
        this.service = service;
    }

    public Observable<String> handle(final HttpServerRequest<ByteBuf> request) {
        switch (request.getDecodedPath()) {
            case "/create-user":
                return createUserHandler(request);
            case "/create-product":
                return createProductHandler(request);
            case "/products":
                return getProductsHandler(request);
            default:
                return Observable.just(request.getDecodedPath());
        }
    }

    private Observable<String> createUserHandler(final HttpServerRequest<ByteBuf> request) {
        long userId = Long.parseLong(parseQueryParameter(request, "userId"));
        String name = parseQueryParameter(request, "name");
        Currency currency = Currency.valueOf(parseQueryParameter(request, "currency"));
        return service.createUser(userId, name, currency);
    }

    private Observable<String> createProductHandler(final HttpServerRequest<ByteBuf> request) {
        long productId = Long.parseLong(parseQueryParameter(request, "productId"));
        String name = parseQueryParameter(request, "name");
        double price = Double.parseDouble(parseQueryParameter(request, "price"));
        return service.createProduct(productId, name, price);
    }

    private Observable<String> getProductsHandler(final HttpServerRequest<ByteBuf> request) {
        long userId = Long.parseLong(parseQueryParameter(request, "userId"));
        return service.getProductsForUser(userId);
    }

    private String parseQueryParameter(final HttpServerRequest<ByteBuf> request, final String parameterName) {
        List<String> values = request.getQueryParameters().get(parameterName);
        if (values.isEmpty()) {
            throw new RuntimeException("Invalid request!");
        } else {
            return values.get(0);
        }
    }
}
