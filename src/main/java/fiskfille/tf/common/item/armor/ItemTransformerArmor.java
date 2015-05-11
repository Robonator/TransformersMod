package fiskfille.tf.common.item.armor;

import fiskfille.tf.client.model.transformer.definition.TFModelRegistry;
import fiskfille.tf.common.playerdata.TFDataManager;
import fiskfille.tf.common.transformer.base.Transformer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemTransformerArmor extends ItemArmor
{
    public ItemTransformerArmor(ArmorMaterial material, int renderIndex, int armorPiece)
    {
        super(material, renderIndex, armorPiece);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return TFModelRegistry.getModel(getTransformer()).getTexture().toString();
    }

    public abstract Transformer getTransformer();

    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemstack, int armorSlot)
    {
        ModelBiped armorModel = null;

        if (itemstack != null)
        {
            armorModel = getTransformer().getModel().getMainModel();

            if (entityLiving instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entityLiving;
                ModelBiped model = getTransformer().getModel().getStealthModel();

                if (TFDataManager.getStealthModeTimer(player) != 5 && model != null && TFDataManager.isInVehicleMode(player))
                {
                    armorModel = model;
                }
            }

            if (armorModel != null)
            {
                armorModel.bipedHead.showModel = armorSlot == 0;
                armorModel.bipedHeadwear.showModel = armorSlot == 0;
                armorModel.bipedBody.showModel = armorSlot == 1;
                armorModel.bipedRightArm.showModel = armorSlot == 1;
                armorModel.bipedLeftArm.showModel = armorSlot == 1;
                armorModel.bipedRightLeg.showModel = armorSlot == 2;
                armorModel.bipedLeftLeg.showModel = armorSlot == 2;

                armorModel.isSneak = entityLiving.isSneaking();
                armorModel.isRiding = entityLiving.isRiding();
                armorModel.isChild = entityLiving.isChild();
                armorModel.heldItemRight = entityLiving.getEquipmentInSlot(0) != null ? 1 : 0;

                if (entityLiving instanceof EntityPlayer)
                {
                    ItemStack heldItem = entityLiving.getHeldItem();
                    armorModel.aimedBow = ((EntityPlayer) entityLiving).getItemInUseDuration() > 0 && heldItem != null && heldItem.getItemUseAction() == EnumAction.BOW;
                    armorModel.heldItemRight = ((EntityPlayer) entityLiving).getItemInUseDuration() > 0 && heldItem != null && heldItem.getItemUseAction() == EnumAction.BLOCK ? 3 : (entityLiving.getEquipmentInSlot(0) != null ? 1 : 0);
                }

                return armorModel;
            }
        }

        return null;
    }
}