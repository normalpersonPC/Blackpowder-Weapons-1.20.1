package net.normalpersonJava.blackpowderweaponsmod.entity.projectile;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import net.normalpersonJava.blackpowderweaponsmod.entity.ModEntities;
import net.normalpersonJava.blackpowderweaponsmod.init.ModSounds;
import net.normalpersonJava.blackpowderweaponsmod.platform.Services;

public class BulletEntity extends Projectile {

    private float baseDamage;
    private float piercing;
    private float knockback;
    private float armorPiercing;

    public BulletEntity(EntityType<BulletEntity> entityType, Level level) {
        super(entityType, level);

    }

    public BulletEntity(Level level, Entity owner, float baseDamage, float piercing, float knockback, float armorPiercing) {
        super(ModEntities.BULLET_ENTITY.get(), level);
        this.setOwner(owner);
        this.baseDamage = baseDamage;
        this.knockback = knockback;
        this.piercing = piercing;
        this.armorPiercing = armorPiercing;
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
        this.setDeltaMovement(this.getDeltaMovement().add(0, -0.03D, 0));

        //slow down in water
        if (this.level().getFluidState(this.blockPosition()).getType() == Fluids.WATER) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.95f, 0.95f, 0.95f));
        }
        Vec3 vec3 = this.getDeltaMovement();
        this.setPos(this.getX() + vec3.x, this.getY() + vec3.y, this.getZ() + vec3.z);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        Entity target = Services.PLATFORM.getHitEntity(entity);

        if (target instanceof LivingEntity) {
            //Knockback
            ((LivingEntity) target).knockback(this.knockback, this.getX() - target.getX(),this.getZ() - target.getZ());

            //armor piercing
            float armorValue = ((LivingEntity) target).getArmorValue();
            float armorReduction = Math.max(0, armorValue * (1 - this.armorPiercing));
            float damage = this.baseDamage - armorReduction;
            entity.hurt(damageSources().playerAttack((Player) getOwner()), damage);

            //IFrame bypass
            target.invulnerableTime = 0;


        } else {
            entity.hurt(damageSources().playerAttack((Player) getOwner()), this.baseDamage);
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
        if (blockstate.is(Blocks.GLASS) || blockstate.is(Blocks.GLASS_PANE) || blockstate.is(Blocks.ICE)) {
            this.level().destroyBlock(hitResult.getBlockPos(), true);
        } else if (!(blockstate.is(Blocks.WATER) || blockstate.is(Blocks.GLASS) || blockstate.is(Blocks.GLASS_PANE) || blockstate.is(Blocks.ICE))) {
            this.discard();
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.BULLET_HIT.get(), SoundSource.BLOCKS, 1.0f, 2.0f);
        }
    }



    @Override
    protected void defineSynchedData() {

    }
}
