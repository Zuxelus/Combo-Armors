package com.zuxelus.comboarmors.init;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.blocks.*;
import com.zuxelus.comboarmors.entities.*;
import com.zuxelus.comboarmors.items.*;
import com.zuxelus.comboarmors.items.armor.*;
import com.zuxelus.comboarmors.recipes.RecipeHandler;
import com.zuxelus.comboarmors.tileentities.*;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.recipe.Recipes;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModItems {
	public static Block armorAssembler;
	public static Item nanoBow;
	public static Item assemblyDrill;
	public static Item assemblyDrillBit;
	public static Item solarNanoHelm;
	public static Item solarQuantumHelm;
	public static Item nanoBatpack;
	public static Item nanoAdvBatpack;
	public static Item nanoEnergypack;
	public static Item nanoJetpack;
	public static Item nanoUltimate;
	public static Item quantumBatpack;
	public static Item quantumAdvBatpack;
	public static Item quantumEnergypack;
	public static Item quantumUltimate;
	public static Item jetpackBatpack;
	public static Item jetpackAdvBatpack;
	public static Item jetpackEnergypack;
	public static Item nanoStatic;
	public static Item quantumStatic;
	public static Item exoNanoHelm;
	public static Item exoNanoChest;
	public static Item exoNanoLegs;
	public static Item exoNanoBoots;
	public static Item exoQuantumHelm;
	public static Item exoQuantumChest;
	public static Item exoQuantumLegs;
	public static Item exoQuantumBoots;
	public static Item exoJetpack;
	public static Item exoBatpack;
	public static Item exoAdvBatpack;
	public static Item exoEnergypack;
	public static Item exoSolar;
	public static Item exoStatic;
	public static Item exoJet;
	public static Item exoCFPack;
	public static Item exoModule;
	public static Item flightModule;
	public static Item jetBooster;
	public static Item solarModule;
	public static Item staticModule;
	public static Item cloakingModule;
	public static Item overchargeModule;
	public static Item cellModule;
	public static Item energyMk2;
	public static Item energyMk3;
	public static Item overclockerUpgrade;
	public static Item creativeUpgrade;
	public static Item lvSolarModule;
	public static Item mvSolarModule;
	public static Item hvSolarModule;
	public static Item lvHat;
	public static Item mvHat;
	public static Item hvHat;
	public static ItemStack lvArray;
	public static ItemStack mvArray;
	public static ItemStack hvArray;
	public static ItemStack asp;
	public static ItemStack hybridsp;
	public static ItemStack ulthybsp;
	public static ItemStack ash;
	public static ItemStack hsh;
	public static ItemStack uhsh;

	public static void onBlockRegistry() {
		armorAssembler = register(new BlockArmorAssembler(), "armor_assembler");
	}

	public static void onItemRegistry() {
		int renderExoNano = ComboArmors.proxy.addArmor("ic2ca/exonano");
		int renderExoQuantum = ComboArmors.proxy.addArmor("ic2ca/exoquantum");
		
		exoNanoHelm = register(new ItemArmorExoNano(renderExoNano, 0), "exo_nano_helm");
		exoNanoChest = register(new ItemArmorExoNano(renderExoNano, 1), "exo_nano_chest");
		exoNanoLegs = register(new ItemArmorExoNano(renderExoNano, 2), "exo_nano_legs");
		exoNanoBoots = register(new ItemArmorExoNano(renderExoNano, 3), "exo_nano_boots");
		exoQuantumHelm = register(new ItemArmorExoQuantum(renderExoQuantum, 0), "exo_quantum_helm");
		exoQuantumChest = register(new ItemArmorExoQuantum(renderExoQuantum, 1), "exo_quantum_chest");
		exoQuantumLegs = register(new ItemArmorExoQuantum(renderExoQuantum, 2), "exo_quantum_legs");
		exoQuantumBoots = register(new ItemArmorExoQuantum(renderExoQuantum, 3), "exo_quantum_boots");
		exoJetpack = register(new ItemArmorExoJetpack(ComboArmors.proxy.addArmor("ic2ca/exojetpack")), "exo_jetpack");
		exoBatpack = register(new ItemArmorExoBatpack(ComboArmors.proxy.addArmor("ic2ca/exobatpack")), "exo_batpack");
		exoAdvBatpack = register(new ItemArmorExoAdvBatpack(ComboArmors.proxy.addArmor("ic2ca/exoadvpack")), "exo_advbatpack");
		exoEnergypack = register(new ItemArmorExoEnergypack(ComboArmors.proxy.addArmor("ic2ca/exoenergypack")), "exo_energypack");
		exoSolar = register(new ItemArmorExoSolar(ComboArmors.proxy.addArmor("ic2ca/exosolar")), "exo_solar");
		exoStatic = register(new ItemArmorExoStatic(ComboArmors.proxy.addArmor("ic2ca/exostatic")), "exo_static");
		exoJet = register(new ItemArmorExoJet(ComboArmors.proxy.addArmor("ic2ca/exojet")), "exo_jet");
		exoCFPack = register(new ItemArmorExoCFPack(ComboArmors.proxy.addArmor("ic2ca/exocfpack")), "exo_cfpack");

		solarNanoHelm = register(new ItemHelmetNanoSolar(ComboArmors.proxy.addArmor("ic2ca/solarnano")), "solar_nano_helm");
		solarQuantumHelm = register(new ItemHelmetQuantumSolar(ComboArmors.proxy.addArmor("ic2ca/solarquantum")), "solar_quantum_helm");
		nanoBatpack = register(new ItemBodyNanoBatpack(ComboArmors.proxy.addArmor("ic2ca/nanobat")), "nano_batpack");
		nanoAdvBatpack = register(new ItemBodyNanoAdvBatpack(ComboArmors.proxy.addArmor("ic2ca/nanoadv")), "nano_advbatpack");
		nanoEnergypack = register(new ItemBodyNanoEnergypack(ComboArmors.proxy.addArmor("ic2ca/nanoenergy")), "nano_energypack");
		nanoJetpack = register(new ItemBodyNanoJetpack(ComboArmors.proxy.addArmor("ic2ca/nanojet")), "nano_jetpack");
		nanoUltimate = register(new ItemBodyNanoUltimate(ComboArmors.proxy.addArmor("ic2ca/ultimatenano")), "ultimate_nano");
		quantumBatpack = register(new ItemBodyQuantumBatpack(ComboArmors.proxy.addArmor("ic2ca/quantumbat")), "quantum_batpack");
		quantumAdvBatpack = register(new ItemBodyQuantumAdvBatpack(ComboArmors.proxy.addArmor("ic2ca/quantumadv")), "quantum_advpack");
		quantumEnergypack = register(new ItemBodyQuantumEnergypack(ComboArmors.proxy.addArmor("ic2ca/quantumenergy")), "quantum_energypack");
		quantumUltimate = register(new ItemBodyQuantumUltimate(ComboArmors.proxy.addArmor("ic2ca/ultimatequantum")), "ultimate_quantum");
		jetpackBatpack = register(new ItemBodyJetpackBatpack(ComboArmors.proxy.addArmor("ic2ca/batjetpack")), "jetpack_batpack");
		jetpackAdvBatpack = register(new ItemBodyJetpackAdvBatpack(ComboArmors.proxy.addArmor("ic2ca/advjetpack")), "jetpack_advpack");
		jetpackEnergypack = register(new ItemBodyJetpackEnergypack(ComboArmors.proxy.addArmor("ic2ca/energyjetpack")), "jetpack_energypack");
		nanoStatic = register(new ItemBootsStaticNano(ComboArmors.proxy.addArmor("ic2ca/nanostatic")), "nano_static");
		quantumStatic = register(new ItemBootsStaticQuantum(ComboArmors.proxy.addArmor("ic2ca/quantumstatic")), "quantum_static");

		exoModule = register(new ItemIc2ca(), "exo_module");
		jetBooster = register(new ItemIc2ca(EnumRarity.rare), "jet_booster");
		flightModule = register(new ItemUpgrade(EnumRarity.rare, 1, EnumUpgradeType.JETPACKS), "flight_module");
		solarModule = register(new ItemUpgrade(EnumRarity.uncommon, EnumUpgradeType.SOLARS), "solar_module");
		staticModule = register(new ItemUpgrade(EnumRarity.uncommon, EnumUpgradeType.STATICS), "static_module");
		cloakingModule = register(new ItemUpgrade(EnumRarity.rare, 1, EnumUpgradeType.CHESTS), "cloaking_module");
		overchargeModule = register(new ItemUpgrade(EnumRarity.rare, 1, EnumUpgradeType.CHESTS), "overcharge_module");
		cellModule = register(new ItemUpgrade(EnumRarity.uncommon, EnumUpgradeType.TANKS), "cell_module");

		energyMk2 = register(new ItemUpgrade(EnumRarity.common, EnumUpgradeType.ELECTRICS), "energy_mk2");
		energyMk3 = register(new ItemUpgrade(EnumRarity.uncommon, EnumUpgradeType.ELECTRICS), "energy_mk3");
		assemblyDrill = register(new ItemIc2ca(EnumRarity.common, 1), "assembly_drill");
		assemblyDrillBit = register(new ItemIc2ca(), "assembly_drill_bit");

		nanoBow = register(new ItemNanoBow(), "nano_bow");

		overclockerUpgrade = register(new ItemAssemblerUpgrade(EnumRarity.rare, 64), "overclocker_upgrade");
		creativeUpgrade = register(new ItemAssemblerUpgrade(EnumRarity.epic, 1), "creative_upgrade");
	}

	public static Block register(Block block, String name) {
		block.setBlockName(name);
		GameRegistry.registerBlock(block, name);
		return block;
	}

	public static Item register(Item item, String name) {
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);
		return item;
	}

	public static void registerTileEntities() {
		EntityRegistry.registerGlobalEntityID(EntityTechArrow.class, "tech_arrow", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityLaser.class, "helmet_laser", EntityRegistry.findGlobalUniqueEntityId());

		GameRegistry.registerTileEntity(TileEntityArmorAssembler.class, "armor_assembler");
	}

	public static void registerLists() {
		ComboArmors.solars.add(solarNanoHelm.getUnlocalizedName());
		ComboArmors.solars.add(solarQuantumHelm.getUnlocalizedName());
		ComboArmors.solars.add(exoSolar.getUnlocalizedName());
		if (lvHat != null) {
			ComboArmors.solars.add(lvHat.getUnlocalizedName());
			ComboArmors.solars.add(mvHat.getUnlocalizedName());
			ComboArmors.solars.add(hvHat.getUnlocalizedName());
		}

		ComboArmors.statics.add(nanoStatic.getUnlocalizedName());
		ComboArmors.statics.add(quantumStatic.getUnlocalizedName());
		ComboArmors.statics.add(exoStatic.getUnlocalizedName());

		ComboArmors.chests.add(exoNanoChest.getUnlocalizedName());
		ComboArmors.chests.add(exoQuantumChest.getUnlocalizedName());
		ComboArmors.chests.add(exoJetpack.getUnlocalizedName());
		ComboArmors.chests.add(exoAdvBatpack.getUnlocalizedName());
		ComboArmors.chests.add(exoEnergypack.getUnlocalizedName());
		ComboArmors.chests.add(exoBatpack.getUnlocalizedName());
		ComboArmors.chests.add(nanoBatpack.getUnlocalizedName());
		ComboArmors.chests.add(nanoAdvBatpack.getUnlocalizedName());
		ComboArmors.chests.add(nanoEnergypack.getUnlocalizedName());
		ComboArmors.chests.add(nanoJetpack.getUnlocalizedName());
		ComboArmors.chests.add(nanoUltimate.getUnlocalizedName());
		ComboArmors.chests.add(quantumBatpack.getUnlocalizedName());
		ComboArmors.chests.add(quantumAdvBatpack.getUnlocalizedName());
		ComboArmors.chests.add(quantumEnergypack.getUnlocalizedName());
		ComboArmors.chests.add(quantumUltimate.getUnlocalizedName());
		ComboArmors.chests.add(jetpackBatpack.getUnlocalizedName());
		ComboArmors.chests.add(jetpackAdvBatpack.getUnlocalizedName());
		ComboArmors.chests.add(jetpackEnergypack.getUnlocalizedName()); 
	}
	
	public static void registerCraftingRecipes()
	{
		RecipeHandler.instance().addComboRecipes();
		RecipeHandler.instance().addCraftingRecipes();

		RecipeHandler.instance().addJetpackRecipes(flightModule);
		RecipeHandler.instance().addSolarRecipes(solarModule);
		if (ModItems.lvSolarModule != null) {
			RecipeHandler.instance().addSolarRecipes(ModItems.lvSolarModule);
			RecipeHandler.instance().addSolarRecipes(ModItems.mvSolarModule);
			RecipeHandler.instance().addSolarRecipes(ModItems.hvSolarModule);
		}
		RecipeHandler.instance().addStaticRecipes(staticModule);
		RecipeHandler.instance().addChestpieceRecipes(cloakingModule);
		RecipeHandler.instance().addChestpieceRecipes(overchargeModule);
		RecipeHandler.instance().addCellRecipes(cellModule);
		if (ComboArmors.config.useOverclocker)
			RecipeHandler.instance().addElectricRecipes(Ic2Items.overclockerUpgrade);
		if (ComboArmors.config.useEnergyMk1)
			RecipeHandler.instance().addElectricRecipes(Ic2Items.energyStorageUpgrade);
		if (ComboArmors.config.useTransformer)
			RecipeHandler.instance().addElectricRecipes(Ic2Items.transformerUpgrade);
		RecipeHandler.instance().addElectricRecipes(new ItemStack(energyMk2));
		RecipeHandler.instance().addElectricRecipes(new ItemStack(energyMk3));
	}
}
