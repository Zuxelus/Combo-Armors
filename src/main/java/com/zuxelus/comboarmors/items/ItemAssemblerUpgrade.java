package com.zuxelus.comboarmors.items;

import java.util.List;

import com.zuxelus.comboarmors.init.ModItems;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAssemblerUpgrade extends ItemIc2ca {

	public ItemAssemblerUpgrade(EnumRarity rarity, int stackSize) {
		super(rarity, stackSize);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format("info.compatable_all_assemblers"));
		if (this == ModItems.creativeUpgrade)
			tooltip.add(I18n.format("info.instantly_processes_armors"));
		if (this == ModItems.overclockerUpgrade)
			tooltip.add(I18n.format("info.reduces_processing_time"));
	}
}
