package ru.perveevm.clock;

import ru.perveevm.clock.statistic.EventStatistic;
import ru.perveevm.clock.statistic.EventStatisticImpl;

public class Main {
    public static void main(String[] args) {
        EventStatistic eventStatistic = new EventStatisticImpl(new NormalClock());

        for (int i = 0; i < 10; i++) {
            eventStatistic.incEvent("a");
        }
        for (int i = 0; i < 10; i++) {
            eventStatistic.incEvent("b");
        }
        for (int i = 0; i < 100; i++) {
            eventStatistic.incEvent("c");
        }

        eventStatistic.printStatistic();
    }
}
