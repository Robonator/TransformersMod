package fiskfille.tf.common.item;

import com.google.common.collect.Multimap;
import fiskfille.tf.common.motion.TFMotionManager;
import fiskfille.tf.common.playerdata.TFDataManager;
import fiskfille.tf.helper.TFHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemPurgesKatana extends ItemSword {
    public ItemPurgesKatana(ToolMaterial material) {
        super(material);
        this.setMaxDamage(1500);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int time) {
        if (!TFDataManager.isInVehicleMode(player) && TFHelper.isPlayerPurge(player)) {
            int timeLeft = this.getMaxItemUseDuration(stack) - time;
            double force = (double) timeLeft / 10;

            if (force > 2.0D) {
                force = 2.0D;
            }

            stack.damageItem(1, player);
            Vec3 vec3 = TFMotionManager.getFrontCoords(player, player.onGround ? force : force * 0.75D, true);
            player.motionX = (vec3.xCoord - player.posX);
            player.motionY = (vec3.yCoord - player.posY);
            player.motionZ = (vec3.zCoord - player.posZ);
            player.fallDistance = 0;
            player.swingItem();
        }
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (TFHelper.isPlayerPurge(player)) {
            if (!TFDataManager.isInVehicleMode(player)) {
                player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            }
        }

        return stack;
    }

    public Multimap getItemAttributeModifiers() {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", 8.0D, 0));
        return multimap;
    }
}