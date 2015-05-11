package fiskfille.tf.common.registry;

import fiskfille.tf.TransformersMod;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class TFBlockRegistry {
    public static void registerBlock(Block block, String name, String modId) {
        String unlocalizedName = name.toLowerCase().replaceAll(" ", "_").replaceAll("'", "");

        block.setUnlocalizedName(unlocalizedName);
        block.setCreativeTab(TransformersMod.tabTransformers);

        GameRegistry.registerBlock(block, unlocalizedName);
    }

    public static void registerOre(Block block, String name, String oreDictName, String modId) {
        registerBlock(block, name, modId);
        OreDictionary.registerOre(oreDictName, block);
    }

    public static void registerOreAsTileEntity(Block block, String name, String oreDictName, Class clazz, String modId) {
        registerOre(block, name, oreDictName, modId);
        GameRegistry.registerTileEntity(clazz, name);
    }

    public static void registerTileEntity(Block block, String name, Class clazz, String modId) {
        registerBlock(block, name, modId);
        GameRegistry.registerTileEntity(clazz, name);
    }
}