package me.saturn.delta.command;

import me.saturn.delta.command.impl.Boom;
import me.saturn.delta.command.impl.Crash;
import me.saturn.delta.command.impl.Creative;
import me.saturn.delta.command.impl.Dupe;
import me.saturn.delta.command.impl.Help;
import me.saturn.delta.command.impl.Item;
import me.saturn.delta.command.impl.Prefix;
import me.saturn.delta.command.impl.Survival;

import java.util.ArrayList;
import java.util.List;

public class Registry {
    private static Registry INSTANCE;
    private Registry() {
        if (INSTANCE != null) throw new RuntimeException("Illegal construction of Registry class");
        init();
    }
    public static Registry instance() {
        if (INSTANCE == null) INSTANCE = new Registry();
        return INSTANCE;
    }
    private final List<Command> commands = new ArrayList<>();

    private void init() {
        commands.add(new Help());
        commands.add(new Crash());
        commands.add(new Item());
        commands.add(new Boom());
        commands.add(new Creative());
        commands.add(new Survival());
        commands.add(new Dupe());
        commands.add(new Prefix());
    }

    public List<Command> getCommands() {
        return new ArrayList<>(commands); // copy to new array list to prevent modifying the main list
    }
}
