package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.IMetalArmor;
import ic2.core.IC2;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class ItemArmorBaseQuantum extends ItemArmorElectricUtility implements IJetpack, IMetalArmor {

	public ItemArmorBaseQuantum(int renderIndex, int piece, int maxCharge, int transferLimit, int tier, boolean share) {
		super(renderIndex, piece, maxCharge, transferLimit, tier, share);
	}

	@Override
	public double getDamageAbsorptionRatio() {
		return 1.1D;
	}

	@Override
	public int getEnergyPerDamage() {
		return 20000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.rare;
	}

	@Override
	public boolean isMetalArmor(ItemStack stack, EntityPlayer player) {
		return true;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (player.inventory.armorInventory[2] != stack)
			return;

		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
		if (onQuantumJetpackTick(player, nbtData))
			player.inventoryContainer.detectAndSendChanges();
	}

	@Override
	public void onFlyKeyPressed(EntityPlayer player, ItemStack stack) {
		flyKeyPressed(player, stack);
	}
}
