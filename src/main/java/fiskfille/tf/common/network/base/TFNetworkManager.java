package fiskfille.tf.common.network.base;

import fiskfille.tf.common.network.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class TFNetworkManager {
    public static SimpleNetworkWrapper networkWrapper;
    private static int packetId = 0;

    public static void registerPackets() {
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("transformersMod");

        registerPacket(MessageHandleTransformation.Handler.class, MessageHandleTransformation.class);
        registerPacket(MessageHandleStealthTransformation.Handler.class, MessageHandleStealthTransformation.class);
        registerPacket(MessagePlayerJoin.Handler.class, MessagePlayerJoin.class);
        registerPacket(MessageVehicleShoot.Handler.class, MessageVehicleShoot.class);
        registerPacket(MessageCloudtrapJetpack.Handler.class, MessageCloudtrapJetpack.class);
        registerPacket(MessageBroadcastState.Handler.class, MessageBroadcastState.class);
        registerPacket(MessageVehicleNitro.Handler.class, MessageVehicleNitro.class);
        registerPacket(MessageLaserShoot.Handler.class, MessageLaserShoot.class);
        registerPacket(MessageSendFlying.Handler.class, MessageSendFlying.class);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerPacket(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType) {
        networkWrapper.registerMessage(messageHandler, requestMessageType, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(messageHandler, requestMessageType, packetId++, Side.SERVER);
    }
}
