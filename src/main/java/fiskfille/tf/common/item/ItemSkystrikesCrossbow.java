package fiskfille.tf.common.item;

import fiskfille.tf.common.entity.EntityLaserBeam;
import fiskfille.tf.helper.TFHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemSkystrikesCrossbow extends ItemSword
{
    public ItemSkystrikesCrossbow(ToolMaterial material)
    {
        super(material);
        this.setMaxDamage(1500);
    }

    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean b)
    {
        if (!itemstack.hasTagCompound())
        {
            itemstack.setTagCompound(new NBTTagCompound());
            itemstack.getTagCompound().setBoolean("blueMode", false);
        }
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int time)
    {
        if (TFHelper.isPlayerSkystrike(player) && !world.isRemote && (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode))
        {
            boolean blue = isBlue(stack);
            stack.getTagCompound().setBoolean("blueMode", !blue);

            stack.damageItem(1, player);

            if (!player.capabilities.isCreativeMode)
            {
                player.inventory.consumeInventoryItem(TFItems.energonCrystalPiece);
            }
        }
    }

    private boolean isBlue(ItemStack stack)
    {
        return stack.hasTagCompound() && stack.getTagCompound().getBoolean("blueMode");
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
        int duration = this.getMaxItemUseDuration(stack) - count;

        if (stack.hasTagCompound() && (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode))
        {
            World world = player.worldObj;

            if (!world.isRemote)
            {
                if (duration > 1 && duration < 80)
                {
                    boolean blue = isBlue(stack);
                    EntityLaserBeam entity = new EntityLaserBeam(world, player, blue);
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
        if (TFHelper.isPlayerSkystrike(player) && (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode))
        {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }

        return stack;
    }
}