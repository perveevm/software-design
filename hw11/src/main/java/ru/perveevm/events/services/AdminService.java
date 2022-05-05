package ru.perveevm.events.services;

import org.springframework.stereotype.Service;
import ru.perveevm.events.commands.ClientCreationCommand;
import ru.perveevm.events.commands.SubscriptionExtensionCommand;
import ru.perveevm.events.events.ClientCreationEvent;
import ru.perveevm.events.events.SubscriptionExtensionEvent;
import ru.perveevm.events.queries.Subscription;
import ru.perveevm.events.repository.ClientCreationRepository;

@Service
public class AdminService {
    private final ClientCreationRepository clientRepository;
    private final SubscriptionService subscriptionService;

    public AdminService(final ClientCreationRepository clientRepository,
                        final SubscriptionService subscriptionService) {
        this.clientRepository = clientRepository;
        this.subscriptionService = subscriptionService;
    }

    public Long createClient(final ClientCreationCommand command) {
        ClientCreationEvent event = new ClientCreationEvent();
        event.setClientName(command.getName());
        return clientRepository.save(event).getId();
    }

    public void extendSubscription(final SubscriptionExtensionCommand command) {
        subscriptionService.extendSubscription(command);
    }

    public Subscription getSubscription(final Long clientId) {
        ClientCreationEvent event = clientRepository.findById(clientId).orElseThrow(
                () -> new RuntimeException("Client not found"));
        return new Subscription(event.getClientName(), subscriptionService.getSubscriptionEnd(clientId));
    }
}
