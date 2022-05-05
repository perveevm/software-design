package ru.perveevm.events.controllers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.perveevm.events.commands.ClientCreationCommand;
import ru.perveevm.events.commands.SubscriptionExtensionCommand;
import ru.perveevm.events.queries.Subscription;
import ru.perveevm.events.repository.SubscriptionExtensionRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @Test
    public void createClient() {
        Long clientId = adminController.createClient(new ClientCreationCommand("client_name"));

        Subscription subscription = adminController.getSubscription(clientId);
        Assertions.assertThat(subscription.getClientName()).isEqualTo("client_name");
        Assertions.assertThat(subscription.getValidUntil()).isNull();
    }

    @Test
    public void extendSubscription() {
        Instant currentTime = Instant.now();
        Long clientId = adminController.createClient(new ClientCreationCommand("client_name_1"));
        SubscriptionExtensionCommand command = new SubscriptionExtensionCommand(clientId, 10L);
        adminController.extendSubscription(command);

        Subscription subscription = adminController.getSubscription(clientId);
        Assertions.assertThat(subscription.getClientName()).isEqualTo("client_name_1");
        Assertions.assertThat(subscription.getValidUntil()).isAfter(currentTime.plus(10, ChronoUnit.DAYS));
        Assertions.assertThat(subscription.getValidUntil()).isBefore(currentTime.plus(11, ChronoUnit.DAYS));
    }

    @Test
    public void extendSubscriptionTwice() {
        Instant currentTime = Instant.now();
        Long clientId = adminController.createClient(new ClientCreationCommand("client_name_1"));
        SubscriptionExtensionCommand command = new SubscriptionExtensionCommand(clientId, 10L);
        adminController.extendSubscription(command);
        adminController.extendSubscription(command);

        Subscription subscription = adminController.getSubscription(clientId);
        Assertions.assertThat(subscription.getClientName()).isEqualTo("client_name_1");
        Assertions.assertThat(subscription.getValidUntil()).isAfter(currentTime.plus(20, ChronoUnit.DAYS));
        Assertions.assertThat(subscription.getValidUntil()).isBefore(currentTime.plus(21, ChronoUnit.DAYS));
    }
}
