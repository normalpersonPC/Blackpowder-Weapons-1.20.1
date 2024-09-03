package net.normalpersonJava.blackpowderweaponsmod.entity;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import net.normalpersonJava.blackpowderweaponsmod.init.ModDamageSources;
import net.normalpersonJava.blackpowderweaponsmod.init.ModEntities;
import net.normalpersonJava.blackpowderweaponsmod.platform.Services;

import javax.annotation.Nullable;

public class BulletEntity extends Projectile {

    private Vec3 intialpos;
    private float damage;
    private float piercing;
    private float knockback;

    public BulletEntity(EntityType<BulletEntity> entityType, Level level) {
        super(entityType, level);

    }

    public BulletEntity(Level level, Entity owner,  Vec3 pos, float damage, float piercing, float knockback) {
        super(ModEntities.BULLET_ENTITY.get(), level);
        this.setOwner(owner);
        this.intialpos = pos;
        this.damage = damage;
        this.knockback = knockback;
        this.piercing = piercing;
    }

    public BulletEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        super(ModEntities.BULLET_ENTITY.get(), level);
    }

    @Override
    public void tick() {
        super.tick();

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !Services.PLATFORM.bulletHitResult(this, hitresult)) {
            this.onHit(hitresult);
            this.hasImpulse = true;
        }

        this.checkInsideBlocks();
        this.setDeltaMovement(this.getDeltaMovement().add(0, -0.05D, 0));
        Vec3 vec3 = this.getDeltaMovement();
        this.setPos(this.getX() + vec3.x, this.getY() + vec3.y, this.getZ() + vec3.z);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        Entity target = Services.PLATFORM.getHitEntity(entity);

        entity.hurt(damageSources().generic(), this.damage);

        if (target instanceof LivingEntity) {
            ((LivingEntity) target).knockback(this.knockback, this.getX() - target.getX(),this.getZ() - target.getZ());
            target.invulnerableTime = 0;
        }

        if (this.piercing > 0) {
            this.piercing--;
        } else {
            this.discard();
        }
    }


    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        BlockState blockstate = this.level().getBlockState(hitResult.getBlockPos());
        if (blockstate.is(Blocks.GLASS) || blockstate.is(Blocks.GLASS_PANE)) {
            this.level().destroyBlock(hitResult.getBlockPos(), true);
        }
        this.discard();
    }



    @Override
    protected void defineSynchedData() {

    }
}
