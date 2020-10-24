package com.zuxelus.comboarmors.items;

import java.util.List;

import com.zuxelus.comboarmors.init.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemAssemblerUpgrade extends ItemIc2ca {

	public ItemAssemblerUpgrade(EnumRarity rarity, int stackSize) {
		super(rarity, stackSize);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean advanced) {
		list.add(I18n.format("info.compatable_all_assemblers"));
		if (this == ModItems.creativeUpgrade)
			list.add(I18n.format("info.instantly_processes_armors"));
		if (this == ModItems.overclockerUpgrade)
			list.add(I18n.format("info.reduces_processing_time"));
	}
}
