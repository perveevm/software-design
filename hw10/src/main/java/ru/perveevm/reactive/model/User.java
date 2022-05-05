package ru.perveevm.reactive.model;

import org.bson.Document;

/**
 * @author Perveev Mike (perveev_m@mail.ru)
 */
public class User {
    private final long id;
    private final String name;
    private final Currency currency;

    public User(final long id, final String name, final Currency currency) {
        this.id = id;
        this.name = name;
        this.currency = currency;
    }

    public User(final Document document) {
        this(document.getLong("id"), document.getString("name"), Currency.valueOf(document.getString("currency")));
    }

    public Document toDocument() {
        return new Document().append("id", id).append("name", name).append("currency", currency.toString());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Currency getCurrency() {
        return currency;
    }
}
