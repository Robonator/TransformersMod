package fiskfille.tf.common.proxy;

import fiskfille.tf.client.tick.ClientTickHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy {
    public ClientTickHandler tickHandler;

    public World getWorld() {
        return null;
    }

    public EntityPlayer getPlayer() {
        return null;
    }

    public void registerRenderInformation() {

    }

    public void registerKeyBinds() {

    }

    public void registerTickHandlers() {

    }
}