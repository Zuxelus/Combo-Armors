package com.zuxelus.comboarmors.items;

import java.util.List;

import com.zuxelus.comboarmors.init.ModItems;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if (type != null)
			tooltip.add(I18n.format("info.compatable_all", I18n.format(type.name)));
	}

	public int getStackModifier(ItemStack stack) {
		if (stack.getItem() == ModItems.flightModule || stack.getItem() == ModItems.cloakingModule || stack.getItem() == ModItems.overchargeModule)
			return 16;
		return 1;
	}
}
