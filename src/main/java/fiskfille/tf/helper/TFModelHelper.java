package fiskfille.tf.helper;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gegy1000
 */
@SideOnly(Side.CLIENT)
public class TFModelHelper
{
    /**
     * The main client player's model.
     */
    public static ModelBiped modelBipedMain;

    private static Map<EntityPlayer, ModelOffset> offsets = new HashMap<EntityPlayer, ModelOffset>();

    /**
     * @returns the model offsets for the specified player.
     */
    public static ModelOffset getOffsets(EntityPlayer player)
    {
        ModelOffset modelOffset = offsets.get(player);

        if (modelOffset == null)
        {
            modelOffset = new ModelOffset();
            offsets.put(player, modelOffset);
        }

        return modelOffset;
    }
}
