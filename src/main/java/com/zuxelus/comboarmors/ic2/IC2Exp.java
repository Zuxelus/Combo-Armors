package com.zuxelus.comboarmors.ic2;

import ic2.api.item.ElectricItem;
import ic2.api.item.IC2Items;
import ic2.core.profile.ProfileManager;
import ic2.core.profile.Version;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IC2Exp extends CrossIC2 {

	@Override
	public IC2Type getType() {
		return IC2Type.EXP;
	}

	@Override
	public int getProfile() {
		return ProfileManager.selected.style == Version.OLD ? 1 : 0; 
	}

	@Override
	public ItemStack getItemStack(String name) {
		switch (name) {
		case "filledTinCan":
			return IC2Items.getItem("filled_tin_can");
		case "overclockerUpgrade":
			return IC2Items.getItem("upgrade", "overclocker");
		case "energyStorageUpgrade":
			return IC2Items.getItem("upgrade", "energy_storage");
		case "transformerUpgrade":
			return IC2Items.getItem("upgrade", "transformer");

		case "solarHelmet":
			return IC2Items.getItem("solar_helmet");
		case "nanoHelmet":
			return IC2Items.getItem("nano_helmet");
		case "nanoBodyarmor":
			return IC2Items.getItem("nano_chestplate");
		/*case "nanoHelmet":
			return IC2Items.getItem("nano_leggings");*/
		case "nanoBoots":
			return IC2Items.getItem("nano_boots");
		case "quantumHelmet":
			return IC2Items.getItem("quantum_helmet");
		case "quantumBodyarmor":
			return IC2Items.getItem("quantum_chestplate");
		/*case "quantumHelmet":
			return IC2Items.getItem("quantum_leggings");*/
		case "quantumBoots":
			return IC2Items.getItem("quantum_boots");
		case "staticBoots":
			return IC2Items.getItem("static_boots");
		case "batPack":
			return IC2Items.getItem("batpack");
		case "advbatPack":
			return IC2Items.getItem("advanced_batpack");
		case "energyPack":
			return IC2Items.getItem("energy_pack");
		case "jetpack":
			return IC2Items.getItem("jetpack");
		case "electricJetpack":
			return IC2Items.getItem("jetpack_electric");
			
			
			
			
		case "machine":
			return IC2Items.getItem("resource", "machine");
		case "mfsu":
			return IC2Items.getItem("te","mfsu");
		case "circuit":
			return IC2Items.getItem("crafting","circuit");
		}
		return ItemStack.EMPTY;
	}

	@Override
	public Item getItem(String name) {
		switch (name) {
		case "seed":
			return IC2Items.getItem("seed").getItem();
		default:
			return null;
		}
	}

	@Override
	public ItemStack getChargedStack(ItemStack stack) {
		ElectricItem.manager.charge(stack, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false);
		return stack;
	}
}
