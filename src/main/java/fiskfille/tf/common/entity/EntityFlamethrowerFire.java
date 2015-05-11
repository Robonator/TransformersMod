package fiskfille.tf.common.entity;

import fiskfille.tf.client.particle.TFParticleType;
import fiskfille.tf.client.particle.TFParticles;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityFlamethrowerFire extends EntityThrowable {
    public EntityFlamethrowerFire(World world) {
        super(world);
    }

    public EntityFlamethrowerFire(World world, EntityLivingBase entity) {
        super(world, entity);
    }

    public EntityFlamethrowerFire(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected float getGravityVelocity() {
        return 0.0F;
    }

    protected float getInaccuracy() {
        return 1.0F;
    }

    public void onUpdate() {
        super.onUpdate();

        if (this.isEntityAlive()) {
            if (worldObj.isRemote) {
                for (int i = 0; i < 5; ++i) {
                    float f = (rand.nextFloat() / 5);

                    TFParticles.spawnParticle(TFParticleType.FLAMETHROWER_FLAME, posX + f, posY + 0.15F + f, posZ + f, 0, 0, 0);
                }
            }

            if (ticksExisted > 7) {
                this.setDead();
            }
        }
    }

    protected void onImpact(MovingObjectPosition mop) {
        if (mop.entityHit != null) {
            mop.entityHit.setFire(20);

            if (getThrower() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) getThrower();
                mop.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(player), 10.0F);
            }
        }

        setFire(worldObj, mop.getBlockPos(), mop.sideHit.getIndex());
        this.setDead();
    }

    public boolean setFire(World world, BlockPos pos, int sideHit) {
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

        if (world.isAirBlock(pos)) {
            world.setBlockState(pos, Blocks.fire.getDefaultState());
        }

        return true;
    }
}