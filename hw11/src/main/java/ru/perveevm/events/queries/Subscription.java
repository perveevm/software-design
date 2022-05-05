package ru.perveevm.events.queries;

import java.time.Instant;

public class Subscription {
    private final String clientName;
    private final Instant validUntil;

    public Subscription(final String clientName, final Instant validUntil) {
        this.clientName = clientName;
        this.validUntil = validUntil;
    }

    public String getClientName() {
        return clientName;
    }

    public Instant getValidUntil() {
        return validUntil;
    }
}
