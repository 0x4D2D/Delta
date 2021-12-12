package me.saturn.delta.mixin;

import me.saturn.delta.Delta;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class) public class ChatScreenMixin {
    @Shadow protected TextFieldWidget chatField;

    @Inject(at = @At("HEAD"), method = "render") void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (chatField.getText().startsWith(Delta.getPrefix())) {
            chatField.setEditableColor(7289492);
        } else {
            chatField.setEditableColor(14737632);
        }
        Delta.c.textRenderer.drawWithShadow(matrices, "Delta v1.0", 5, Delta.c.getWindow().getScaledHeight() - 22, 7289492);
    }
}
