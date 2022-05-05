package ru.perveevm.reactive.model;

import org.bson.Document;
import ru.perveevm.reactive.util.CurrencyUtils;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 */
public class Product {
    private final long id;
    private final String name;
    private final double price;

    public Product(final long id, final String name, final double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(final Document document) {
        this(document.getLong("id"), document.getString("name"), document.getDouble("price"));
    }

    public Document toDocument() {
        return new Document().append("id", id).append("name", name).append("price", price);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String toString(final Currency currency) {
        return "ID: " + id + System.lineSeparator() +
                "Name: " + name + System.lineSeparator() +
                "Price in USD: " + price + System.lineSeparator() +
                "Price in " + currency.toString() + ": " +
                CurrencyUtils.convert(Currency.USD, currency, price) + System.lineSeparator();
    }
}
