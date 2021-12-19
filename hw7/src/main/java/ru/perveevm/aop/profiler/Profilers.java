package ru.perveevm.aop.profiler;

public class Profilers {
    private static final Profiler profiler = new Profiler();

    public static Profiler getProfiler() {
        return profiler;
    }
}
