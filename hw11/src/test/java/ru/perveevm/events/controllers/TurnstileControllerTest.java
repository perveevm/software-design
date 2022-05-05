package ru.perveevm.events.controllers;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.perveevm.events.commands.ClientCreationCommand;
import ru.perveevm.events.commands.SubscriptionExtensionCommand;
import ru.perveevm.events.events.SubscriptionExtensionEvent;
import ru.perveevm.events.repository.ClientCreationRepository;
import ru.perveevm.events.repository.SubscriptionExtensionRepository;
import ru.perveevm.events.repository.TurnstileRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class TurnstileControllerTest {
    @Autowired
    private TurnstileController turnstileController;
    @Autowired
    private AdminController adminController;

    @Autowired
    private ClientCreationRepository clientCreationRepository;
    @Autowired
    private SubscriptionExtensionRepository subscriptionExtensionRepository;
    @Autowired
    private TurnstileRepository turnstileRepository;

    private Long unexpiredClient;
    private Long expiredClient;

    @BeforeEach
    public void prepare() {
        unexpiredClient = adminController.createClient(new ClientCreationCommand("unexpired"));
        expiredClient = adminController.createClient(new ClientCreationCommand("expired"));
        adminController.extendSubscription(new SubscriptionExtensionCommand(unexpiredClient, 1000L));
    }

    @AfterEach
    public void clean() {
        clientCreationRepository.deleteAll();
        subscriptionExtensionRepository.deleteAll();
        turnstileRepository.deleteAll();
    }

    @Test
    public void unexpiredGoIn() {
        AssertionsForClassTypes.assertThatNoException()
                .isThrownBy(() -> turnstileController.addIn(unexpiredClient));
    }

    @Test
    public void expiredGoIn() {
        AssertionsForClassTypes.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> turnstileController.addIn(expiredClient));
    }

    @Test
    public void twiceGoIn() {
        turnstileController.addIn(unexpiredClient);
        AssertionsForClassTypes.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> turnstileController.addIn(unexpiredClient));
    }

    @Test
    public void outWithoutIn() {
        AssertionsForClassTypes.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> turnstileController.addOut(unexpiredClient));
    }

    @Test
    public void inOut() {
        turnstileController.addIn(unexpiredClient);
        AssertionsForClassTypes.assertThatNoException()
                .isThrownBy(() -> turnstileController.addOut(unexpiredClient));
    }

    @Test
    public void twiceGoOut() {
        turnstileController.addIn(unexpiredClient);
        turnstileController.addOut(unexpiredClient);
        AssertionsForClassTypes.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> turnstileController.addOut(unexpiredClient));
    }

    @Test
    public void justEndedSubscription() {
        SubscriptionExtensionEvent event = new SubscriptionExtensionEvent();
        event.setClientId(expiredClient);
        event.setSubscriptionDays(10L);
        event = subscriptionExtensionRepository.save(event);
        event.setModificationTime(Instant.now().minus(11, ChronoUnit.DAYS));
        subscriptionExtensionRepository.save(event);
        AssertionsForClassTypes.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> turnstileController.addIn(expiredClient));
    }
}
