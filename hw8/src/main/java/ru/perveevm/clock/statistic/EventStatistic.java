package ru.perveevm.clock.statistic;

import java.util.Map;

public interface EventStatistic {
    void incEvent(final String name);

    double getEventStatisticByName(final String name);

    Map<String, Double> getAllEventStatistic();

    void printStatistic();
}
