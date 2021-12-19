package ru.perveevm.aop.actions;

public class Main {
    public static void main(String[] args) throws Exception {
        Action[] actions = new Action[]{
                new SimplePing(),
                new CustomPing("ejudge.strategy48.ru", 10),
                new SimpleSleep(),
                new CustomSleep(2500)
        };

        for (Action action : actions) {
            action.perform();
        }
    }
}
