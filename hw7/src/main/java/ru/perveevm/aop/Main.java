package ru.perveevm.aop;

import ru.perveevm.aop.profiler.CallTreeBuilder;
import ru.perveevm.aop.profiler.MethodCall;
import ru.perveevm.aop.profiler.Profiler;
import ru.perveevm.aop.profiler.Profilers;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void dfs(final MethodCall call, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("\t");
        }
        System.out.println(call.getName());

        for (MethodCall child : call.getChildren()) {
            dfs(child, depth + 1);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 4) {
            System.out.println("Expected arguments: <classpath> <package name> <start class with main() method> <path where to " +
                    "generate tree> <parameters...>");
            return;
        }

        System.setProperty("profiledPackage", args[1]);
        Class<?> clazz = Class.forName(args[2]);
        Method main = clazz.getMethod("main", String[].class);
        String[] programArgs;
        if (args.length > 4) {
            programArgs = Arrays.copyOfRange(args, 4, args.length);
        } else {
            programArgs = null;
        }
        main.invoke(null, new Object[]{programArgs});

        Profiler profiler = Profilers.getProfiler();
        List<MethodCall> roots = profiler.getRootCals();
        System.out.println("Text-style tree:");
        for (MethodCall call : roots) {
            dfs(call, 0);
        }

        System.out.println("Count of methods' calls:");
        for (Map.Entry<String, Integer> e : profiler.getCallsNumber().entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }

        System.out.println("Average execution time of methods' calls:");
        for (Map.Entry<String, Long> e : profiler.getAllTime().entrySet()) {
            long allTime = e.getValue();
            int count = profiler.getCallsNumber().get(e.getKey());
            long average = allTime / count;
            System.out.println(e.getKey() + ": " + Duration.ofNanos(average).toMillis() + "ms");
        }

        System.out.println("Summary execution time of methods' calls:");
        for (Map.Entry<String, Long> e : profiler.getAllTime().entrySet()) {
            System.out.println(e.getKey() + ": " + Duration.ofNanos(e.getValue()).toMillis() + "ms");
        }

        CallTreeBuilder builder = new CallTreeBuilder(profiler);
        builder.build(Path.of(args[3]));
    }
}
