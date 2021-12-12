package me.saturn.delta.command.impl;

import me.saturn.delta.command.Command;
import me.saturn.delta.command.Registry;
import me.saturn.delta.utils.ChatUtils;

public class Help extends Command {
    public Help() {
        super("Help", "Shows all commands", "help", "?");
    }

    @Override public void execute(String[] args) {
        ChatUtils.message("All commands:");
        for (Command command : Registry.instance().getCommands()) {
            String alist = String.join(", ",command.getTriggers());
            ChatUtils.message(command.getName()+" ("+alist+")");
            ChatUtils.message("  "+command.getDescription());
        }
    }
}
