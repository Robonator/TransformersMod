package fiskfille.tf.client.render.entity.player;

import fiskfille.tf.client.model.player.ModelBipedTF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCustomPlayer extends RenderPlayer {
    public RenderCustomPlayer() {
        super(Minecraft.getMinecraft().getRenderManager());
        this.mainModel = new ModelBipedTF();
    }
}