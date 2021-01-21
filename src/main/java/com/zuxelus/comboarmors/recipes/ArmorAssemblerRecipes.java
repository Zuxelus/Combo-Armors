package com.zuxelus.comboarmors.recipes;

import java.util.ArrayList;
import java.util.List;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.items.IItemUpgradeable;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import ic2.api.item.IElectricItem;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ArmorAssemblerRecipes {

	public static class Recipe {
		public final ItemStack input1;
		public final ItemStack input2;
		public final ItemStack output;
		public Recipe(ItemStack input1, ItemStack input2, ItemStack output) {
			this.input1 = input1;
			this.input2 = input2;
			this.output = output;
		}

		public boolean matches(ItemStack i1, ItemStack i2) {
			return input1.getItem() == i1.getItem() && input2.getItem() == i2.getItem();
		}
	}

	private static List<Recipe> recipes = new ArrayList<Recipe>();
	private static List<Item> items = new ArrayList();

	public static List<Recipe> getRecipeList() {
		return recipes;
	}

	public static void addAssemblyRecipe(ItemStack result, ItemStack input1, Item input2) {
		addAssemblyRecipe(result.copy(), input1.copy(), new ItemStack(input2));
	}

	public static void addAssemblyRecipe(Item result, Item input1, ItemStack input2) {
		addAssemblyRecipe(new ItemStack(result), new ItemStack(input1), input2.copy());
	}

	public static void addAssemblyRecipe(Item result, ItemStack input1, Item input2) {
		addAssemblyRecipe(new ItemStack(result), input1.copy(), new ItemStack(input2));
	}

	public static void addAssemblyRecipe(Item result, Item input1, Item input2) {
		addAssemblyRecipe(new ItemStack(result), new ItemStack(input1), new ItemStack(input2));
	}

	public static void addAssemblyRecipe(ItemStack result, ItemStack input1, ItemStack input2) {
		addRecipe(result, input1, input2);
		if (!items.contains(input1.getItem()))
			items.add(input1.getItem());
		if (!items.contains(input2.getItem()))
			items.add(input2.getItem());
	}

	private static void addRecipe(ItemStack result, ItemStack input1, ItemStack input2) {
		if (result.isEmpty())
			throw new NullPointerException("The recipe output is null");
		if (input1.isEmpty())
			throw new NullPointerException("The recipe input 1 is null");
		if (input2.isEmpty())
			throw new NullPointerException("The recipe input 2 is null");

		InventoryBasic inv = new InventoryBasic(null, false, 2);
		ItemStack output = result.copy();
		inv.setInventorySlotContents(0, input1.copy());
		inv.setInventorySlotContents(1, input2.copy());
		RecipeHandler.onCrafting(output, inv);
		recipes.add(new Recipe(input1, input2, output));
		recipes.add(new Recipe(input2, input1, output));
	}

	public static List<Item> getItemList() {
		return items;
	}

	public static ItemStack getAssemblyResult(ItemStack input1, ItemStack input2) {
		if (input1.isEmpty() || input2.isEmpty())
			return ItemStack.EMPTY;
		for (Recipe recipe : recipes)
			if (recipe.matches(input1, input2) && checkRecipe(input1, input2, recipe.output))
				return recipe.output;
		return ItemStack.EMPTY;
	}

	private static boolean checkRecipe(ItemStack input1, ItemStack input2, ItemStack output) {
		if (output.getItem() instanceof IElectricItem && output.getItem() instanceof IItemUpgradeable) {
			if (input1.isItemEqual(ComboArmors.ic2.getItemStack("transformerUpgrade")) && input2.getItem() instanceof IElectricItem && input2.getItem() instanceof IItemUpgradeable) {
				NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(input2);
				if (nbt.getInteger("tier") == 1)
					return false;
			}
			if (input2.isItemEqual(ComboArmors.ic2.getItemStack("transformerUpgrade")) && input1.getItem() instanceof IElectricItem && input1.getItem() instanceof IItemUpgradeable) {
				NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(input1);
				if (nbt.getInteger("tier") == 1)
					return false;
			}
		}
		return true;
	}
}
