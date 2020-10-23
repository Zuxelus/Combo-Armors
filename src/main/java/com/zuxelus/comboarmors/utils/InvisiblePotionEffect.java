package com.zuxelus.comboarmors.utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class InvisiblePotionEffect extends PotionEffect {

	public InvisiblePotionEffect() {
		super(MobEffects.INVISIBILITY);
	}

	@Override
	public boolean onUpdate(EntityLivingBase entity) {
		return true;
	}
}
