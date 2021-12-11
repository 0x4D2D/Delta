package me.saturn.delta.command.impl;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.saturn.delta.Delta;
import me.saturn.delta.command.Command;
import me.saturn.delta.utils.ChatUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;

import java.util.function.Supplier;

public class Item extends Command {
    enum ItemImpl {
        LIST("List", () -> {
            for (ItemImpl value : ItemImpl.values()) {
                ChatUtils.message(value.getName());
            }
            return null;
        }), ARMOR("ArmorCrash", () -> {
            ItemStack e = new ItemStack(Items.CHEST, 1);
            try {
                e.setNbt(StringNbtReader.parse("{BlockEntityTag:{Items:[{Count:1b,Slot:13b,id:\"minecraft:chainmail_chestplate\",tag:{Damage:0,Enchantments:[{id:\"minecraft:\",lvl:1s}]}}],id:\"minecraft:chest\"},display:{Lore:['{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"light_purple\",\"text\":\"Wear the armor\"}],\"text\":\"\"}'],Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_purple\",\"text\":\"Crash Armor\"}],\"text\":\"\"}'}}"));
            } catch (CommandSyntaxException e1) {
                e1.printStackTrace();
            }
//            Delta.c.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(36 + Delta.c.player.getInventory().selectedSlot, e));
            return e;
        }), CRASHSHULKER("CrashShulker", () -> {
            ItemStack f = new ItemStack(Items.SHULKER_BOX, 1);
            try {
                f.setNbt(StringNbtReader.parse("{BlockEntityTag:{LootTable:\" \"},display:{Lore:['{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"light_purple\",\"text\":\"Dispense This\"}],\"text\":\"\"}'],Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_purple\",\"text\":\"Crash Shulker\"}],\"text\":\"\"}'}}"));
            } catch (CommandSyntaxException e1) {
                e1.printStackTrace();
            }
            return f;
        }), CRASHEGG("CrashEgg", () -> {
            ItemStack g = new ItemStack(Items.SHULKER_SPAWN_EGG, 1);
            try {
                g.setNbt(StringNbtReader.parse("{EntityTag:{id:\"minecraft:small_fireball\",power:[1.0E43d,0.0d,0.0d]},display:{Lore:['{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"light_purple\",\"text\":\"Place This\"}],\"text\":\"\"}'],Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_purple\",\"text\":\"Crash Spawn Egg\"}],\"text\":\"\"}'}}"));
            } catch (CommandSyntaxException e1) {
                e1.printStackTrace();
            }
            return g;
        });
        Supplier<ItemStack> r;
        String n;
        ItemImpl(String name, Supplier<ItemStack> exec) {
            this.r = exec;
            this.n = name;
        }

        public String getName() {
            return n;
        }

        public ItemStack get() {
            return r.get();
        }
    }

    public Item() {
        super("Item", "gives you crash items", "item");
    }

    @Override public void execute(String[] args) {
        if (args.length == 0) {
            ChatUtils.message("use $item <item> (use $item list to show all)");
            return;
        }
        if (!Delta.c.interactionManager.hasCreativeInventory()) {
            ChatUtils.message("You must be in creative to do this!");
            return;
        }
        String n = args[0];
        for (ItemImpl value : ItemImpl.values()) {
            if (value.getName().equalsIgnoreCase(n)) {
                ItemStack g = value.get();
                Delta.c.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(36 + Delta.c.player.getInventory().selectedSlot, g));
                ChatUtils.message("Tried to give you the item");
                return;
            }
        }
        ChatUtils.message("Couldnt find item. do $item list to list them");
    }
}
