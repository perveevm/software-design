package ru.perveevm.aop.profiler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MethodCall {
    private final String name;
    private final List<MethodCall> children = new ArrayList<>();
    private final long startTime;
    private long endTime;

    public MethodCall(final String name, final long startTime) {
        this.name = name;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public List<MethodCall> getChildren() {
        return children;
    }

    public void setEndTime(final long endTime) {
        this.endTime = endTime;
    }

    public void addChild(final MethodCall child) {
        children.add(child);
    }

    public Duration getExecutionTime() {
        return Duration.ofNanos(endTime - startTime);
    }

    public String getShortName() {
        String[] parts = name.split("\\.");
        return parts[parts.length - 2] + "." + parts[parts.length - 1];
    }
}
