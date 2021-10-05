package me.saturn.delta.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.saturn.delta.Delta;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.awt.Color;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Shadow
    protected TextFieldWidget chatField;

    @Inject(at = {@At("HEAD")}, method = "render")
	private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci){
		if(chatField.getText().startsWith("$")){
            chatField.setEditableColor(7289492);
        }else{
            chatField.setEditableColor(14737632);
        }
        Delta.c.textRenderer.drawWithShadow(matrices, "Delta v1.0", 5, Delta.c.getWindow().getScaledHeight() - 22, 7289492);
	}
}
