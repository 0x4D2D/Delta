package me.saturn.delta.mixin;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.saturn.delta.Delta;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class) public abstract class ClientConnectionMixin extends SimpleChannelInboundHandler<Packet<?>> {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;handlePacket(Lnet/minecraft/network/Packet;Lnet/minecraft/network/listener/PacketListener;)V", ordinal = 0),
            method = {"channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V"})
    private void onChannelRead0(ChannelHandlerContext ctx, Packet<?> p, CallbackInfo ci) {
        if (p instanceof WorldTimeUpdateS2CPacket) {
            Delta.servertick();
        }
    }
}