package me.saturn.delta.command.impl;

import me.saturn.delta.command.Command;

public class Dupe extends Command {
    public Dupe() {
        super("Dupe", "dupes an item (real)", "dupe");
    }

    @Override public void execute(String[] args) {
        System.exit(0);
    }
}
