package com.zuxelus.comboarmors.items;

import java.util.List;

import com.zuxelus.comboarmors.init.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemUpgrade extends ItemIc2ca implements IItemUpgrade {
	private EnumUpgradeType type;

	public ItemUpgrade(EnumRarity rarity, EnumUpgradeType type) {
		this(rarity, 64, type);
	}

	public ItemUpgrade(EnumRarity rarity, int stackSize, EnumUpgradeType type) {
		super(rarity, stackSize);
		this.type = type;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean advanced) {
		if (type != null)
			list.add(I18n.format("info.compatable_all", I18n.format(type.name)));
	}

	public int getStackModifier(ItemStack stack) {
		if (stack.getItem() == ModItems.flightModule || stack.getItem() == ModItems.cloakingModule || stack.getItem() == ModItems.overchargeModule)
			return 16;
		return 1;
	}
}
