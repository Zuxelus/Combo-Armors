package com.zuxelus.comboarmors.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
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
	protected void onImpact(MovingObjectPosition position) {
		if (position.entityHit != null) {
			position.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 1);
			position.entityHit.setFire(100);
		}
		for (int i = 0; i < 8; ++i)
			worldObj.spawnParticle("largesmoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		if (!worldObj.isRemote)
			setDead();
	}
}
