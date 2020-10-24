package com.zuxelus.comboarmors.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityLaser extends EntityThrowable {

	public EntityLaser(World world) {
		super(world);
	}

	public EntityLaser(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityLaser(World world, EntityLiving entity) {
		super(world, entity);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.entityHit != null) {
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 1);
			result.entityHit.setFire(100);
		}
		for (int i = 0; i < 8; ++i)
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		if (!world.isRemote)
			setDead();
	}
}
