package com.zuxelus.comboarmors.items.armor;

import java.util.List;

import com.zuxelus.comboarmors.items.IItemUpgradeable;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;
import com.zuxelus.comboarmors.utils.Util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ItemArmorExoUtility extends ItemArmorBase implements IItemUpgradeable {

	public ItemArmorExoUtility(int renderIndex, int piece) {
		super(renderIndex, piece);
		setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		list.add(I18n.format("info.upgrade_module_installed"));
		if (nbt.getBoolean("flight"))
			list.add(I18n.format("info.flight_turbine_installed"));
		if (nbt.getBoolean("cloaking"))
			list.add(I18n.format("info.cloaking_module_installed"));
		if (nbt.getBoolean("overcharge"))
			list.add(I18n.format("info.discharge_module_installed"));
		if (nbt.getInteger("solarProd") > 0)
			list.add(I18n.format("info.solar_produces", nbt.getInteger("solarProd") + 1));
		if (nbt.getInteger("staticProd") > 0)
			list.add(I18n.format("info.static_produces", nbt.getInteger("staticProd") + 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		list.add(new ItemStack(this, 1, getMaxDamage()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		itemIcon = Util.register(ir, this);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
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
