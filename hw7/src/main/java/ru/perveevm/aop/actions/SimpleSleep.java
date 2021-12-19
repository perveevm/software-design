package ru.perveevm.aop.actions;

public class SimpleSleep implements Action {
    @Override
    public void perform() throws InterruptedException {
        Thread.sleep(1000);
    }
}
