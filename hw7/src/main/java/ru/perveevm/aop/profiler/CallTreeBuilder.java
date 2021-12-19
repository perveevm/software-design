package ru.perveevm.aop.profiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;

public class CallTreeBuilder {
    private final Profiler profiler;
    private final Map<String, Integer> nameToId = new HashMap<>();
    private final Map<Integer, Set<Integer>> edges = new HashMap<>();
    private final List<MethodCall> order = new ArrayList<>();

    public CallTreeBuilder(final Profiler profiler) {
        this.profiler = profiler;
    }

    public void build(final Path outputFilePath) throws IOException {
        for (MethodCall root : profiler.getRootCals()) {
            dfs(root);
        }

        StringBuilder s = new StringBuilder();
        s.append("digraph g {").append(System.lineSeparator());
        s.append("node [shape=circle, width=0.2];").append(System.lineSeparator());
        for (Map.Entry<String, Integer> e : nameToId.entrySet()) {
            MethodCall call = order.get(e.getValue());
            String text = String.format("method = %s\\ncalls = %d\\ntime = %dms", e.getKey(),
                    profiler.getCallsNumber().get(call.getName()),
                    Duration.ofNanos(profiler.getAllTime().get(call.getName())).toMillis());
            s.append(String.format("%d [label=\"%s\"];", e.getValue(), text)).append(System.lineSeparator());
        }
        for (Integer from : edges.keySet()) {
            for (Integer to : edges.get(from)) {
                s.append(from).append(" -> ").append(to).append(";").append(System.lineSeparator());
            }
        }
        s.append("}").append(System.lineSeparator());

        BufferedWriter writer = Files.newBufferedWriter(outputFilePath);
        writer.write(s.toString());
        writer.close();
    }

    private void dfs(final MethodCall call) {
        String name = call.getShortName();
        if (!nameToId.containsKey(name)) {
            nameToId.put(name, nameToId.size());
            edges.put(nameToId.size() - 1, new HashSet<>());
            order.add(call);
        }

        for (MethodCall child : call.getChildren()) {
            dfs(child);
            edges.get(nameToId.get(name)).add(nameToId.get(child.getShortName()));
        }
    }
}
