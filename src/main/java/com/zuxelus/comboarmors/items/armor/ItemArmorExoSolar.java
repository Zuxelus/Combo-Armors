package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorExoSolar extends ItemArmorExoUtility {

	public ItemArmorExoSolar() {
		super(EntityEquipmentSlot.HEAD);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return ComboArmors.MODID + ":textures/armor/exo_solar.png";
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public int getItemTier() {
		return 1;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (onHelmetSolarTick(player, stack))
			player.inventoryContainer.detectAndSendChanges();
	}
}
