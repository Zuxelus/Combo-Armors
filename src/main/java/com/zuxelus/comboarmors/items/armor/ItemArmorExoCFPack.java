package com.zuxelus.comboarmors.items.armor;

import java.util.List;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.init.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemArmorExoCFPack extends ItemArmorTankUtility {

	public ItemArmorExoCFPack(int renderIndex) {
		super(renderIndex, 1, BlocksItems.getFluid(InternalName.fluidConstructionFoam), 80000);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return ComboArmors.MODID + ":textures/armor/exo_cfpack.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced) {
		info.add(I18n.format("info.upgrade_module_installed"));
		super.addInformation(stack, player, info, advanced);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		ItemStack stack = new ItemStack(ModItems.exoCFPack, 1);
		fillTank(stack);
		stack.setItemDamage(1);
		list.add(stack);

		stack = new ItemStack(ModItems.exoCFPack, 1);
		stack.setItemDamage(getMaxDamage());
		list.add(stack);
	}
}
