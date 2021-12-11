package me.saturn.delta.command.impl;

import me.saturn.delta.Delta;
import me.saturn.delta.command.Command;
import me.saturn.delta.utils.ChatUtils;
import net.minecraft.world.GameMode;

public class Survival extends Command {
    public Survival() {
        super("Survival", "sets gamemode to survival", "survival", "gms");
    }

    @Override public void execute(String[] args) {
        Delta.c.interactionManager.setGameMode(GameMode.SURVIVAL);
        ChatUtils.message("Set Gamemode to Survival!");
    }
}
