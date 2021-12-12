package me.saturn.delta.mixin;

import me.saturn.delta.Delta;
import me.saturn.delta.command.Command;
import me.saturn.delta.command.Registry;
import me.saturn.delta.utils.ChatUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(ClientPlayerEntity.class) public class ClientPlayerEntityMixin {
    // i made a proper command system :troll:
    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true) private void onSendChatMessage(String message, CallbackInfo ci) {
        if (message.startsWith(Delta.getPrefix())) {
            ci.cancel();
            String mSpl = message.substring(Delta.getPrefix().length());
            String[] args = mSpl.split(" +");
            String cmd = args[0].toLowerCase();
            args = Arrays.copyOfRange(args, 1, args.length);
            for (Command command : Registry.instance().getCommands()) {
                if (command.shouldExecute(cmd)) {
                    command.execute(args);
                    return;
                }
            }
            ChatUtils.message("Command not found. See "+Delta.getPrefix()+"help.");
        }
    }
}
