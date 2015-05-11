package fiskfille.tf.common.entity;

import fiskfille.tf.common.achievement.TFAchievements;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityMissile extends EntityThrowable implements IEntityAdditionalSpawnData {
    public boolean isInStealthMode;
    public boolean allowExplosions;

    public EntityMissile(World world) {
        super(world);
    }

    public EntityMissile(World world, EntityLivingBase entity, boolean explosions, boolean stealthMode) {
        super(world, entity);
        isInStealthMode = stealthMode;
        allowExplosions = explosions;
        setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0F);
    }

    public EntityMissile(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public void onUpdate() {
        super.onUpdate();

        if (ticksExisted < 5 && !isInStealthMode) {
            posY -= 0.2F;
        }

        for (int i = 0; i < 20; ++i) {
            float spread = (rand.nextFloat() - 0.5F) / 4;
            worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + spread, posY + spread, posZ + spread, 0, 0, 0);
        }
    }

    protected float getGravityVelocity() {
        return isInStealthMode ? 0.05F : 0.005F;
    }

    protected float func_70182_d() {
        return isInStealthMode ? 2.0F : 4.0F;
    }

    protected void onImpact(MovingObjectPosition mop) {
        if (!worldObj.isRemote) {
            if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                explode(mop.getBlockPos(), mop.sideHit.getIndex());
            } else if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                worldObj.createExplosion(null, mop.entityHit.posX, mop.entityHit.posY, mop.entityHit.posZ, 4, allowExplosions);

                if (mop.entityHit instanceof EntityBat && getThrower() instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) getThrower();

                    if (player.getDistanceSqToEntity(mop.entityHit) >= 25.0D) {
                        player.addStat(TFAchievements.sharpshooter, 1);
                    }
                }
            }
        }

        setDead();
    }

    public void explode(BlockPos pos, int sideHit) {
        if (sideHit == 0) {
            pos.down();
        }

        if (sideHit == 1) {
            pos.up();
        }

        if (sideHit == 2) {
            pos.north();
        }

        if (sideHit == 3) {
            pos.south();
        }

        if (sideHit == 4) {
            pos.west();
        }

        if (sideHit == 5) {
            pos.east();
        }

        worldObj.createExplosion(null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 4, allowExplosions);
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Explosions", allowExplosions);
        nbt.setBoolean("StealthForce", isInStealthMode);
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        allowExplosions = nbt.getBoolean("Explosions");
        isInStealthMode = nbt.getBoolean("StealthForce");
    }

    @Override
    public void writeSpawnData(ByteBuf buf) {
        buf.writeBoolean(allowExplosions);
        buf.writeBoolean(isInStealthMode);
    }

    @Override
    public void readSpawnData(ByteBuf buf) {
        allowExplosions = buf.readBoolean();
        isInStealthMode = buf.readBoolean();
    }
}