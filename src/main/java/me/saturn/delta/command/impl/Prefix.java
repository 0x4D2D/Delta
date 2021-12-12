package me.saturn.delta.command.impl;

import me.saturn.delta.Delta;
import me.saturn.delta.command.Command;
import me.saturn.delta.utils.ChatUtils;

public class Prefix extends Command {
    public Prefix() {
        super("Prefix", "Sets the mod's prefix", "prefix", "p");
    }

    @Override public void execute(String[] args) {
        if (args.length == 0) {
            ChatUtils.message("cmon man i need a prefix to set");
            return;
        }
        Delta.config.getConfig().prefix = String.join(" ",args);
        ChatUtils.message("Set prefix to \""+Delta.getPrefix()+"\"");
    }
}
