package ru.perveevm.aop.actions;

public class CustomSleep implements Action {
    private final int time;

    public CustomSleep(final int time) {
        this.time = time;
    }

    @Override
    public void perform() throws InterruptedException {
        Thread.sleep(time);
    }
}
