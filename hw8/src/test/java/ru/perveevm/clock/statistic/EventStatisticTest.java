package ru.perveevm.clock.statistic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.perveevm.clock.SettableClock;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class EventStatisticTest {
    private static final double EPS = 1e-9;

    private final SettableClock clock = new SettableClock(Instant.now());
    private EventStatistic eventStatistic;
    private Instant startInstant;

    @BeforeEach
    public void setupTest() {
        clock.setNow(Instant.now());
        startInstant = clock.now();
        eventStatistic = new EventStatisticImpl(clock);
    }

    @Test
    public void testNoAdditions() {
        Assertions.assertEquals(0.0, eventStatistic.getEventStatisticByName("a"), EPS);
    }

    @Test
    public void testDeletedEvent() {
        eventStatistic.incEvent("a");
        clock.setNow(startInstant.plus(Duration.ofHours(1)));
        Assertions.assertEquals(0.0, eventStatistic.getEventStatisticByName("a"), EPS);
    }

    @Test
    public void testSmallThanHour() {
        eventStatistic.incEvent("a");
        clock.setNow(startInstant.plus(Duration.ofMillis(60 * 60 * 1000 - 1)));
        Assertions.assertEquals(1.0 / 60.0, eventStatistic.getEventStatisticByName("a"), EPS);
    }

    @Test
    public void testOneName() {
        for (int i = 0; i < 10; i++) {
            eventStatistic.incEvent("a");
        }
        Assertions.assertEquals(1.0 / 6.0, eventStatistic.getEventStatisticByName("a"), EPS);
    }

    @Test
    public void testManyNames() {
        for (int i = 0; i < 5; i++) {
            eventStatistic.incEvent("a");
            clock.setNow(startInstant.plus(Duration.ofSeconds(i + 1)));
        }
        for (int i = 0; i < 10; i++) {
            eventStatistic.incEvent("b");
            clock.setNow(startInstant.plus(Duration.ofSeconds(5 + 2 * i + 2)));
        }

        Assertions.assertEquals(1.0 / 12.0, eventStatistic.getEventStatisticByName("a"), EPS);
        Assertions.assertEquals(1.0 / 6.0, eventStatistic.getEventStatisticByName("b"), EPS);

        Map<String, Double> allStatistics = eventStatistic.getAllEventStatistic();
        Assertions.assertEquals(allStatistics.size(), 2);
        Assertions.assertTrue(allStatistics.containsKey("a"));
        Assertions.assertTrue(allStatistics.containsKey("b"));
        Assertions.assertEquals(allStatistics.get("a"), 1.0 / 12.0, EPS);
        Assertions.assertEquals(allStatistics.get("b"), 1.0 / 6.0, EPS);
    }
}
