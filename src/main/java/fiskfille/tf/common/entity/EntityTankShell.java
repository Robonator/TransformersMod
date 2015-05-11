package fiskfille.tf.common.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityTankShell extends EntityThrowable implements IEntityAdditionalSpawnData
{
    public boolean allowExplosions;

    public EntityTankShell(World world)
    {
        super(world);
    }

    public EntityTankShell(World world, EntityLivingBase entity, boolean explosions)
    {
        super(world, entity);
        allowExplosions = explosions;
    }

    public EntityTankShell(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public void onUpdate()
    {
        super.onUpdate();
    }

    protected float getGravityVelocity()
    {
        return 0.04F;
    }

    protected float getInaccuracy()
    {
        return 4.0F;
    }

    protected void onImpact(MovingObjectPosition mop)
    {
        if (!worldObj.isRemote)
        {
            if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                explode(mop.getBlockPos(), mop.sideHit.getIndex());
            }
            else if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY)
            {
                worldObj.createExplosion(null, mop.entityHit.posX, mop.entityHit.posY, mop.entityHit.posZ, 1.0F, allowExplosions);
            }
        }

        setDead();
    }

    public void explode(BlockPos pos, int sideHit)
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

        worldObj.createExplosion(null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 4, allowExplosions);
    }

    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Explosions", allowExplosions);
    }

    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        allowExplosions = nbt.getBoolean("Explosions");
    }

    @Override
    public void writeSpawnData(ByteBuf buf)
    {
        buf.writeBoolean(allowExplosions);
    }

    @Override
    public void readSpawnData(ByteBuf buf)
    {
        allowExplosions = buf.readBoolean();
    }
}