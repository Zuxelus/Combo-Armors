package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.utils.ArmorUtils;

import ic2.api.item.IMetalArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorExoStatic extends ItemArmorExoUtility implements IMetalArmor {
	public ItemArmorExoStatic(int renderIndex) {
		super(renderIndex, 3);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return ComboArmors.MODID + ":textures/armor/exo_static.png";
	}

	@Override
	public int getItemTier() {
		return 1;
	}

	@Override
	public boolean isMetalArmor(ItemStack stack, EntityPlayer player) {
		return true;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (ArmorUtils.doStatic(player, stack))
			player.inventoryContainer.detectAndSendChanges();
	}
}
