package me.saturn.delta;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Delta implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger          LOGGER       = LogManager.getLogger("delta");
    public static       MinecraftClient c            = MinecraftClient.getInstance();
    public static long lastServerTick = System.currentTimeMillis();

    public static void servertick() {
        lastServerTick = System.currentTimeMillis();
    }

    @Override public void onInitialize() {
        LOGGER.info("Delta Loaded!");
    }
}

