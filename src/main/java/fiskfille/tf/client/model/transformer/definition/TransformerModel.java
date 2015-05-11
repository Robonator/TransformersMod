package fiskfille.tf.client.model.transformer.definition;

import fiskfille.tf.client.model.transformer.ModelChildBase.Biped;
import fiskfille.tf.client.model.transformer.vehicle.ModelBaseVehicle;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class TransformerModel
{
    public abstract Biped getMainModel();

    public abstract ModelBaseVehicle getVehicleModel();

    public abstract ModelRenderer getLowerArm();

    public abstract ModelRenderer getUpperArm();

    public abstract ModelRenderer getBody();

    public abstract ModelRenderer getHead();

    public abstract ResourceLocation getTexture();

    public Biped getStealthModel()
    {
        return null;
    }

    public void renderItem(EntityPlayer player, ItemStack stack)
    {
    }

    public void renderCape(EntityPlayer player)
    {
    }

    public void renderFirstPersonArm(EntityPlayer player)
    {
    }
}
