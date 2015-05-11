package fiskfille.tf.common.block;

import fiskfille.tf.common.item.TFItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockEnergonCube extends BlockBasic //BlockIce
{
    public BlockEnergonCube() {
        super(Material.glass);
        this.setHarvestLvl("pickaxe", 1);
        this.setStepSound(Block.soundTypeGlass);
        this.setHardness(6.0F);
        this.setResistance(10.0F);
        this.setLightLevel(0.75F);
    }

    protected boolean canSilkHarvest() {
        return true;
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return shouldRenderSide(world, x, y, z, 1 - side);
    }

    public int quantityDropped(Random random) {
        return random.nextInt(1) + 9;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    public Item getItemDropped(int p_149650_1_, Random random, int p_149650_3_) {
        return TFItems.energonCrystalPiece;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderSide(IBlockAccess world, int x, int y, int z, int side) {
        Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

        return block != this && super.shouldSideBeRendered(world, new BlockPos(x, y, z), EnumFacing.values()[side]);
    }
}
