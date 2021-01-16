package com.zuxelus.comboarmors.recipes;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.items.IItemUpgradeable;
import com.zuxelus.comboarmors.items.armor.IJetpack;
import com.zuxelus.comboarmors.items.armor.ItemArmorTankUtility;
//import com.zuxelus.comboarmors.utils.ModIntegrationHandler;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class RecipeHandler {
	private static final RecipeHandler instance = new RecipeHandler();

	public static RecipeHandler instance() {
		return instance;
	}

	public void addChestpieceRecipes(Item upgrade) {
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoNanoChest, ModItems.exoNanoChest, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoQuantumChest, ModItems.exoQuantumChest, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoBatpack, ModItems.exoBatpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoAdvBatpack, ModItems.exoAdvBatpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoEnergypack, ModItems.exoEnergypack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoJetpack, ModItems.exoJetpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoJetpack, ModItems.nanoJetpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoBatpack, ModItems.nanoBatpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoAdvBatpack, ModItems.nanoAdvBatpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoEnergypack, ModItems.nanoEnergypack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoUltimate, ModItems.nanoUltimate, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumBatpack, ModItems.quantumBatpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumAdvBatpack, ModItems.quantumAdvBatpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumEnergypack, ModItems.quantumEnergypack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumUltimate, ModItems.quantumUltimate, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackBatpack, ModItems.jetpackBatpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackAdvBatpack, ModItems.jetpackAdvBatpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackEnergypack, ModItems.jetpackEnergypack, upgrade);
	}

	public void addComboRecipes() {
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarNanoHelm, ModItems.exoNanoHelm, ComboArmors.ic2.getItemStack("solarHelmet"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarNanoHelm, ModItems.exoSolar, ComboArmors.ic2.getItemStack("nanoHelmet"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.exoQuantumHelm, ComboArmors.ic2.getItemStack("solarHelmet"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.exoSolar, ComboArmors.ic2.getItemStack("quantumHelmet"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.solarNanoHelm, ComboArmors.ic2.getItemStack("quantumHelmet"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoBatpack, ModItems.exoNanoChest, ComboArmors.ic2.getItemStack("batPack"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoBatpack, ModItems.exoBatpack, ComboArmors.ic2.getItemStack("nanoBodyarmor"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoAdvBatpack, ModItems.exoNanoChest, ComboArmors.ic2.getItemStack("advbatPack"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoAdvBatpack, ModItems.exoAdvBatpack, ComboArmors.ic2.getItemStack("nanoBodyarmor"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoAdvBatpack, ModItems.nanoBatpack, ComboArmors.ic2.getItemStack("advbatPack"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoEnergypack, ModItems.exoNanoChest, ComboArmors.ic2.getItemStack("energyPack"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoEnergypack, ModItems.exoEnergypack, ComboArmors.ic2.getItemStack("nanoBodyarmor"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoEnergypack, ModItems.nanoAdvBatpack, ComboArmors.ic2.getItemStack("energyPack"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoJetpack, ModItems.exoNanoChest, ComboArmors.ic2.getItemStack("electricJetpack"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoJetpack, ModItems.exoJetpack, ComboArmors.ic2.getItemStack("nanoBodyarmor"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoUltimate, ComboArmors.ic2.getItemStack("nanoBodyarmor"), ModItems.jetpackEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoUltimate, ComboArmors.ic2.getItemStack("electricJetpack"), ModItems.nanoEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoUltimate, ComboArmors.ic2.getItemStack("energyPack"), ModItems.nanoJetpack);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumBatpack, ComboArmors.ic2.getItemStack("batPack"), ModItems.exoQuantumChest);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumBatpack, ComboArmors.ic2.getItemStack("quantumBodyarmor"), ModItems.exoBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumBatpack, ModItems.nanoBatpack, ComboArmors.ic2.getItemStack("quantumBodyarmor"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumAdvBatpack, ComboArmors.ic2.getItemStack("advbatPack"), ModItems.exoQuantumChest);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumAdvBatpack, ComboArmors.ic2.getItemStack("quantumBodyarmor"), ModItems.exoAdvBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumAdvBatpack, ModItems.nanoAdvBatpack, ComboArmors.ic2.getItemStack("quantumBodyarmor"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumAdvBatpack, ModItems.quantumBatpack, ComboArmors.ic2.getItemStack("advbatPack"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumEnergypack, ComboArmors.ic2.getItemStack("energyPack"), ModItems.exoQuantumChest);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumEnergypack, ComboArmors.ic2.getItemStack("quantumBodyarmor"), ModItems.exoEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumEnergypack, ModItems.nanoEnergypack, ComboArmors.ic2.getItemStack("quantumBodyarmor"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumEnergypack, ModItems.quantumAdvBatpack, ComboArmors.ic2.getItemStack("energyPack"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumUltimate, ComboArmors.ic2.getItemStack("electricJetpack"), ModItems.quantumEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumUltimate, ComboArmors.ic2.getItemStack("quantumBodyarmor"), ModItems.jetpackEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumUltimate, ModItems.nanoUltimate, ComboArmors.ic2.getItemStack("quantumBodyarmor"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackBatpack, ComboArmors.ic2.getItemStack("electricJetpack"), ModItems.exoBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackBatpack, ModItems.exoJetpack, ComboArmors.ic2.getItemStack("batPack"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackAdvBatpack, ComboArmors.ic2.getItemStack("electricJetpack"), ModItems.exoAdvBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackAdvBatpack, ModItems.exoJetpack, ComboArmors.ic2.getItemStack("advbatPack"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackAdvBatpack, ModItems.jetpackBatpack, ComboArmors.ic2.getItemStack("advbatPack"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackEnergypack, ComboArmors.ic2.getItemStack("electricJetpack"), ModItems.exoEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackEnergypack, ModItems.exoJetpack, ComboArmors.ic2.getItemStack("energyPack"));
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackEnergypack, ModItems.jetpackAdvBatpack, ComboArmors.ic2.getItemStack("energyPack"));

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoStatic, ComboArmors.ic2.getItemStack("staticBoots"), ModItems.exoNanoBoots);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoStatic, ComboArmors.ic2.getItemStack("nanoBoots"), ModItems.exoStatic);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumStatic, ComboArmors.ic2.getItemStack("staticBoots"), ModItems.exoQuantumBoots);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumStatic, ComboArmors.ic2.getItemStack("quantumBoots"), ModItems.exoStatic);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumStatic, ModItems.nanoStatic, ComboArmors.ic2.getItemStack("quantumBoots"));

		if (ModItems.ash != null && !ModItems.ash.isEmpty()) {
			ItemStack advanced = new ItemStack(ModItems.solarNanoHelm, 1);
			NBTTagCompound nbtadv = ItemNBTHelper.getOrCreateNbtData(advanced);
			nbtadv.setInteger("solarProd", 7);
			ArmorAssemblerRecipes.addAssemblyRecipe(advanced, ModItems.ash, ModItems.exoModule);

			ItemStack hybrid = new ItemStack(ModItems.solarQuantumHelm, 1);
			NBTTagCompound nbthyb = ItemNBTHelper.getOrCreateNbtData(hybrid);
			nbthyb.setInteger("solarProd", 63);
			nbthyb.setInteger("upgradedTransfer", 4000);
			nbthyb.setInteger("transferLimit", 5000);
			ArmorAssemblerRecipes.addAssemblyRecipe(hybrid, ModItems.hsh, ModItems.exoModule);

			ItemStack ultimate = new ItemStack(ModItems.solarQuantumHelm, 1);
			NBTTagCompound nbtult = ItemNBTHelper.getOrCreateNbtData(ultimate);
			nbtult.setInteger("solarProd", 511);
			nbtult.setInteger("upgradedTransfer", 4000);
			nbtult.setInteger("transferLimit", 5000);
			ArmorAssemblerRecipes.addAssemblyRecipe(ultimate, ModItems.uhsh, ModItems.exoModule);
		}
		if (ModItems.lvHat != null) {
			ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarNanoHelm, ModItems.exoNanoHelm, ModItems.lvHat);
			ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarNanoHelm, ModItems.exoNanoHelm, ModItems.mvHat);
			ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarNanoHelm, ModItems.exoNanoHelm, ModItems.hvHat);
			ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.exoQuantumHelm, ModItems.lvHat);
			ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.exoQuantumHelm, ModItems.mvHat);
			ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.exoQuantumHelm, ModItems.hvHat);
		}
	}

	public void addElectricRecipes(ItemStack upgrade) {
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoBow, upgrade, ModItems.nanoBow);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarNanoHelm, upgrade, ModItems.solarNanoHelm);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, upgrade, ModItems.solarQuantumHelm);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoBatpack, upgrade, ModItems.nanoBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoAdvBatpack, upgrade, ModItems.nanoAdvBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoEnergypack, upgrade, ModItems.nanoEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoJetpack, upgrade, ModItems.nanoJetpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoUltimate, upgrade, ModItems.nanoUltimate);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumBatpack, upgrade, ModItems.quantumBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumAdvBatpack, upgrade, ModItems.quantumAdvBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumEnergypack, upgrade, ModItems.quantumEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumUltimate, upgrade, ModItems.quantumUltimate);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackBatpack, upgrade, ModItems.jetpackBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackAdvBatpack, upgrade, ModItems.jetpackAdvBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackEnergypack, upgrade, ModItems.jetpackEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoStatic, upgrade, ModItems.nanoStatic);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumStatic, upgrade, ModItems.quantumStatic);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoNanoHelm, upgrade, ModItems.exoNanoHelm);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoNanoChest, upgrade, ModItems.exoNanoChest);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoNanoLegs, upgrade, ModItems.exoNanoLegs);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoNanoBoots, upgrade, ModItems.exoNanoBoots);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoQuantumHelm, upgrade, ModItems.exoQuantumHelm);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoQuantumChest, upgrade, ModItems.exoQuantumChest);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoQuantumLegs, upgrade, ModItems.exoQuantumLegs);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoQuantumBoots, upgrade, ModItems.exoQuantumBoots);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoJetpack, upgrade, ModItems.exoJetpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoBatpack, upgrade, ModItems.exoBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoAdvBatpack, upgrade, ModItems.exoAdvBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoEnergypack, upgrade, ModItems.exoEnergypack);
	}

	public void addJetpackRecipes(Item upgrade) {
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoJetpack, ModItems.nanoJetpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoUltimate, ModItems.nanoUltimate, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoQuantumChest, ModItems.exoQuantumChest, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumBatpack, ModItems.quantumBatpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumAdvBatpack, ModItems.quantumAdvBatpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumEnergypack, ModItems.quantumEnergypack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumUltimate, ModItems.quantumUltimate, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackBatpack, ModItems.jetpackBatpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackAdvBatpack, ModItems.jetpackAdvBatpack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackEnergypack, ModItems.jetpackEnergypack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoJet, ModItems.exoJet, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoJetpack, ModItems.exoJetpack, upgrade);
	}

	public void addSolarRecipes(Item upgrade) {
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarNanoHelm, ModItems.solarNanoHelm, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.solarQuantumHelm, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoSolar, ModItems.exoSolar, upgrade);
	}

	public void addStaticRecipes(Item upgrade) {
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoStatic, ModItems.nanoStatic, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumStatic, ModItems.quantumStatic, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoStatic, ModItems.exoStatic, upgrade);
	}

	public void addCellRecipes(Item upgrade) {
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoCFPack, ModItems.exoCFPack, upgrade);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.exoJet, ModItems.exoJet, upgrade);
	}

	public static void onCrafting(ItemStack output, IInventory inventory) {
		if (output.isEmpty())
			return;
		Item item = output.getItem();
		if (!(item instanceof IItemUpgradeable) && !(item instanceof ItemArmorTankUtility))
			return;

		output.setTagCompound(null);
		NBTTagCompound nbtout = ItemNBTHelper.getOrCreateNbtData(output);

		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			ItemStack input = inventory.getStackInSlot(i);
			if (!input.isEmpty()) {
				NBTTagCompound nbtin = ItemNBTHelper.getOrCreateNbtData(input);
				if (input.getItem() == ModItems.flightModule && output.getItem() instanceof IJetpack)
					nbtout.setBoolean("flight", true);
				if (input.getItem() == ModItems.cloakingModule && ComboArmors.chests.contains(output.getUnlocalizedName()))
					nbtout.setBoolean("cloaking", true);
				if (input.getItem() == ModItems.overchargeModule && ComboArmors.chests.contains(output.getUnlocalizedName()))
					nbtout.setBoolean("overcharge", true);
				if (input.getItem() == ModItems.solarModule && ComboArmors.solars.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("solarProd");
					prod += input.getCount();
					input.setCount(0);
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("solarProd", prod);
				}
				if (ModItems.lvSolarModule != null && input.getItem() == ModItems.lvSolarModule && ComboArmors.solars.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("solarProd");
					prod += input.getCount() * 8;
					input.setCount(0);
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("solarProd", prod);
				}
				if (ModItems.mvSolarModule != null && input.getItem() == ModItems.mvSolarModule && ComboArmors.solars.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("solarProd");
					prod += input.getCount() * 64;
					input.setCount(0);
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("solarProd", prod);
				}
				if (ModItems.hvSolarModule != null && input.getItem() == ModItems.hvSolarModule && ComboArmors.solars.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("solarProd");
					prod += input.getCount() * 512;
					input.setCount(0);
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("solarProd", prod);
				}
				if (input.getItem() == ModItems.staticModule && ComboArmors.statics.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("staticProd");
					prod += input.getCount();
					input.setCount(0);
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("staticProd", prod);
				}
				if (input.getItem() == ModItems.cellModule && output.getItem() instanceof ItemArmorTankUtility) {
					int prod = nbtout.getInteger("addCapacity");
					prod += input.getCount();
					input.setCount(0);
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("addCapacity", prod * 10000);
				}
				if (input.isItemEqual(ComboArmors.ic2.getItemStack("energyStorageUpgrade")) && output.getItem() instanceof IElectricItem && output.getItem() instanceof IItemUpgradeable) {
					IItemUpgradeable outputItem = (IItemUpgradeable) output.getItem();
					int charge = nbtout.getInteger("upgradedCharge");
					charge += input.getCount() * 10000;
					if (charge > outputItem.getMaxUpgradeableCharge())
						charge = outputItem.getMaxUpgradeableCharge();
					input.setCount(0);
					nbtout.setInteger("upgradedCharge", charge);
					nbtout.setInteger("maxCharge", outputItem.getDefaultMaxCharge() + charge);
					updateElectricDamageBars(output);
				}
				if (input.getItem() == ModItems.energyMk2 && output.getItem() instanceof IElectricItem && output.getItem() instanceof IItemUpgradeable) {
					IItemUpgradeable outputItem = (IItemUpgradeable) output.getItem();
					int charge = nbtout.getInteger("upgradedCharge");
					charge += input.getCount() * 100000;
					if (charge > outputItem.getMaxUpgradeableCharge())
						charge = outputItem.getMaxUpgradeableCharge();
					input.setCount(0);
					nbtout.setInteger("upgradedCharge", charge);
					nbtout.setInteger("maxCharge", outputItem.getDefaultMaxCharge() + charge);
					updateElectricDamageBars(output);
				}
				if (input.getItem() == ModItems.energyMk3 && output.getItem() instanceof IElectricItem && output.getItem() instanceof IItemUpgradeable) {
					IItemUpgradeable outputItem = (IItemUpgradeable) output.getItem();
					int charge = nbtout.getInteger("upgradedCharge");
					charge += input.getCount() * 1000000;
					if (charge > outputItem.getMaxUpgradeableCharge())
						charge = outputItem.getMaxUpgradeableCharge();
					input.setCount(0);
					nbtout.setInteger("upgradedCharge", charge);
					nbtout.setInteger("maxCharge", outputItem.getDefaultMaxCharge() + charge);
					updateElectricDamageBars(output);
				}
				if (input.isItemEqual(ComboArmors.ic2.getItemStack("overclockerUpgrade")) && output.getItem() instanceof IElectricItem && output.getItem() instanceof IItemUpgradeable) {
					IItemUpgradeable outputItem = (IItemUpgradeable) output.getItem();
					int transfer = nbtout.getInteger("upgradedTransfer");
					transfer += input.getCount() * 100;
					if (transfer > outputItem.getMaxUpgradeableTransfer())
						transfer = outputItem.getMaxUpgradeableTransfer();
					input.setCount(0);
					nbtout.setInteger("upgradedTransfer", transfer);
					nbtout.setInteger("transferLimit", outputItem.getDefaultTransferLimit() + transfer);
					updateElectricDamageBars(output);
				}
				if (input.isItemEqual(ComboArmors.ic2.getItemStack("transformerUpgrade")) && output.getItem() instanceof IElectricItem && output.getItem() instanceof IItemUpgradeable) {
					IItemUpgradeable outputItem = (IItemUpgradeable) output.getItem();
					int tier = nbtout.getInteger("upgradedTier");
					tier += input.getCount();
					if (outputItem.getDefaultTier() - tier < 1)
						tier = outputItem.getDefaultTier() - 1;
					input.setCount(0);
					nbtout.setInteger("upgradedTier", tier);
					nbtout.setInteger("tier", outputItem.getDefaultTier() - tier);
					if (nbtout.getInteger("tier") < 1)
						nbtout.setInteger("tier", 1);
					updateElectricDamageBars(output);
				}
				if (input.getItem() instanceof ItemArmorTankUtility && output.getItem() instanceof ItemArmorTankUtility)
					nbtout.setTag("Fluid", nbtin.getCompoundTag("Fluid"));
				if (input.getItem() instanceof IElectricItem && input.getItem() instanceof IItemUpgradeable && output.getItem() instanceof IElectricItem && output.getItem() instanceof IItemUpgradeable) {
					IItemUpgradeable outputItem = (IItemUpgradeable) output.getItem();

					int charge = nbtout.getInteger("upgradedCharge");
					charge += nbtin.getInteger("upgradedCharge");
					if (charge > outputItem.getMaxUpgradeableCharge())
						charge = outputItem.getMaxUpgradeableCharge();
					input.setCount(0);
					nbtout.setInteger("upgradedCharge", charge);
					nbtout.setInteger("maxCharge", outputItem.getDefaultMaxCharge() + charge);

					int transfer = nbtout.getInteger("upgradedTransfer");
					transfer += nbtin.getInteger("upgradedTransfer");
					if (transfer > outputItem.getMaxUpgradeableTransfer())
						transfer = outputItem.getMaxUpgradeableTransfer();
					input.setCount(0);
					nbtout.setInteger("upgradedTransfer", transfer);
					nbtout.setInteger("transferLimit", outputItem.getDefaultTransferLimit() + transfer);

					int out = nbtout.getInteger("upgradedTier");
					out += nbtin.getInteger("upgradedTier");
					if (outputItem.getDefaultTier() - out < 1)
						out = outputItem.getDefaultTier() - 1;
					input.setCount(0);
					nbtout.setInteger("upgradedTier", out);
					nbtout.setInteger("tier", outputItem.getDefaultTier() - out);
					if (nbtout.getInteger("tier") < 1)
						nbtout.setInteger("tier", 1);

					int prod = nbtout.getInteger("solarProd");
					prod += nbtin.getInteger("solarProd");
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("solarProd", prod);

					prod = nbtout.getInteger("staticProd");
					prod += nbtin.getInteger("staticProd");
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("staticProd", prod);
					updateElectricDamageBars(output);
				}
				if (ComboArmors.solars.contains(input.getUnlocalizedName()) && ComboArmors.solars.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("solarProd");
					prod += nbtin.getInteger("solarProd");
					/*if (ModIntegrationHandler.isModLoaded(0)) { //TODO
						if (input.getItem() == ModItems.lvHat)
							prod += 7;
						else if (input.getItem() == ModItems.mvHat)
							prod += 63;
						else if (input.getItem() == ModItems.hvHat)
							prod += 511;
					}*/
					input.setCount(0);
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("solarProd", prod);
				}
				if (ComboArmors.statics.contains(input.getUnlocalizedName()) && ComboArmors.statics.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("staticProd");
					prod += nbtin.getInteger("staticProd");
					input.setCount(0);
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("staticProd", prod);
				}
				if (input.getItem() instanceof IJetpack && output.getItem() instanceof IJetpack && nbtin.getBoolean("flight"))
					nbtout.setBoolean("flight", true);
				if (ComboArmors.chests.contains(input.getUnlocalizedName()) && ComboArmors.chests.contains(output.getUnlocalizedName()) && nbtin.getBoolean("cloaking"))
					nbtout.setBoolean("cloaking", true);
				if (ComboArmors.chests.contains(input.getUnlocalizedName()) && ComboArmors.chests.contains(output.getUnlocalizedName()) && nbtin.getBoolean("overcharge"))
					nbtout.setBoolean("overcharge", true);
			}
			if (output.getItem() instanceof ItemArmorTankUtility)
				((ItemArmorTankUtility) output.getItem()).updateDamage(output);
		}
	}

	public static void updateElectricDamageBars(ItemStack itemstack)
	{
		if (itemstack.getItem() instanceof IElectricItem)
		{
			ElectricItem.manager.discharge(itemstack, 0, Integer.MAX_VALUE, true, false, false);
			/*NBTTagCompound nbt = StackUtil.getOrCreateNbtData(itemstack);
			if (nbt.getInteger("maxCharge") <= 0 && itemstack.getItem() instanceof IItemUpgradeable)
			{
				IItemUpgradeable item = (IItemUpgradeable)itemstack.getItem();
				nbt.setInteger("maxCharge", item.getDefaultMaxCharge());
			}
			int maxcharge = nbt.getInteger("maxCharge");
			int charge = nbt.getInteger("charge");
			if (itemstack.getItem() instanceof IElectricItem)
			{
				if (itemstack.getMaxDamage() > 2)
				{
					long a = (long)(maxcharge - charge);
					long b = (long)(a * (itemstack.getMaxDamage() - 2));
					long c = (long)(b / maxcharge);
					int d = (int)c;
					int p = d + 1;
					itemstack.setItemDamage(p);
				}
				else
				{
					itemstack.setItemDamage(0);
				}
			}
			else
			{
				itemstack.setItemDamage(0);
			}*/
		}
	}
}
