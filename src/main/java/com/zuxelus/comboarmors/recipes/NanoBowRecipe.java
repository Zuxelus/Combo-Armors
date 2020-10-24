package com.zuxelus.comboarmors.recipes;

import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import ic2.api.item.IC2Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public class NanoBowRecipe extends ShapedRecipes {

	public NanoBowRecipe() {
		super("", 3, 3, NonNullList.withSize(9, Ingredient.EMPTY), new ItemStack(ModItems.nanoBow));
		recipeItems.set(1, Ingredient.fromStacks(IC2Items.getItem("crafting", "carbon_plate")));
		recipeItems.set(2, Ingredient.fromStacks(IC2Items.getItem("cable", "type:glass,insulation:0")));
		ItemStack stack = new ItemStack(IC2Items.getItem("energy_crystal").getItem(), 1, Short.MAX_VALUE);
		recipeItems.set(3, Ingredient.fromStacks(stack));
		recipeItems.set(4, Ingredient.EMPTY);
		recipeItems.set(5, Ingredient.fromStacks(IC2Items.getItem("cable", "type:glass,insulation:0")));
		recipeItems.set(6, Ingredient.EMPTY);
		recipeItems.set(7, Ingredient.fromStacks(IC2Items.getItem("crafting", "carbon_plate")));
		recipeItems.set(8, Ingredient.fromStacks(IC2Items.getItem("cable", "type:glass,insulation:0")));
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack stack = inv.getStackInSlot(3);
		if (stack.isEmpty() || stack.getItem() != IC2Items.getItem("energy_crystal").getItem())
			return ItemStack.EMPTY;
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null || !tag.hasKey("charge"))
			return new ItemStack(ModItems.nanoBow);
		double energy = tag.getDouble("charge");
		return ItemNBTHelper.getStackWithEnergy(ModItems.nanoBow, "charge", Math.min(energy, 40000.0D));
	}
}
