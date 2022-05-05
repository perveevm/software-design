package ru.perveevm.events.services;

import org.springframework.stereotype.Service;
import ru.perveevm.events.events.TurnstileDirection;
import ru.perveevm.events.events.TurnstileEvent;
import ru.perveevm.events.repository.TurnstileRepository;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
public class TurnstileService {
    private final SubscriptionService subscriptionService;
    private final TurnstileRepository turnstileRepository;

    public TurnstileService(final SubscriptionService subscriptionService,
                            final TurnstileRepository turnstileRepository) {
        this.subscriptionService = subscriptionService;
        this.turnstileRepository = turnstileRepository;
    }

    public void addClientIn(final Long clientId) {
        Instant subscriptionEnd = subscriptionService.getSubscriptionEnd(clientId);
        if (subscriptionEnd == null || subscriptionEnd.isBefore(Instant.now())) {
            throw new RuntimeException("Subscription is over");
        }

        List<TurnstileEvent> events = turnstileRepository.findAllByClientId(clientId);
        events.sort(Comparator.comparing(TurnstileEvent::getModificationTime).reversed());
        if (!events.isEmpty() && events.get(0).getDirection() == TurnstileDirection.IN) {
            throw new RuntimeException("Already inside");
        }

        TurnstileEvent event = new TurnstileEvent();
        event.setClientId(clientId);
        event.setDirection(TurnstileDirection.IN);
        turnstileRepository.save(event);
    }

    public void addClientOut(final Long clientId) {
        List<TurnstileEvent> events = turnstileRepository.findAllByClientId(clientId);
        events.sort(Comparator.comparing(TurnstileEvent::getModificationTime).reversed());
        if (events.isEmpty() || events.get(0).getDirection() == TurnstileDirection.OUT) {
            throw new RuntimeException("Already outside");
        }

        TurnstileEvent event = new TurnstileEvent();
        event.setClientId(clientId);
        event.setDirection(TurnstileDirection.OUT);
        turnstileRepository.save(event);
    }
}
