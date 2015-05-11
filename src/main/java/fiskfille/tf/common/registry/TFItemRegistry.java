package fiskfille.tf.common.registry;

import fiskfille.tf.TransformersMod;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class TFItemRegistry {
    public static void registerItem(Item item, String name, String modId) {
        item.setCreativeTab(TransformersMod.tabTransformers);

        registerItemNoTab(item, name, modId);
    }

    public static void registerIngot(Item item, String name, String modId, String oreDictName) {
        registerItem(item, name, modId);
        OreDictionary.registerOre(oreDictName, item);
    }

    public static void registerItemNoTab(Item item, String name, String modId) {
        String unlocalizedName = name.toLowerCase().replaceAll(" ", "_").replaceAll("'", "");

        item.setUnlocalizedName(unlocalizedName);

        GameRegistry.registerItem(item, unlocalizedName);
    }
}