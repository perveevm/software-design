package ru.perveevm.events.services;

import org.springframework.stereotype.Service;
import ru.perveevm.events.commands.SubscriptionExtensionCommand;
import ru.perveevm.events.events.SubscriptionExtensionEvent;
import ru.perveevm.events.repository.SubscriptionExtensionRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
public class SubscriptionService {
    private final SubscriptionExtensionRepository subscriptionRepository;

    public SubscriptionService(final SubscriptionExtensionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void extendSubscription(final SubscriptionExtensionCommand command) {
        SubscriptionExtensionEvent event = new SubscriptionExtensionEvent();
        event.setClientId(command.getClientId());
        event.setSubscriptionDays(command.getSubscriptionDays());
        subscriptionRepository.save(event);
    }

    public Instant getSubscriptionEnd(final Long clientId) {
        List<SubscriptionExtensionEvent> events = subscriptionRepository.findAllByClientId(clientId);
        if (events.isEmpty()) {
            return null;
        }

        events.sort(Comparator.comparing(SubscriptionExtensionEvent::getModificationTime));
        SubscriptionExtensionEvent lastEvent = events.get(0);
        Instant result = lastEvent.getModificationTime().plus(lastEvent.getSubscriptionDays(), ChronoUnit.DAYS);
        for (int i = 1; i < events.size(); i++) {
            SubscriptionExtensionEvent currentEvent = events.get(i);
            if (currentEvent.getModificationTime().isAfter(result)) {
                result = currentEvent.getModificationTime().plus(currentEvent.getSubscriptionDays(), ChronoUnit.DAYS);
            } else {
                result = result.plus(currentEvent.getSubscriptionDays(), ChronoUnit.DAYS);
            }
        }

        return result;
    }
}
