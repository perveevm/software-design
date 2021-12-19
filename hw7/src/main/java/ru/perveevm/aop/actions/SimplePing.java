package ru.perveevm.aop.actions;

import java.io.IOException;

public class SimplePing implements Action {
    @Override
    public void perform() throws IOException {
        new ProcessBuilder("ping", "8.8.8.8", "-c", "3").inheritIO().start();
    }
}
