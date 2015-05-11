package fiskfille.tf.common.block;

import fiskfille.tf.common.item.ItemMiniVehicle;
import fiskfille.tf.common.tileentity.TileEntityDisplayPillar;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDisplayPillar extends BlockBasic implements ITileEntityProvider
{
    private Random rand = new Random();

    public BlockDisplayPillar()
    {
        super(Material.rock);
        this.setHardness(0.5F);
        this.setResistance(1.0F);
    }

    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntityDisplayPillar tileEntityDisplayPillar = (TileEntityDisplayPillar) world.getTileEntity(pos);
        ItemStack itemstack = tileEntityDisplayPillar.getDisplayItem();

        if (itemstack != null)
        {
            float f = this.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

            while (itemstack.stackSize > 0)
            {
                int j1 = this.rand.nextInt(21) + 10;

                if (j1 > itemstack.stackSize)
                {
                    j1 = itemstack.stackSize;
                }

                itemstack.stackSize -= j1;

                EntityItem entityitem = new EntityItem(world, (double) ((float) pos.getX() + f), (double) ((float) pos.getY() + f1), (double) ((float) pos.getZ() + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                if (itemstack.hasTagCompound())
                {
                    entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                }

                float f3 = 0.05F;

                entityitem.motionX = (double) ((float) this.rand.nextGaussian() * f3);
                entityitem.motionY = (double) ((float) this.rand.nextGaussian() * f3 + 0.2F);
                entityitem.motionZ = (double) ((float) this.rand.nextGaussian() * f3);

                world.spawnEntityInWorld(entityitem);
            }
        }

        super.breakBlock(world, pos, state);
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntityDisplayPillar tileEntityDisplayPillar = (TileEntityDisplayPillar) world.getTileEntity(pos);

        if (tileEntityDisplayPillar != null)
        {
            ItemStack heldItem = player.getHeldItem();

            ItemStack displayItem = tileEntityDisplayPillar.getDisplayItem();

            if (heldItem == null && displayItem != null)
            {
                player.setCurrentItemOrArmor(0, displayItem);

                tileEntityDisplayPillar.setDisplayItem(null, true);
            }
            else if (heldItem != null && heldItem.getItem() instanceof ItemMiniVehicle)
            {
                if (displayItem == null)
                {
                    tileEntityDisplayPillar.setDisplayItem(heldItem, true);

                    //if (!player.capabilities.isCreativeMode)
                    {
                        player.setCurrentItemOrArmor(0, null);
                    }
                }
            }
        }

        return false;
    }

    public MovingObjectPosition collisionRayTrace(World world, BlockPos pos, Vec3 start, Vec3 end)
    {
        TileEntityDisplayPillar tileEntityDisplayPillar = (TileEntityDisplayPillar) world.getTileEntity(pos);

        if (tileEntityDisplayPillar != null)
        {
            ItemStack displayItem = tileEntityDisplayPillar.getDisplayItem();

            if (displayItem != null)
            {
                float f = 0.21F;
                int metadata = displayItem.getItemDamage();

                if (metadata == 0 || metadata == 1 || metadata == 4)
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
                else if (metadata == 2 || metadata == 3)
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                }
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.55F, 1.0F);
            }
        }

        return super.collisionRayTrace(world, pos, start, end);
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return -1;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityDisplayPillar();
    }
}