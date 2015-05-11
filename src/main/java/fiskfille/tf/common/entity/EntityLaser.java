package fiskfille.tf.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityLaser extends EntityThrowable
{
    public EntityLaser(World world)
    {
        super(world);
    }

    public EntityLaser(World world, EntityLivingBase entity)
    {
        super(world, entity);
    }

    public EntityLaser(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public void onUpdate()
    {
        super.onUpdate();
    }

    protected float getGravityVelocity()
    {
        return 0.005F;
    }

    protected float getInaccuracy()
    {
        return 4F;
    }

    protected void onImpact(MovingObjectPosition mop)
    {
        if (!worldObj.isRemote)
        {
            if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                setFire(mop.getBlockPos(), mop.sideHit.getIndex());
            }
            else if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY)
            {
                Entity entityHit = mop.entityHit;

                entityHit.setFire(10);
                entityHit.attackEntityFrom(DamageSource.inFire, 10F);
                entityHit.hurtResistantTime = 0;
                entityHit.motionY -= 0.2F;
            }
        }

        setDead();
    }

    public void setFire(BlockPos pos, int sideHit)
    {
        if (sideHit == 0)
        {
            pos.down();
        }

        if (sideHit == 1)
        {
            pos.up();
        }

        if (sideHit == 2)
        {
            pos.north();
        }

        if (sideHit == 3)
        {
            pos.south();
        }

        if (sideHit == 4)
        {
            pos.west();
        }

        if (sideHit == 5)
        {
            pos.east();
        }

        worldObj.setBlockState(pos, Blocks.fire.getDefaultState());
    }
}