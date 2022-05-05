package ru.perveevm.events.controllers;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.perveevm.events.commands.ClientCreationCommand;
import ru.perveevm.events.commands.SubscriptionExtensionCommand;
import ru.perveevm.events.events.TurnstileDirection;
import ru.perveevm.events.events.TurnstileEvent;
import ru.perveevm.events.queries.Statistics;
import ru.perveevm.events.repository.ClientCreationRepository;
import ru.perveevm.events.repository.SubscriptionExtensionRepository;
import ru.perveevm.events.repository.TurnstileRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@SpringBootTest
public class ReportControllerTest {
    @Autowired
    private AdminController adminController;
    @Autowired
    private ReportController reportController;

    @Autowired
    private ClientCreationRepository clientCreationRepository;
    @Autowired
    private SubscriptionExtensionRepository subscriptionExtensionRepository;
    @Autowired
    private TurnstileRepository turnstileRepository;

    private Long client1;
    private Long client2;

    @BeforeEach
    public void prepare() {
        client1 = adminController.createClient(new ClientCreationCommand("client_1"));
        client2 = adminController.createClient(new ClientCreationCommand("client_2"));

        SubscriptionExtensionCommand command = new SubscriptionExtensionCommand(client1, 10L);
        adminController.extendSubscription(command);

        command = new SubscriptionExtensionCommand(client2, 10L);
        adminController.extendSubscription(command);
    }

    @AfterEach
    public void clean() {
        clientCreationRepository.deleteAll();
        subscriptionExtensionRepository.deleteAll();
        turnstileRepository.deleteAll();
    }

    @Test
    public void testDailyStats() {
        addDayVisit(1L, client1);
        addDayVisit(1L, client2);
        addDayVisit(5L, client1);

        Map<Instant, Long> statistics = reportController.getDailyReport(6L);
        Assertions.assertThat(statistics).containsEntry(getInstantAgo(1L), 2L).containsEntry(getInstantAgo(5L), 1L);
    }

    @Test
    public void testAverage() {
        Statistics statistics = reportController.getAverageReport();
        Assertions.assertThat(statistics.getMinutesPerVisit()).isEqualTo(0.0);
        Assertions.assertThat(statistics.getVisitsPerDay()).isEqualTo(0.0);

        addDayVisit(1L, client1);
        addDayVisit(1L, client2);
        addDayVisit(5L, client1);

        statistics = reportController.getAverageReport();
        Assertions.assertThat(statistics.getMinutesPerVisit()).isEqualTo(24.0 * 60, Offset.offset(0.1));
        Assertions.assertThat(statistics.getVisitsPerDay()).isEqualTo(3.0 / 5.0, Offset.offset(0.1));
    }

    private void goIn(final Long days, final Long clientId) {
        TurnstileEvent event = new TurnstileEvent();
        event.setClientId(clientId);
        event.setDirection(TurnstileDirection.IN);
        event = turnstileRepository.save(event);
        event.setModificationTime(Instant.now().minus(days, ChronoUnit.DAYS));
        turnstileRepository.save(event);
    }

    private void goOut(final Long days, final Long clientId) {
        TurnstileEvent event = new TurnstileEvent();
        event.setClientId(clientId);
        event.setDirection(TurnstileDirection.OUT);
        event = turnstileRepository.save(event);
        event.setModificationTime(Instant.now().minus(days, ChronoUnit.DAYS));
        turnstileRepository.save(event);
    }

    private void addEpsilonVisit(final Long days, final Long clientId) {
        goIn(days, clientId);
        goOut(days, clientId);
    }

    private void addDayVisit(final Long days, final Long clientId) {
        goIn(days, clientId);
        goOut(days - 1, clientId);
    }

    private Instant getInstantAgo(final Long days) {
        return Instant.now().minus(days, ChronoUnit.DAYS)
                .truncatedTo(ChronoUnit.DAYS);
    }
}
