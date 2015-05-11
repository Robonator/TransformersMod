package fiskfille.tf.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderBassCharge extends Render
{
    public RenderBassCharge()
    {
        super(Minecraft.getMinecraft().getRenderManager());
    }

    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_)
    {
        float scale = 0.5F;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(scale, scale, scale);
        this.bindEntityTexture(entity);
        WorldRenderer tessellator = Tessellator.getInstance().getWorldRenderer();

        this.renderIIcon(tessellator);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return TextureMap.LOCATION_MISSING_TEXTURE;
    }

    private void renderIIcon(WorldRenderer p_77026_1_)
    {
        float f = 0f;
        float f1 = 1f;
        float f2 = 0f;
        float f3 = 1f;
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        p_77026_1_.startDrawingQuads();
        p_77026_1_.setNormal(0.0F, 1.0F, 0.0F);
        p_77026_1_.addVertexWithUV((double) (0.0F - f5), (double) (0.0F - f6), 0.0D, (double) f, (double) f3);
        p_77026_1_.addVertexWithUV((double) (f4 - f5), (double) (0.0F - f6), 0.0D, (double) f1, (double) f3);
        p_77026_1_.addVertexWithUV((double) (f4 - f5), (double) (f4 - f6), 0.0D, (double) f1, (double) f2);
        p_77026_1_.addVertexWithUV((double) (0.0F - f5), (double) (f4 - f6), 0.0D, (double) f, (double) f2);
        p_77026_1_.finishDrawing();
    }
}