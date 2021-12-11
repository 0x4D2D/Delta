package me.saturn.delta.command.impl;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.saturn.delta.Delta;
import me.saturn.delta.command.Command;
import me.saturn.delta.utils.ChatUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Boom extends Command {
    public Boom() {
        super("Boom", "big boom", "boom");
    }

    @Override public void execute(String[] args) {
        ItemStack boom = new ItemStack(Items.BLAZE_SPAWN_EGG, 1);
        if (args.length == 0) {
            ChatUtils.message("Please provide a power!");
            return;
        }
        try {
            boom.setNbt(StringNbtReader.parse("{display:{Name:'{\"text\":\"Kaboom\",\"color\":\"red\",\"italic\":false}',Lore:['{\"text\":\"owo\",\"color\":\"blue\",\"italic\":false}']},EntityTag:{id:\"minecraft:fireball\",ExplosionPower:" + args[0] + ",power:[0.0,-1.0,0.0]}}"));
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        ClientPlayerEntity player = Delta.c.player;
        ItemStack stored = player.getMainHandStack();
        player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(36 + player.getInventory().selectedSlot, boom));
        BlockHitResult b = new BlockHitResult(new Vec3d(0.5, 0.5, 0.5), Direction.UP, new BlockPos(player.getX(), player.getY() - 2, player.getZ()), true);
        player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, b));
        player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(36 + player.getInventory().selectedSlot, stored));
        ChatUtils.message("Kaboom!");
    }
}
