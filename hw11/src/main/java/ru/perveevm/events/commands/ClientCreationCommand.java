package ru.perveevm.events.commands;

public class ClientCreationCommand {
    private final String name;

    public ClientCreationCommand(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
