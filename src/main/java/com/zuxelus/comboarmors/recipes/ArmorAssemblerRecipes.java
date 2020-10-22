package com.zuxelus.comboarmors.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ArmorAssemblerRecipes {
	public static class Input {
		public final ItemStack i1;
		public final ItemStack i2;
		public Input(ItemStack input1, ItemStack input2) {
			i1 = input1;
			i2 = input2;
		}
		public boolean matches(ItemStack input1, ItemStack input2) {
			return i1.getItem() == input1.getItem() && i2.getItem() == input2.getItem();
		}
	}

	private static Map<Input, ItemStack> recipes = new HashMap();
	private static List<Item> items = new ArrayList();

	public static Map<Input, ItemStack> getRecipeList() {
		return recipes;
	}

	public static void addAssemblyRecipe(ItemStack result, ItemStack input1, Item input2) {
		addAssemblyRecipe(result, input1, new ItemStack(input2));
	}

	public static void addAssemblyRecipe(Item result, Item input1, ItemStack input2) {
		addAssemblyRecipe(new ItemStack(result), new ItemStack(input1), input2);
	}

	public static void addAssemblyRecipe(Item result, ItemStack input1, Item input2) {
		addAssemblyRecipe(new ItemStack(result), input1, new ItemStack(input2));
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
		if (result == null)
			throw new NullPointerException("The recipe output is null");
		if (input1 == null)
			throw new NullPointerException("The I1 recipe input is null");
		if (input2 == null)
			throw new NullPointerException("The I2 recipe input is null");

		InventoryBasic inv = new InventoryBasic(null, false, 2);
		ItemStack output = result.copy();
		inv.setInventorySlotContents(0, input1);
		inv.setInventorySlotContents(1, input2);
		RecipeHandler.onCrafting(output, inv);
		recipes.put(new Input(input1, input2), output);
		recipes.put(new Input(input2, input1), output);
	}

	public static List<Item> getItemList() {
		return items;
	}

	public static ItemStack getAssemblyResult(ItemStack input1, ItemStack input2) {
		if (input1 == null || input2 == null)
			return null;
		for (Map.Entry<Input, ItemStack> entry : recipes.entrySet()) {
			Input input = (Input) entry.getKey();
			if (input.matches(input1, input2))
				return (ItemStack) entry.getValue();
		}
		return null;
	}
}
