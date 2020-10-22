package com.zuxelus.comboarmors.utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class InvisiblePotionEffect extends PotionEffect {

	public InvisiblePotionEffect() {
		super(Potion.invisibility.id, 1, 0);
	}

	@Override
	public boolean onUpdate(EntityLivingBase entity) {
		return true;
	}
}
