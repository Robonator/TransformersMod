package fiskfille.tf.common.transformer;

import fiskfille.tf.common.item.TFItems;
import fiskfille.tf.common.transformer.base.TransformerJet;
import fiskfille.tf.common.transformer.cloudtrap.CloudtrapJetpackManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

/**
 * @author gegy1000
 */
public class TransformerCloudtrap extends TransformerJet
{
    public TransformerCloudtrap()
    {
        super("Cloudtrap");
    }

    @Override
    public Item getHelmet()
    {
        return TFItems.cloudtrapHelmet;
    }

    @Override
    public Item getChestplate()
    {
        return TFItems.cloudtrapChestplate;
    }

    @Override
    public Item getLeggings()
    {
        return TFItems.cloudtrapLeggings;
    }

    @Override
    public Item getBoots()
    {
        return TFItems.cloudtrapBoots;
    }

    @Override
    public void tick(EntityPlayer player, int timer)
    {
        if (timer > 10)
        {
            if (player.worldObj.isRemote)
            {
                CloudtrapJetpackManager.cloudtrapTick(player);
            }
        }
    }
}