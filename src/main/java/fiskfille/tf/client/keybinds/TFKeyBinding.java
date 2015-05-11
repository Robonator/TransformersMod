package fiskfille.tf.client.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TFKeyBinding extends KeyBinding
{
    public TFKeyBinding(String name, int key)
    {
        super(name, key, "Transformers");
    }
}
