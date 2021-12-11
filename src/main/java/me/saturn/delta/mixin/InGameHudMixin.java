package me.saturn.delta.mixin;

import me.saturn.delta.Delta;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(InGameHud.class) public class InGameHudMixin {
    @Inject(method = "render", at = @At("HEAD")) public void onRender(MatrixStack matrix, float tickDelta, CallbackInfo ci) {
        if (System.currentTimeMillis() - Delta.lastServerTick > 2000) {
            String s = "CRASHING: " + (System.currentTimeMillis() - Delta.lastServerTick) + " ms";
            Delta.c.textRenderer.draw(matrix, s, (Delta.c.getWindow().getScaledWidth() / 2f) - (Delta.c.textRenderer.getWidth(s) / 2f), 3, 7289492);
        }
    }
}
