package com.zuxelus.comboarmors.items.armor;

import java.util.List;

import com.zuxelus.comboarmors.items.IItemUpgradeable;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemArmorExoUtility extends ItemArmorBase implements IItemUpgradeable {

	public ItemArmorExoUtility(EntityEquipmentSlot slot) {
		super(slot);
		setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		tooltip.add(I18n.format("info.upgrade_module_installed"));
		if (nbt.getBoolean("flight"))
			tooltip.add(I18n.format("info.flight_turbine_installed"));
		if (nbt.getBoolean("cloaking"))
			tooltip.add(I18n.format("info.cloaking_module_installed"));
		if (nbt.getBoolean("overcharge"))
			tooltip.add(I18n.format("info.discharge_module_installed"));
		if (nbt.getInteger("solarProd") > 0)
			tooltip.add(I18n.format("info.solar_produces", nbt.getInteger("solarProd") + 1));
		if (nbt.getInteger("staticProd") > 0)
			tooltip.add(I18n.format("info.static_produces", nbt.getInteger("staticProd") + 1));
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab))
			return;
		items.add(new ItemStack(this, 1, getMaxDamage()));
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return null;
	}

	@Override
	public int getDefaultMaxCharge() {
		return 0;
	}

	@Override
	public int getDefaultTier() {
		return 0;
	}

	@Override
	public int getDefaultTransferLimit() {
		return 0;
	}

	@Override
	public int getMaxUpgradeableCharge() {
		return 0;
	}

	@Override
	public int getMaxUpgradeableTransfer() {
		return 0;
	}
}
