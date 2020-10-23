package com.zuxelus.comboarmors.init;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.blocks.*;
import com.zuxelus.comboarmors.entities.*;
import com.zuxelus.comboarmors.ic2.CrossIC2;
import com.zuxelus.comboarmors.items.*;
import com.zuxelus.comboarmors.items.armor.*;
import com.zuxelus.comboarmors.recipes.RecipeHandler;
import com.zuxelus.comboarmors.tileentities.*;

import ic2.api.recipe.Recipes;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
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

	@SubscribeEvent
	public static void onBlockRegistry(Register<Block> event) {
		armorAssembler = register(event, new BlockArmorAssembler(), "armor_assembler");
	}

	@SubscribeEvent
	public static void onItemRegistry(Register<Item> event) {
		event.getRegistry().register(new ItemBlock(armorAssembler).setRegistryName("armor_assembler"));

		exoNanoHelm = register(event, new ItemArmorExoNano(EntityEquipmentSlot.HEAD), "exo_nano_helm");
		exoNanoChest = register(event, new ItemArmorExoNano(EntityEquipmentSlot.CHEST), "exo_nano_chest");
		exoNanoLegs = register(event, new ItemArmorExoNano(EntityEquipmentSlot.LEGS), "exo_nano_legs");
		exoNanoBoots = register(event, new ItemArmorExoNano(EntityEquipmentSlot.FEET), "exo_nano_boots");
		exoQuantumHelm = register(event, new ItemArmorExoQuantum(EntityEquipmentSlot.HEAD), "exo_quantum_helm");
		exoQuantumChest = register(event, new ItemArmorExoQuantum(EntityEquipmentSlot.CHEST), "exo_quantum_chest");
		exoQuantumLegs = register(event, new ItemArmorExoQuantum(EntityEquipmentSlot.LEGS), "exo_quantum_legs");
		exoQuantumBoots = register(event, new ItemArmorExoQuantum(EntityEquipmentSlot.FEET), "exo_quantum_boots");
		exoJetpack = register(event, new ItemArmorExoJetpack(), "exo_jetpack");
		exoBatpack = register(event, new ItemArmorExoBatpack(), "exo_batpack");
		exoAdvBatpack = register(event, new ItemArmorExoAdvBatpack(), "exo_advbatpack");
		exoEnergypack = register(event, new ItemArmorExoEnergypack(), "exo_energypack");
		exoSolar = register(event, new ItemArmorExoSolar(), "exo_solar");
		exoStatic = register(event, new ItemArmorExoStatic(), "exo_static");
		exoJet = register(event, new ItemArmorExoJet(), "exo_jet");
		exoCFPack = register(event, new ItemArmorExoCFPack(), "exo_cfpack");

		solarNanoHelm = register(event, new ItemHelmetNanoSolar(), "solar_nano_helm");
		solarQuantumHelm = register(event, new ItemHelmetQuantumSolar(), "solar_quantum_helm");
		nanoBatpack = register(event, new ItemBodyNanoBatpack(), "nano_batpack");
		nanoAdvBatpack = register(event, new ItemBodyNanoAdvBatpack(), "nano_advbatpack");
		nanoEnergypack = register(event, new ItemBodyNanoEnergypack(), "nano_energypack");
		nanoJetpack = register(event, new ItemBodyNanoJetpack(), "nano_jetpack");
		nanoUltimate = register(event, new ItemBodyNanoUltimate(), "ultimate_nano");
		quantumBatpack = register(event, new ItemBodyQuantumBatpack(), "quantum_batpack");
		quantumAdvBatpack = register(event, new ItemBodyQuantumAdvBatpack(), "quantum_advpack");
		quantumEnergypack = register(event, new ItemBodyQuantumEnergypack(), "quantum_energypack");
		quantumUltimate = register(event, new ItemBodyQuantumUltimate(), "ultimate_quantum");
		jetpackBatpack = register(event, new ItemBodyJetpackBatpack(), "jetpack_batpack");
		jetpackAdvBatpack = register(event, new ItemBodyJetpackAdvBatpack(), "jetpack_advpack");
		jetpackEnergypack = register(event, new ItemBodyJetpackEnergypack(), "jetpack_energypack");
		nanoStatic = register(event, new ItemBootsStaticNano(), "nano_static");
		quantumStatic = register(event, new ItemBootsStaticQuantum(), "quantum_static");

		exoModule = register(event, new ItemIc2ca(), "exo_module");
		jetBooster = register(event, new ItemIc2ca(EnumRarity.RARE), "jet_booster");
		flightModule = register(event, new ItemUpgrade(EnumRarity.RARE, 1, EnumUpgradeType.JETPACKS), "flight_module");
		solarModule = register(event, new ItemUpgrade(EnumRarity.UNCOMMON, EnumUpgradeType.SOLARS), "solar_module");
		staticModule = register(event, new ItemUpgrade(EnumRarity.UNCOMMON, EnumUpgradeType.STATICS), "static_module");
		cloakingModule = register(event, new ItemUpgrade(EnumRarity.RARE, 1, EnumUpgradeType.CHESTS), "cloaking_module");
		overchargeModule = register(event, new ItemUpgrade(EnumRarity.RARE, 1, EnumUpgradeType.CHESTS), "overcharge_module");
		cellModule = register(event, new ItemUpgrade(EnumRarity.UNCOMMON, EnumUpgradeType.TANKS), "cell_module");

		energyMk2 = register(event, new ItemUpgrade(EnumRarity.COMMON, EnumUpgradeType.ELECTRICS), "energy_mk2");
		energyMk3 = register(event, new ItemUpgrade(EnumRarity.UNCOMMON, EnumUpgradeType.ELECTRICS), "energy_mk3");
		assemblyDrill = register(event, new ItemIc2ca(EnumRarity.COMMON, 1), "assembly_drill");
		assemblyDrillBit = register(event, new ItemIc2ca(), "assembly_drill_bit");

		//nanoBow = register(event, new ItemNanoBow(), "nanoBow");

		overclockerUpgrade = register(event, new ItemAssemblerUpgrade(EnumRarity.RARE, 64), "overclocker_upgrade");
		creativeUpgrade = register(event, new ItemAssemblerUpgrade(EnumRarity.EPIC, 1), "creative_upgrade");
	}

	public static Block register(Register<Block> event, Block block, String name) {
		block.setUnlocalizedName(name);
		block.setRegistryName(name);
		event.getRegistry().register(block);
		return block;
	}

	public static Item register(Register<Item> event, Item item, String name) {
		item.setUnlocalizedName(name);
		item.setRegistryName(name);
		event.getRegistry().register(item);
		return item;
	}

	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		registerBlockModel(armorAssembler, 0, "armor_assembler");
		
		registerItemModel(exoNanoHelm, "exo_nano_helm");
		registerItemModel(exoNanoChest, "exo_nano_chest");
		registerItemModel(exoNanoLegs, "exo_nano_legs");
		registerItemModel(exoNanoBoots, "exo_nano_boots");
		registerItemModel(exoQuantumHelm, "exo_quantum_helm");
		registerItemModel(exoQuantumChest, "exo_quantum_chest");
		registerItemModel(exoQuantumLegs, "exo_quantum_legs");
		registerItemModel(exoQuantumBoots, "exo_quantum_boots");
		registerItemModel(exoJetpack, "exo_jetpack");
		registerItemModel(exoBatpack, "exo_batpack");
		registerItemModel(exoAdvBatpack, "exo_advbatpack");
		registerItemModel(exoEnergypack, "exo_energypack");
		registerItemModel(exoSolar, "exo_solar");
		registerItemModel(exoStatic, "exo_static");
		registerItemModel(exoJet, "exo_jet");
		registerItemModel(exoCFPack, "exo_cfpack");

		registerItemModel(solarNanoHelm, "solar_nano_helm");
		registerItemModel(solarQuantumHelm , "solar_quantum_helm");
		registerItemModel(nanoBatpack, "nano_batpack");
		registerItemModel(nanoAdvBatpack, "nano_advbatpack");
		registerItemModel(nanoEnergypack, "nano_energypack");
		registerItemModel(nanoJetpack, "nano_jetpack");
		registerItemModel(nanoUltimate, "ultimate_nano");
		registerItemModel(quantumBatpack, "quantum_batpack");
		registerItemModel(quantumAdvBatpack, "quantum_advpack");
		registerItemModel(quantumEnergypack, "quantum_energypack");
		registerItemModel(quantumUltimate, "ultimate_quantum");
		registerItemModel(jetpackBatpack, "jetpack_batpack");
		registerItemModel(jetpackAdvBatpack, "jetpack_advpack");
		registerItemModel(jetpackEnergypack, "jetpack_energypack");
		registerItemModel(nanoStatic , "nano_static");
		registerItemModel(quantumStatic, "quantum_static");

		registerItemModel(exoModule, "exo_module");
		registerItemModel(jetBooster, "jet_booster");
		registerItemModel(flightModule, "flight_module");
		registerItemModel(solarModule, "solar_module");
		registerItemModel(staticModule, "static_module");
		registerItemModel(cloakingModule, "cloaking_module");
		registerItemModel(overchargeModule, "overcharge_module");
		registerItemModel(cellModule, "cell_module");

		registerItemModel(energyMk2, "energy_mk2");
		registerItemModel(energyMk3, "energy_mk3");
		registerItemModel(assemblyDrill, "assembly_drill");
		registerItemModel(assemblyDrillBit, "assembly_drill_bit");

		//registerItemModel(nanoBow, "nanoBow");

		registerItemModel(overclockerUpgrade, "overclocker_upgrade");
		registerItemModel(creativeUpgrade, "creative_upgrade");
	}

	private static void registerBlockModel(Block block, int meta, String name) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(ComboArmors.MODID + ":" + name, "inventory"));
	}

	public static void registerItemModel(Item item, String name) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ComboArmors.MODID + ":" + name, "inventory"));
	}
	
	public static void registerTileEntities() {
		/*EntityRegistry.registerGlobalEntityID(EntityTechArrow.class, "tech_arrow", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityLaser.class, "helmet_laser", EntityRegistry.findGlobalUniqueEntityId());*/

		GameRegistry.registerTileEntity(TileEntityArmorAssembler.class, ComboArmors.MODID + ":armor_assembler");
	}

	@SubscribeEvent
	public static void registerRecipes(Register<IRecipe> event) {
		ComboArmors.ic2 = CrossIC2.getMod();
		registerCraftingRecipes();
		registerLists();
	}

	public static void registerLists() {
		ComboArmors.solars.add(solarNanoHelm.getUnlocalizedName());
		ComboArmors.solars.add(solarQuantumHelm.getUnlocalizedName());
		ComboArmors.solars.add(exoSolar.getUnlocalizedName());

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
		/*if (ComboArmors.config.craftFlightTurbine) {
			Recipes.advRecipes.addRecipe(new ItemStack(jetBooster), new Object[] { "RAR", "RIR", "G G", Character.valueOf('R'), "plateIron", Character.valueOf('A'), Ic2Items.advancedCircuit, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('G'), Items.GLOWSTONE_DUST });
			Recipes.advRecipes.addRecipe(new ItemStack(flightModule), new Object[] { "RAR", "BLB", "RAR", Character.valueOf('R'), "plateIron", Character.valueOf('A'), Ic2Items.advancedCircuit, Character.valueOf('L'), StackUtil.copyWithWildCard(Ic2Items.lapotronCrystal), Character.valueOf('B'), jetBooster });
		}
		RecipeHandler.instance().addJetpackRecipes(flightModule);
		if (ComboArmors.config.craftSolarProd)
			Recipes.advRecipes.addRecipe(new ItemStack(solarModule), new Object[] { "RRR", "CSC", "RRR", Character.valueOf('R'), "plateIron", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('S'), Ic2Items.solarPanel });
		RecipeHandler.instance().addSolarRecipes(solarModule);
		if (ComboArmors.config.craftStaticProd)
			Recipes.advRecipes.addRecipe(new ItemStack(staticModule), new Object[] { "RWR", "CEC", "RWR", Character.valueOf('R'), "plateIron", Character.valueOf('W'), Blocks.WOOL, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('E'), Ic2Items.electronicCircuit });
		RecipeHandler.instance().addStaticRecipes(staticModule);
		if (ComboArmors.config.craftCloakingModule)
			Recipes.advRecipes.addRecipe(new ItemStack(cloakingModule), new Object[] { "RAR", "CIC", "RAR", Character.valueOf('R'), "plateIron", Character.valueOf('C'), Items.GOLDEN_CARROT, Character.valueOf('A'), Ic2Items.advancedCircuit, Character.valueOf('I'), Ic2Items.iridiumPlate });
		RecipeHandler.instance().addChestpieceRecipes(cloakingModule);
		if (ComboArmors.config.craftDischargeModule)
			Recipes.advRecipes.addRecipe(new ItemStack(overchargeModule), new Object[] { "RAR", "TIT", "RAR", Character.valueOf('R'), "plateIron", Character.valueOf('T'), Ic2Items.teslaCoil, Character.valueOf('A'), Ic2Items.advancedCircuit, Character.valueOf('I'), Ic2Items.iridiumPlate });
		RecipeHandler.instance().addChestpieceRecipes(overchargeModule);
		if (ComboArmors.config.craftCellModule)
			Recipes.advRecipes.addRecipe(new ItemStack(cellModule), new Object[] { "RTR", "AIA", "RTR", Character.valueOf('R'), "plateIron", Character.valueOf('T'), Ic2Items.casingtin, Character.valueOf('A'), Ic2Items.advancedCircuit, Character.valueOf('I'), Ic2Items.FluidCell });
		RecipeHandler.instance().addCellRecipes(cellModule);
		if (ComboArmors.config.useOverclocker)
			RecipeHandler.instance().addElectricRecipes(ComboArmors.ic2.getItemStack("overclockerUpgrade"));
		if (ComboArmors.config.useEnergyMk1)
			RecipeHandler.instance().addElectricRecipes(ComboArmors.ic2.getItemStack("energyStorageUpgrade"));
		if (ComboArmors.config.useTransformer)
			RecipeHandler.instance().addElectricRecipes(ComboArmors.ic2.getItemStack("transformerUpgrade"));
		if (ComboArmors.config.craftEnergyMk2)
			Recipes.advRecipes.addRecipe(new ItemStack(energyMk2), new Object[] { "WWW", "GEG", "WCW", Character.valueOf('W'), Blocks.planks, Character.valueOf('G'), Ic2Items.insulatedGoldCableItem, Character.valueOf('E'), StackUtil.copyWithWildCard(Ic2Items.energyCrystal), Character.valueOf('C'), Ic2Items.electronicCircuit });
		RecipeHandler.instance().addElectricRecipes(new ItemStack(energyMk2));
		if (ComboArmors.config.craftEnergyMk3)
			Recipes.advRecipes.addRecipe(new ItemStack(energyMk3), new Object[] { "WWW", "GEG", "WCW", Character.valueOf('W'), Blocks.planks, Character.valueOf('G'), Ic2Items.glassFiberCableItem, Character.valueOf('E'), StackUtil.copyWithWildCard(Ic2Items.lapotronCrystal), Character.valueOf('C'), Ic2Items.advancedCircuit });
		RecipeHandler.instance().addElectricRecipes(new ItemStack(energyMk3));*/
	}
}
