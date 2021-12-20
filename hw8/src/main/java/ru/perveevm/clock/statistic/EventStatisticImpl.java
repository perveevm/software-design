package ru.perveevm.clock.statistic;

import ru.perveevm.clock.Clock;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventStatisticImpl implements EventStatistic {
    private final Clock clock;
    private final Map<String, List<Instant>> eventTimings = new HashMap<>();
    private static final int MINUTES = 60;

    public EventStatisticImpl(final Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        eventTimings.putIfAbsent(name, new ArrayList<>());
        eventTimings.get(name).add(clock.now());
    }

    @Override
    public double getEventStatisticByName(String name) {
        Instant from = subtractHour(clock.now());
        double result = 0.0;
        if (eventTimings.containsKey(name)) {
            result = eventTimings.get(name).stream().filter(p -> p.isAfter(from)).count();
        }
        return result / MINUTES;
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        Map<String, Double> result = new HashMap<>();
        for (String name : eventTimings.keySet()) {
            result.put(name, getEventStatisticByName(name));
        }
        return result;
    }

    @Override
    public void printStatistic() {
        for (Map.Entry<String, Double> eventStatistic : getAllEventStatistic().entrySet()) {
            System.out.println(eventStatistic.getKey() + ":\t" + eventStatistic.getValue());
        }
    }

    private Instant subtractHour(final Instant from) {
        return from.minus(Duration.ofHours(1));
    }
}
