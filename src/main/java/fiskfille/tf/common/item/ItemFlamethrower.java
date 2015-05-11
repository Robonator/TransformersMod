package fiskfille.tf.common.item;

import fiskfille.tf.common.entity.EntityFlamethrowerFire;
import fiskfille.tf.common.motion.TFMotionManager;
import fiskfille.tf.helper.TFHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemFlamethrower extends ItemSword
{
    public ItemFlamethrower(ToolMaterial material)
    {
        super(material);
        this.setMaxDamage(1500);
        this.setCreativeTab(null);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int time)
    {
        if (TFHelper.isPlayerCloudtrap(player) && !world.isRemote && (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode))
        {
            stack.damageItem(1, player);

            if (!player.capabilities.isCreativeMode)
            {
                player.inventory.consumeInventoryItem(TFItems.energonCrystalPiece);
            }
        }
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
        int duration = this.getMaxItemUseDuration(stack) - count;

        if (duration < 100)
        {
            if (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode)
            {
                World world = player.worldObj;

                if (duration % 5 == 0)
                {
                    Vec3 backCoords = TFMotionManager.getFrontCoords(player, -0.3F, true);
                    player.motionX = (backCoords.xCoord - player.posX);
                    player.motionZ = (backCoords.zCoord - player.posZ);

                    world.playAuxSFX(1009, player.getPosition(), 0);
                }

                if (!world.isRemote)
                {
                    EntityFlamethrowerFire entity = new EntityFlamethrowerFire(world, player);
                    world.spawnEntityInWorld(entity);
                }
            }
        }
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (TFHelper.isPlayerCloudtrap(player) && (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode))
        {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }

        return stack;
    }
}