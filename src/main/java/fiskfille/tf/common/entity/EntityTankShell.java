package fiskfille.tf.common.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

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
    
    protected float func_70182_d()
    {
        return 4.0F;
    }
    
    protected void onImpact(MovingObjectPosition mop)
    {
        if (!worldObj.isRemote)
        {
            if (mop.typeOfHit == mop.typeOfHit.BLOCK)
            {
                explode(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit);
            }
            else if (mop.typeOfHit == mop.typeOfHit.ENTITY)
            {
                worldObj.createExplosion(null, mop.entityHit.posX, mop.entityHit.posY, mop.entityHit.posZ, 1.0F, allowExplosions);
            }
        }
        
        setDead();
    }
    
    public void explode(int x, int y, int z, int sideHit)
    {
        if (sideHit == 0)
        {
            --y;
        }
        else if (sideHit == 1)
        {
            ++y;
        }
        else if (sideHit == 2)
        {
            --z;
        }
        else if (sideHit == 3)
        {
            ++z;
        }
        else if (sideHit == 4)
        {
            --x;
        }
        else if (sideHit == 5)
        {
            ++x;
        }
        
        worldObj.createExplosion(null, x + 0.5F, y + 0.5F, z + 0.5F, 1.0F, allowExplosions);
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