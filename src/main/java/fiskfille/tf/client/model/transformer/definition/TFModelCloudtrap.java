package fiskfille.tf.client.model.transformer.definition;

import fiskfille.tf.TransformersMod;
import fiskfille.tf.client.model.transformer.ModelChildBase.Biped;
import fiskfille.tf.client.model.transformer.ModelCloudtrap;
import fiskfille.tf.client.model.transformer.vehicle.ModelBaseVehicle;
import fiskfille.tf.client.model.transformer.vehicle.ModelCloudtrapVehicle;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TFModelCloudtrap extends TransformerModel {
    private ModelCloudtrap model;
    private ModelCloudtrapVehicle vehicle;

    public TFModelCloudtrap() {
        this.model = new ModelCloudtrap();
        this.vehicle = new ModelCloudtrapVehicle();
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
        return null;
    }

    @Override
    public ModelRenderer getUpperArm() {
        return null;
    }

    @Override
    public ModelRenderer getBody() {
        return null;
    }

    @Override
    public ModelRenderer getHead() {
        return null;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(TransformersMod.modid, "textures/models/cloudtrap/cloudtrap.png");
    }
}
