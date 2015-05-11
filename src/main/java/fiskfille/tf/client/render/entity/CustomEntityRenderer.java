package fiskfille.tf.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;

public class CustomEntityRenderer extends EntityRenderer {
    private static Map<EntityPlayer, Float> offsetY = new HashMap<EntityPlayer, Float>();
    private final Minecraft mc;

    public CustomEntityRenderer(Minecraft mc) {
        super(mc, mc.getResourceManager());
        this.mc = mc;
    }

    public static void setOffsetY(EntityPlayer player, float f) {
        offsetY.put(player, f);
    }

    public static float getOffsetY(EntityPlayer entityPlayer) {
        return offsetY != null ? offsetY.get(entityPlayer) : null;
    }

    @Override
    public void updateCameraAndRender(float partialTick) {
        EntityPlayer player = mc.thePlayer;

        if (player == null || player.isPlayerSleeping()) {
            super.updateCameraAndRender(partialTick);
            return;
        }

        Float offsetForPlayer = offsetY.get(player);

        if (offsetForPlayer == null) {
            offsetForPlayer = 1.62F;
            offsetY.put(player, 1.62F);
        }

        player.renderOffsetY -= offsetForPlayer;
        super.updateCameraAndRender(partialTick);
        player.renderOffsetY = 1.62F;
    }

    @Override
    public void getMouseOver(float partialTick) {
        super.getMouseOver(partialTick);
    }
}
