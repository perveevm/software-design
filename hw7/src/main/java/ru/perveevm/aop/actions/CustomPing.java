package ru.perveevm.aop.actions;

import java.io.IOException;

public class CustomPing implements Action {
    private final String host;
    private final int count;

    public CustomPing(final String host, final int count) {
        this.host = host;
        this.count = count;
    }

    @Override
    public void perform() throws IOException {
        new ProcessBuilder("ping", host, "-c", String.valueOf(count)).inheritIO().start();
    }
}
