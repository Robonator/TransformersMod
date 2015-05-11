package fiskfille.tf.common.block;

import net.minecraft.block.material.Material;

public class BlockTransformiumStone extends BlockBasic
{
    public BlockTransformiumStone()
    {
        super(Material.rock);
        this.setHarvestLvl("pickaxe", 2);
        this.setHardness(2.5F);
        this.setResistance(10.0F);
    }
}