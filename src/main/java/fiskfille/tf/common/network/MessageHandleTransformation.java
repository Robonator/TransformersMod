package fiskfille.tf.common.network;

import fiskfille.tf.TransformersMod;
import fiskfille.tf.common.playerdata.TFDataManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandleTransformation implements IMessage {
    public int id;
    private boolean transformed;

    public MessageHandleTransformation() {

    }

    public MessageHandleTransformation(EntityPlayer player, boolean mode) {
        id = player.getEntityId();
        transformed = mode;
    }

    public void fromBytes(ByteBuf buf) {
        id = buf.readInt();
        transformed = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
        buf.writeBoolean(transformed);
    }

    public static class Handler implements IMessageHandler<MessageHandleTransformation, IMessage> {
        public IMessage onMessage(MessageHandleTransformation message, MessageContext ctx) {
            boolean isTransformed = message.transformed;

            if (ctx.side.isClient()) {
                EntityPlayer player = TransformersMod.proxy.getPlayer();
                EntityPlayer from = null;
                Entity entity = player.worldObj.getEntityByID(message.id);

                if (entity instanceof EntityPlayer)
                    from = (EntityPlayer) entity;

                if (from != null && from != player) {
                    TFDataManager.setInVehicleModeWithoutNotify(from, isTransformed);
                    TFDataManager.setTransformationTimer(from, isTransformed ? 20 : 0);

                    String suffix = isTransformed ? "vehicle" : "robot";
                    from.worldObj.playSound(from.posX, from.posY - (double) from.renderOffsetY, from.posZ, TransformersMod.modid + ":transform_" + suffix, 1, 1, false);
                }
            } else {
                EntityPlayer player = ctx.getServerHandler().playerEntity;

                if (player != null) {
                    TFDataManager.setInVehicleMode(player, isTransformed);
                }
            }

            return null;
        }
    }
}