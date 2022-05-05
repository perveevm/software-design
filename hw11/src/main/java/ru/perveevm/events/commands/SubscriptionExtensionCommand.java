package ru.perveevm.events.commands;

public class SubscriptionExtensionCommand {
    private final Long clientId;
    private final Long subscriptionDays;

    public SubscriptionExtensionCommand(final Long clientId, final Long subscriptionDays) {
        this.clientId = clientId;
        this.subscriptionDays = subscriptionDays;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getSubscriptionDays() {
        return subscriptionDays;
    }
}
