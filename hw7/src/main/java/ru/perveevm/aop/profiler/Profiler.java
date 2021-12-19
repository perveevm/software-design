package ru.perveevm.aop.profiler;

import java.util.*;

public class Profiler {
    private final Deque<MethodCall> stack = new ArrayDeque<>();
    private final List<MethodCall> rootCalls = new ArrayList<>();
    private final Map<String, Integer> callsNumber = new HashMap<>();
    private final Map<String, Long> allTime = new HashMap<>();

    public void start(final String name) {
        MethodCall call = new MethodCall(name, System.nanoTime());
        if (callsNumber.containsKey(name)) {
            callsNumber.put(name, callsNumber.get(name) + 1);
        } else {
            callsNumber.put(name, 1);
        }

        if (stack.isEmpty()) {
            rootCalls.add(call);
        } else {
            stack.peekLast().addChild(call);
        }
        stack.addLast(call);
    }

    public void end(final String name) {
        MethodCall call = stack.removeLast();
        call.setEndTime(System.nanoTime());

        if (!call.getName().equals(name)) {
            throw new IllegalStateException("Invalid call tree");
        }

        if (allTime.containsKey(name)) {
            allTime.put(name, allTime.get(name) + call.getExecutionTime().toNanos());
        } else {
            allTime.put(name, call.getExecutionTime().toNanos());
        }
    }

    public List<MethodCall> getRootCals() {
        return rootCalls;
    }

    public Map<String, Integer> getCallsNumber() {
        return callsNumber;
    }

    public Map<String, Long> getAllTime() {
        return allTime;
    }
}
