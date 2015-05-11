package fiskfille.tf.client.model.transformer.definition;

import fiskfille.tf.TransformersMod;
import fiskfille.tf.client.model.transformer.ModelChildBase.Biped;
import fiskfille.tf.client.model.transformer.ModelSkystrike;
import fiskfille.tf.client.model.transformer.vehicle.ModelBaseVehicle;
import fiskfille.tf.client.model.transformer.vehicle.ModelSkystrikeVehicle;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TFModelSkystrike extends TransformerModel
{
    private ModelSkystrike model;
    private ModelSkystrikeVehicle vehicle;

    public TFModelSkystrike()
    {
        this.model = new ModelSkystrike();
        this.vehicle = new ModelSkystrikeVehicle();
    }

    @Override
    public Biped getMainModel()
    {
        return model;
    }

    @Override
    public ModelBaseVehicle getVehicleModel()
    {
        return vehicle;
    }

    @Override
    public ModelRenderer getLowerArm()
    {
        return model.lowerArmR;
    }

    @Override
    public ModelRenderer getUpperArm()
    {
        return model.shoulderR;
    }

    @Override
    public ModelRenderer getBody()
    {
        return model.chest1;
    }

    @Override
    public ModelRenderer getHead()
    {
        return model.headbase;
    }

    @Override
    public void renderItem(EntityPlayer player, ItemStack stack)
    {
        GL11.glTranslatef(0.05F, 0.1F, -0.05F);
    }

    @Override
    public void renderCape(EntityPlayer player)
    {
        GL11.glTranslatef(0, 0F, 0.25F);
    }

    @Override
    public void renderFirstPersonArm(EntityPlayer player)
    {
        GL11.glTranslatef(0.2F, -0.2F, -0.0F);
    }

    @Override
    public ResourceLocation getTexture()
    {
        return new ResourceLocation(TransformersMod.modid, "textures/models/skystrike/skystrike.png");
    }
}
