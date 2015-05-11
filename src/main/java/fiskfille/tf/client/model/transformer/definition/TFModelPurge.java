package fiskfille.tf.client.model.transformer.definition;

import fiskfille.tf.TransformersMod;
import fiskfille.tf.client.model.transformer.ModelChildBase.Biped;
import fiskfille.tf.client.model.transformer.ModelPurge;
import fiskfille.tf.client.model.transformer.vehicle.ModelBaseVehicle;
import fiskfille.tf.client.model.transformer.vehicle.ModelPurgeVehicle;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TFModelPurge extends TransformerModel {
    private ModelPurge model;
    private ModelPurgeVehicle vehicle;

    public TFModelPurge() {
        this.model = new ModelPurge();
        this.vehicle = new ModelPurgeVehicle();
    }

    @Override
    public Biped getMainModel() {
        return model;
    }

    @Override
    public ModelBaseVehicle getVehicleModel() {
        return vehicle;
    }

    @Override
    public ModelRenderer getLowerArm() {
        return model.lowerArm1;
    }

    @Override
    public ModelRenderer getUpperArm() {
        return model.upperArmR;
    }

    @Override
    public ModelRenderer getBody() {
        return model.chest;
    }

    @Override
    public ModelRenderer getHead() {
        return model.head;
    }

    @Override
    public void renderItem(EntityPlayer player, ItemStack stack) {
        GL11.glTranslatef(0.05F, -0F, 0.1F);
    }

    @Override
    public void renderCape(EntityPlayer player) {
        GL11.glTranslatef(0, -0.2F, 0.1F);
    }

    @Override
    public void renderFirstPersonArm(EntityPlayer player) {
        GL11.glTranslatef(0, -0.3F, 0.1F);
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(TransformersMod.modid, "textures/models/purge/purge.png");
    }
}
