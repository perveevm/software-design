package ru.perveevm.aop.profiler;

import org.aspectj.lang.Signature;

public aspect ProfilerAspect {
    Profiler profiler = Profilers.getProfiler();

    pointcut methodCall(): (call(* *(..)) || call(new(..))) && if(thisJoinPointStaticPart.getSignature()
            .getDeclaringType().getPackage().toString().startsWith("package " + System.getProperty("profiledPackage")));

    before(): methodCall() {
        if (getFullMethodName(thisJoinPointStaticPart.getSignature()).contains("ConsoleLogger")) {
            return;
        }
        profiler.start(getFullMethodName(thisJoinPointStaticPart.getSignature()));
    }

    after(): methodCall() {
        if (getFullMethodName(thisJoinPointStaticPart.getSignature()).contains("ConsoleLogger")) {
            return;
        }
        profiler.end(getFullMethodName(thisJoinPointStaticPart.getSignature()));
    }

    private String getFullMethodName(final Signature signature) {
        return signature.getDeclaringType().getName() + "." + signature.getName();
    }
}
