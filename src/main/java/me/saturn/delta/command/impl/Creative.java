package me.saturn.delta.command.impl;

import me.saturn.delta.Delta;
import me.saturn.delta.command.Command;
import me.saturn.delta.utils.ChatUtils;
import net.minecraft.world.GameMode;

public class Creative extends Command {
    public Creative() {
        super("Creative", "sets gamemode to creative", "creative", "gmc");
    }

    @Override public void execute(String[] args) {
        Delta.c.interactionManager.setGameMode(GameMode.CREATIVE);
        ChatUtils.message("Set Gamemode to Creative!");
    }
}
