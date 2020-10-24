package com.zuxelus.comboarmors.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityTechArrow extends EntityTippedArrow {
	private boolean explosive = false;
	private float explosionPower = 3.0f;

	public EntityTechArrow(World world, EntityLivingBase shooter) {
		super(world, shooter);
		setPotionEffect(new ItemStack(Items.ARROW));
	}

	public void setExplosive(boolean value) {
		explosive = value;
	}

	public void setExplosionPower(float value) {
		explosionPower = value;
	}

	@Override
	protected ItemStack getArrowStack() {
		return ItemStack.EMPTY;
	}

	@Override
	protected void doBlockCollisions() {
		super.doBlockCollisions();

		if ((isDead || arrowShake > 0) && explosive) {
			world.newExplosion(null, posX, posY, posZ, explosionPower, false, false);
			setDead();
		}
	}
}