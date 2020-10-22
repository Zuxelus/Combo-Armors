package com.zuxelus.comboarmors.recipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.items.IItemUpgradeable;
import com.zuxelus.comboarmors.items.armor.IJetpack;
import com.zuxelus.comboarmors.items.armor.ItemArmorTankUtility;
import com.zuxelus.comboarmors.utils.ModIntegrationHandler;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.recipe.Recipes;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
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
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarNanoHelm, ModItems.exoNanoHelm, Ic2Items.solarHelmet);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarNanoHelm, ModItems.exoSolar, Ic2Items.nanoHelmet);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.exoQuantumHelm, Ic2Items.solarHelmet);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.exoSolar, Ic2Items.quantumHelmet);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.solarNanoHelm, Ic2Items.quantumHelmet);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoBatpack, ModItems.exoNanoChest, Ic2Items.batPack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoBatpack, ModItems.exoBatpack, Ic2Items.nanoBodyarmor);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoAdvBatpack, ModItems.exoNanoChest, Ic2Items.advbatPack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoAdvBatpack, ModItems.exoAdvBatpack, Ic2Items.nanoBodyarmor);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoAdvBatpack, ModItems.nanoBatpack, Ic2Items.advbatPack);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoEnergypack, ModItems.exoNanoChest, Ic2Items.energyPack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoEnergypack, ModItems.exoEnergypack, Ic2Items.nanoBodyarmor);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoEnergypack, ModItems.nanoAdvBatpack, Ic2Items.energyPack);	  

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoJetpack, ModItems.exoNanoChest, Ic2Items.electricJetpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoJetpack, ModItems.exoJetpack, Ic2Items.nanoBodyarmor);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoUltimate, Ic2Items.nanoBodyarmor, ModItems.jetpackEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoUltimate, Ic2Items.electricJetpack, ModItems.nanoEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoUltimate, Ic2Items.energyPack, ModItems.nanoJetpack);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumBatpack, Ic2Items.batPack, ModItems.exoQuantumChest);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumBatpack, Ic2Items.quantumBodyarmor, ModItems.exoBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumBatpack, ModItems.nanoBatpack, Ic2Items.quantumBodyarmor);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumAdvBatpack, Ic2Items.advbatPack, ModItems.exoQuantumChest);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumAdvBatpack, Ic2Items.quantumBodyarmor, ModItems.exoAdvBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumAdvBatpack, ModItems.nanoAdvBatpack, Ic2Items.quantumBodyarmor);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumAdvBatpack, ModItems.quantumBatpack, Ic2Items.advbatPack);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumEnergypack, Ic2Items.energyPack, ModItems.exoQuantumChest);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumEnergypack, Ic2Items.quantumBodyarmor, ModItems.exoEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumEnergypack, ModItems.nanoEnergypack, Ic2Items.quantumBodyarmor);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumEnergypack, ModItems.quantumAdvBatpack, Ic2Items.energyPack);	  

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumUltimate, Ic2Items.electricJetpack, ModItems.quantumEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumUltimate, Ic2Items.quantumBodyarmor, ModItems.jetpackEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumUltimate, ModItems.nanoUltimate, Ic2Items.quantumBodyarmor);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackBatpack, Ic2Items.electricJetpack, ModItems.exoBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackBatpack, ModItems.exoJetpack, Ic2Items.batPack);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackAdvBatpack, Ic2Items.electricJetpack, ModItems.exoAdvBatpack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackAdvBatpack, ModItems.exoJetpack, Ic2Items.advbatPack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackAdvBatpack, ModItems.jetpackBatpack, Ic2Items.advbatPack);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackEnergypack, Ic2Items.electricJetpack, ModItems.exoEnergypack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackEnergypack, ModItems.exoJetpack, Ic2Items.energyPack);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.jetpackEnergypack, ModItems.jetpackAdvBatpack, Ic2Items.energyPack);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoStatic, Ic2Items.staticBoots, ModItems.exoNanoBoots);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.nanoStatic, Ic2Items.nanoBoots, ModItems.exoStatic);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumStatic, Ic2Items.staticBoots, ModItems.exoQuantumBoots);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumStatic, Ic2Items.quantumBoots, ModItems.exoStatic);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.quantumStatic, ModItems.nanoStatic, Ic2Items.quantumBoots);
	}

	public void addCraftingRecipes() {
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.drillBit), new Object[] { "RRA", Character.valueOf('R'), "plateIron", Character.valueOf('A'), Ic2Items.advancedAlloy });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.drill), new Object[] { "BRE", "  P", "  R", Character.valueOf('R'), "plateIron", Character.valueOf('B'), ModItems.drillBit, Character.valueOf('E'), Ic2Items.electronicCircuit, Character.valueOf('P'), StackUtil.copyWithWildCard(Ic2Items.reBattery) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.drill), new Object[] { "BRE", "  P", "  R", Character.valueOf('R'), "plateIron", Character.valueOf('B'), ModItems.drillBit, Character.valueOf('E'), Ic2Items.electronicCircuit, Character.valueOf('P'), StackUtil.copyWithWildCard(Ic2Items.chargedReBattery) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.armorAssembler), new Object[] { "GDG", "ALA", "GCG", Character.valueOf('G'), Items.glowstone_dust, Character.valueOf('D'), ModItems.drill, Character.valueOf('C'), Ic2Items.advancedCircuit, Character.valueOf('L'), StackUtil.copyWithWildCard(Ic2Items.lapotronCrystal), Character.valueOf('A'), Ic2Items.advancedMachine });
		if (ComboArmors.config.craftNanoBow)
			Recipes.advRecipes.addRecipe(new ItemStack(ModItems.nanoBow), new Object[] { " CS", "E S", " CS", Character.valueOf('G'), Items.glowstone_dust, Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('S'), Ic2Items.glassFiberCableItem, Character.valueOf('A'), Ic2Items.advancedAlloy, Character.valueOf('E'), StackUtil.copyWithWildCard(Ic2Items.energyCrystal) });
		Recipes.advRecipes.addRecipe(StackUtil.copyWithSize(new ItemStack(ModItems.exoModule), 4), new Object[] { "RRR", "RCR", "RRR", Character.valueOf('R'), "plateIron", Character.valueOf('C'), Ic2Items.electronicCircuit });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoNanoHelm), new Object[] { "EEE", "EHE", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('H'), StackUtil.copyWithWildCard(Ic2Items.nanoHelmet) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoNanoChest), new Object[] { "ENE", "EEE", "EEE", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('N'), StackUtil.copyWithWildCard(Ic2Items.nanoBodyarmor) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoNanoLegs), new Object[] { "EEE", "ENE", "E E", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('N'), StackUtil.copyWithWildCard(Ic2Items.nanoLeggings) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoNanoBoots), new Object[] { "ENE", "E E", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('N'), StackUtil.copyWithWildCard(Ic2Items.nanoBoots) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoQuantumHelm), new Object[] { "EEE", "EHE", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('H'), StackUtil.copyWithWildCard(Ic2Items.quantumHelmet) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoQuantumChest), new Object[] { "ENE", "EEE", "EEE", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('N'), StackUtil.copyWithWildCard(Ic2Items.quantumBodyarmor) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoQuantumLegs), new Object[] { "EEE", "ENE", "E E", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('N'), StackUtil.copyWithWildCard(Ic2Items.quantumLeggings) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoQuantumBoots), new Object[] { "ENE", "E E", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('N'), StackUtil.copyWithWildCard(Ic2Items.quantumBoots) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoQuantumHelm), new Object[] { " n ", "ILI", "CGC", Character.valueOf('n'), ModItems.exoNanoHelm, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('L'), StackUtil.copyWithWildCard(Ic2Items.lapotronCrystal), Character.valueOf('G'), Ic2Items.reinforcedGlass, Character.valueOf('C'), Ic2Items.advancedCircuit });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoQuantumChest), new Object[] { "AnA", "ILI", "IAI", Character.valueOf('n'), ModItems.exoNanoChest, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('L'), StackUtil.copyWithWildCard(Ic2Items.lapotronCrystal), Character.valueOf('A'), Ic2Items.advancedAlloy });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoQuantumLegs), new Object[] { "MLM", "InI", "G G", Character.valueOf('n'), ModItems.exoNanoLegs, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('L'), StackUtil.copyWithWildCard(Ic2Items.lapotronCrystal), Character.valueOf('G'), Items.glowstone_dust, Character.valueOf('M'), Ic2Items.machine });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoQuantumBoots), new Object[] { "InI", "RLR", Character.valueOf('n'), ModItems.exoNanoBoots, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('L'), StackUtil.copyWithWildCard(Ic2Items.lapotronCrystal), Character.valueOf('R'), Ic2Items.hazmatBoots });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoSolar), new Object[] { "EEE", "ESE", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('S'), Ic2Items.solarHelmet });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoStatic), new Object[] { "ESE", "E E", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('S'), Ic2Items.staticBoots });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoJetpack), new Object[] { "EJE", "EEE", "EEE", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('J'), StackUtil.copyWithWildCard(Ic2Items.electricJetpack) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoBatpack), new Object[] { "EBE", "EEE", "EEE", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('B'), StackUtil.copyWithWildCard(Ic2Items.batPack) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoAdvBatpack), new Object[] { "EAE", "EEE", "EEE", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('A'), StackUtil.copyWithWildCard(Ic2Items.advbatPack) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoEnergypack), new Object[] { "ELE", "EEE", "EEE", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('L'), StackUtil.copyWithWildCard(Ic2Items.energyPack) });    
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.overclockerUpgrade), new Object[] { "IOI", "OAO", "IOI", Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('O'), Ic2Items.overclockerUpgrade, Character.valueOf('A'), Ic2Items.advancedCircuit });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoJet), new Object[] { "EBE", "EEE", "EEE", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('B'), StackUtil.copyWithWildCard(Ic2Items.jetpack) });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.exoCFPack), new Object[] { "EBE", "EEE", "EEE", Character.valueOf('E'), ModItems.exoModule, Character.valueOf('B'), StackUtil.copyWithWildCard(Ic2Items.cfPack) });    
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
		if (output == null)
			return;
		Item item = output.getItem();
		if (!(item instanceof IItemUpgradeable) && !(item instanceof ItemArmorTankUtility))
			return;

		output.setTagCompound(null);
		NBTTagCompound nbtout = StackUtil.getOrCreateNbtData(output);

		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			ItemStack input = inventory.getStackInSlot(i);
			if (input != null) {

				NBTTagCompound nbtin = StackUtil.getOrCreateNbtData(input);
				if (input.getItem() == ModItems.flightModule && output.getItem() instanceof IJetpack)
					nbtout.setBoolean("flight", true);
				if (input.getItem() == ModItems.cloakingModule && ComboArmors.chests.contains(output.getUnlocalizedName()))
					nbtout.setBoolean("cloaking", true);
				if (input.getItem() == ModItems.overchargeModule && ComboArmors.chests.contains(output.getUnlocalizedName()))
					nbtout.setBoolean("overcharge", true);
				if (input.getItem() == ModItems.solarModule && ComboArmors.solars.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("solarProd");
					prod += input.stackSize;
					input.stackSize = 0;
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("solarProd", prod);
				}
				if (ModItems.lvSolarModule != null && input.getItem() == ModItems.lvSolarModule && ComboArmors.solars.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("solarProd");
					prod += input.stackSize * 8;
					input.stackSize = 0;
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("solarProd", prod);
				}
				if (ModItems.mvSolarModule != null && input.getItem() == ModItems.mvSolarModule && ComboArmors.solars.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("solarProd");
					prod += input.stackSize * 64;
					input.stackSize = 0;
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("solarProd", prod);
				}
				if (ModItems.hvSolarModule != null && input.getItem() == ModItems.hvSolarModule && ComboArmors.solars.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("solarProd");
					prod += input.stackSize * 512;
					input.stackSize = 0;
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("solarProd", prod);
				}
				if (input.getItem() == ModItems.staticModule && ComboArmors.statics.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("staticProd");
					prod += input.stackSize;
					input.stackSize = 0;
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("staticProd", prod);
				}
				if (input.getItem() == ModItems.cellModule && output.getItem() instanceof ItemArmorTankUtility) {
					int prod = nbtout.getInteger("addCapacity");
					prod += input.stackSize;
					input.stackSize = 0;
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("addCapacity", prod * 10000);
				}
				if (input.isItemEqual(Ic2Items.energyStorageUpgrade) && output.getItem() instanceof IElectricItem && output.getItem() instanceof IItemUpgradeable) {
					IItemUpgradeable outputItem = (IItemUpgradeable) output.getItem();
					int charge = nbtout.getInteger("upgradedCharge");
					charge += input.stackSize * 10000;
					if (charge > outputItem.getMaxUpgradeableCharge())
						charge = outputItem.getMaxUpgradeableCharge();
					input.stackSize = 0;
					nbtout.setInteger("upgradedCharge", charge);
					nbtout.setInteger("maxCharge", outputItem.getDefaultMaxCharge() + charge);
					updateElectricDamageBars(output);
				}
				if (input.getItem() == ModItems.energyMk2 && output.getItem() instanceof IElectricItem && output.getItem() instanceof IItemUpgradeable) {
					IItemUpgradeable outputItem = (IItemUpgradeable) output.getItem();
					int charge = nbtout.getInteger("upgradedCharge");
					charge += input.stackSize * 100000;
					if (charge > outputItem.getMaxUpgradeableCharge())
						charge = outputItem.getMaxUpgradeableCharge();
					input.stackSize = 0;
					nbtout.setInteger("upgradedCharge", charge);
					nbtout.setInteger("maxCharge", outputItem.getDefaultMaxCharge() + charge);
					updateElectricDamageBars(output);
				}
				if (input.getItem() == ModItems.energyMk3 && output.getItem() instanceof IElectricItem && output.getItem() instanceof IItemUpgradeable) {
					IItemUpgradeable outputItem = (IItemUpgradeable) output.getItem();
					int charge = nbtout.getInteger("upgradedCharge");
					charge += input.stackSize * 1000000;
					if (charge > outputItem.getMaxUpgradeableCharge())
						charge = outputItem.getMaxUpgradeableCharge();
					input.stackSize = 0;
					nbtout.setInteger("upgradedCharge", charge);
					nbtout.setInteger("maxCharge", outputItem.getDefaultMaxCharge() + charge);
					updateElectricDamageBars(output);
				}
				if (input.isItemEqual(Ic2Items.overclockerUpgrade) && output.getItem() instanceof IElectricItem && output.getItem() instanceof IItemUpgradeable) {
					IItemUpgradeable outputItem = (IItemUpgradeable) output.getItem();
					int transfer = nbtout.getInteger("upgradedTransfer");
					transfer += input.stackSize * 100;
					if (transfer > outputItem.getMaxUpgradeableTransfer())
						transfer = outputItem.getMaxUpgradeableTransfer();
					input.stackSize = 0;
					nbtout.setInteger("upgradedTransfer", transfer);
					nbtout.setInteger("transferLimit", outputItem.getDefaultTransferLimit() + transfer);
					updateElectricDamageBars(output);
				}
				if (input.isItemEqual(Ic2Items.transformerUpgrade) && output.getItem() instanceof IElectricItem && output.getItem() instanceof IItemUpgradeable) {
					IItemUpgradeable outputItem = (IItemUpgradeable) output.getItem();
					int tier = nbtout.getInteger("upgradedTier");
					tier += input.stackSize;
					if (outputItem.getDefaultTier() - tier < 1)
						tier = outputItem.getDefaultTier() - 1;
					input.stackSize = 0;
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
					input.stackSize = 0;
					nbtout.setInteger("upgradedCharge", charge);
					nbtout.setInteger("maxCharge", outputItem.getDefaultMaxCharge() + charge);

					int transfer = nbtout.getInteger("upgradedTransfer");
					transfer += nbtin.getInteger("upgradedTransfer");
					if (transfer > outputItem.getMaxUpgradeableTransfer())
						transfer = outputItem.getMaxUpgradeableTransfer();
					input.stackSize = 0;
					nbtout.setInteger("upgradedTransfer", transfer);
					nbtout.setInteger("transferLimit", outputItem.getDefaultTransferLimit() + transfer);

					int out = nbtout.getInteger("upgradedTier");
					out += nbtin.getInteger("upgradedTier");
					if (outputItem.getDefaultTier() - out < 1)
						out = outputItem.getDefaultTier() - 1;
					input.stackSize = 0;
					nbtout.setInteger("upgradedTier", out);
					nbtout.setInteger("tier", outputItem.getDefaultTier() - out);
					if (nbtout.getInteger("tier") < 1)
						nbtout.setInteger("tier", 1);
					updateElectricDamageBars(output);
				}
				if (ComboArmors.solars.contains(input.getUnlocalizedName()) && ComboArmors.solars.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("solarProd");
					prod += nbtin.getInteger("solarProd");
					if (ModIntegrationHandler.isModLoaded(0)) {
						if (input.getItem() == ModItems.lvHat)
							prod += 7;
						else if (input.getItem() == ModItems.mvHat)
							prod += 63;
						else if (input.getItem() == ModItems.hvHat)
							prod += 511;
					}
					input.stackSize = 0;
					if (prod > ComboArmors.config.maxProdUpgrades)
						prod = ComboArmors.config.maxProdUpgrades;
					nbtout.setInteger("solarProd", prod);
				}
				if (ComboArmors.statics.contains(input.getUnlocalizedName()) && ComboArmors.statics.contains(output.getUnlocalizedName())) {
					int prod = nbtout.getInteger("staticProd");
					prod += nbtin.getInteger("staticProd");
					input.stackSize = 0;
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
