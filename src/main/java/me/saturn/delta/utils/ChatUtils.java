package me.saturn.delta.utils;

import me.saturn.delta.Delta;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ChatUtils {
    public static void message(String message) {
        if (Delta.c.player == null) return;
        Delta.c.player.sendMessage(Text.of(Formatting.DARK_PURPLE + "[" + Formatting.LIGHT_PURPLE + "Delta" + Formatting.DARK_PURPLE + "] " + Formatting.RESET + message), false);
    }
}
