package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.utils.ArmorUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IMetalArmor;
import ic2.core.IC2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class ItemBootsStaticNano extends ItemArmorElectricUtility implements IMetalArmor {
	public ItemBootsStaticNano(int renderIndex) {
		super(renderIndex, 3, 1000000, 1000, 3, false);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return ComboArmors.MODID + ":textures/armor/nano_static.png";
	}

	@Override
	public double getDamageAbsorptionRatio() {
		return 0.9D;
	}

	@Override
	public int getEnergyPerDamage() {
		return 5000;
	}

	@Override
	public int getItemTier() {
		return 3;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase entity, ItemStack armor, DamageSource source, double damage, int slot) {
		if (source == DamageSource.fall) {
			int energyPerDamage = getEnergyPerDamage();
			int damageLimit = Integer.MAX_VALUE;
			if (energyPerDamage > 0)
				damageLimit = (int) Math.min(damageLimit, 25.0D * ElectricItem.manager.getCharge(armor) / energyPerDamage);
			return new ArmorProperties(10, damage < 8.0D ? 1.0D : 0.875D, damageLimit);
		}
		return super.getProperties(entity, armor, source, damage, slot);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}

	@Override
	public boolean isMetalArmor(ItemStack stack1, EntityPlayer stack2) {
		return true;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (ArmorUtils.doStatic(player, itemStack))
			player.inventoryContainer.detectAndSendChanges();
	}

	@SubscribeEvent
	public void onEntityLivingFallEvent(LivingFallEvent event) {
		onFall(event, false);
	}
}
