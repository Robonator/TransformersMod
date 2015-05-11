package fiskfille.tf.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class TFParticles {
    private static Minecraft mc = Minecraft.getMinecraft();
    private static World theWorld = mc.theWorld;

    public static EntityFX spawnParticle(TFParticleType particleType, double x, double y, double z, float motionX, float motionY, float motionZ) {
        if (mc != null && mc.getRenderViewEntity() != null && mc.effectRenderer != null) {
            if (theWorld.isRemote) {
                int particleSetting = mc.gameSettings.particleSetting;

                if (particleSetting == 1 && theWorld.rand.nextInt(3) == 0) {
                    particleSetting = 2;
                }

                double diffX = mc.getRenderViewEntity().posX - x;
                double diffY = mc.getRenderViewEntity().posY - y;
                double diffZ = mc.getRenderViewEntity().posZ - z;

                EntityFX particle;
                double maxRenderDistance = 16.0D;

                if (diffX * diffX + diffY * diffY + diffZ * diffZ > maxRenderDistance * maxRenderDistance) {
                    return null;
                } else if (particleSetting > 1) {
                    return null;
                } else {
                    particle = new EntityTFFlameFX(theWorld, x, y, z, motionX, motionY, motionZ);

                    mc.effectRenderer.addEffect(particle);

                    return particle;
                }
            }
        }

        return null;
    }
}