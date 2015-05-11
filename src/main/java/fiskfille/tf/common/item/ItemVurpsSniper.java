package fiskfille.tf.common.item;

import fiskfille.tf.common.network.MessageLaserShoot;
import fiskfille.tf.common.network.base.TFNetworkManager;
import fiskfille.tf.common.playerdata.TFDataManager;
import fiskfille.tf.helper.TFHelper;
import fiskfille.tf.helper.TFShootManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemVurpsSniper extends ItemSword
{
    public ItemVurpsSniper(ToolMaterial material)
    {
        super(material);
        this.setMaxDamage(800);
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {

        if (TFHelper.isPlayerVurp(player) && !TFDataManager.isInVehicleMode(player))
        {
            if (world.isRemote)
            {
                if (!TFShootManager.laserFilling && TFShootManager.laserCharge > 0)
                {
                    TFShootManager.laserCharge -= 5;
                    player.playSound("random.fizz", 1, 2F);
                    TFNetworkManager.networkWrapper.sendToServer(new MessageLaserShoot(player, false));
                }
                else
                {
                    if (!TFShootManager.laserFilling && (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode))
                    {
                        stack.damageItem(1, player);
                        TFNetworkManager.networkWrapper.sendToServer(new MessageLaserShoot(player, true));
                        TFShootManager.laserFilling = true;
                    }
                }
            }
        }

        return stack;
    }
}