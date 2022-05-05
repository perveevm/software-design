package ru.perveevm.events.queries;

public class Statistics {
    private final double visitsPerDay;
    private final double minutesPerVisit;

    public Statistics(final double visitsPerDay, final double minutesPerVisit) {
        this.visitsPerDay = visitsPerDay;
        this.minutesPerVisit = minutesPerVisit;
    }

    public double getVisitsPerDay() {
        return visitsPerDay;
    }

    public double getMinutesPerVisit() {
        return minutesPerVisit;
    }
}
