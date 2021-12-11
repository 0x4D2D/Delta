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
        int longestName = 0;
        for (Command command : Registry.instance().getCommands()) {
            longestName = Math.max(longestName, command.getName().length());
        }
        for (Command command : Registry.instance().getCommands()) {
            String alist = String.join(", ",command.getTriggers());
            ChatUtils.message(command.getName()+" ".repeat(longestName-command.getName().length())+"  "+alist);
            ChatUtils.message("  "+command.getDescription());
        }
    }
}
