package fiskfille.tf.common.event;

import fiskfille.tf.common.transformer.base.Transformer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PlayerTransformEvent extends PlayerEvent {
    public final boolean transformed;
    public final boolean stealthForce;
    public final Transformer transformer;

    public PlayerTransformEvent(EntityPlayer player, Transformer transformer, boolean transformed, boolean stealthForce) {
        super(player);
        this.transformed = transformed;
        this.stealthForce = stealthForce;
        this.transformer = transformer;
    }
}
