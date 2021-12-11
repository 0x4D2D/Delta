package me.saturn.delta.command;

import java.util.Arrays;

public abstract class Command {
    String[] t;
    String n, d;
    public Command(String name, String description, String... triggers) {
        this.n = name;
        this.d = description;
        this.t = triggers;
    }

    public String getDescription() {
        return d;
    }

    public String getName() {
        return n;
    }

    public String[] getTriggers() {
        return t;
    }

    public boolean shouldExecute(String cmd) {
        return Arrays.stream(getTriggers()).anyMatch(s -> s.equalsIgnoreCase(cmd));
    }

    public abstract void execute(String[] args);
}
