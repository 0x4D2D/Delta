package me.saturn.delta.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.saturn.delta.Delta;


@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("HEAD"))
    public void onRender(MatrixStack matrix, float tickDelta, CallbackInfo ci){
        Delta.clienttick();
        if(Delta.timeoutTicks > 150){
            String s = "CRASHING: " + Delta.timeoutTicks + "ms";
            Delta.c.textRenderer.draw(matrix, s, (Delta.c.getWindow().getScaledWidth() / 2) - Delta.c.textRenderer.getWidth(s), 3, 7289492);
        }
    }
}
