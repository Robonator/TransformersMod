package fiskfille.tf.common.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBassCharge extends EntityThrowable
{
    public EntityBassCharge(World world)
    {
        super(world);
        this.setSize(1.0F, 1.0F);
    }

    public EntityBassCharge(World world, EntityLivingBase entity)
    {
        super(world, entity);
        this.setSize(1.0F, 1.0F);
    }

    public EntityBassCharge(World world, double x, double y, double z)
    {
        super(world, x, y, z);
        this.setSize(1.0F, 1.0F);
    }

    public void onUpdate()
    {
        super.onUpdate();

        worldObj.playSound(posX, posY, posZ, "note.bassattack", 1.0F, 0.8F, true);
        worldObj.playSound(posX, posY, posZ, "note.bass", 1.0F, 0.8F, true);

        if (ticksExisted > 6)
        {
            this.setDead();
        }
    }

    protected float getGravityVelocity()
    {
        return 0.0F;
    }

    protected float getInaccuracy()
    {
        return 3.0F;
    }

    protected void onImpact(MovingObjectPosition mop)
    {
        if (mop.entityHit != null)
        {
            float f = 2.0F;
            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), f);
            mop.entityHit.hurtResistantTime = 0;
        }
        else if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && worldObj.getBlockState(mop.getBlockPos()).getBlock().getMaterial() == Material.glass)
        {
            this.worldObj.playAuxSFX(2001, mop.getBlockPos().up(), Block.getIdFromBlock(worldObj.getBlockState(mop.getBlockPos()).getBlock())/* + (worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ) << 12)*/);
            this.worldObj.setBlockToAir(mop.getBlockPos());
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
}