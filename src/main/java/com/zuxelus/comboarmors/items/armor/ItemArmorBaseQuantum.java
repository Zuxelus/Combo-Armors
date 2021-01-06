package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import ic2.api.item.IMetalArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemArmorBaseQuantum extends ItemArmorElectricUtility implements IJetpack, IMetalArmor {

	public ItemArmorBaseQuantum(EntityEquipmentSlot slot, int maxCharge, int transferLimit, int tier, boolean share) {
		super(slot, maxCharge, transferLimit, tier, share);
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
		return EnumRarity.RARE;
	}

	@Override
	public boolean isMetalArmor(ItemStack stack, EntityPlayer player) {
		return true;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (player.inventory.armorInventory.get(2) != stack)
			return;

		NBTTagCompound nbtData = ItemNBTHelper.getOrCreateNbtData(stack);
		if (onQuantumJetpackTick(player, nbtData))
			player.inventoryContainer.detectAndSendChanges();
	}
}
