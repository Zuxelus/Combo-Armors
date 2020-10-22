package com.zuxelus.comboarmors.nei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.gui.GuiArmorAssembler;
import com.zuxelus.comboarmors.recipes.ArmorAssemblerRecipes;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class ArmorAssemblerRecipeHandler extends TemplateRecipeHandler {
	int ticks;

	public class CachedArmorAssemblerRecipe extends TemplateRecipeHandler.CachedRecipe {
		public PositionedStack output;
		public List<PositionedStack> ingredients = new ArrayList();

		public CachedArmorAssemblerRecipe(ItemStack i1, ItemStack i2, ItemStack output) {
			super();
			ingredients.add(new PositionedStack(i1, 63, 1));
			ingredients.add(new PositionedStack(i2, 37, 1));
			this.output = new PositionedStack(output, 117, 19);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(ArmorAssemblerRecipeHandler.this.cycleticks / 20, ingredients);
		}

		@Override
		public PositionedStack getResult() {
			return output;
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiArmorAssembler.class;
	}

	@Override
	public String getGuiTexture() {
		return ComboArmors.MODID + ":textures/gui/gui_armor_assembler.png";
	}

	public String getRecipeId() {
		return ComboArmors.MODID + ".armor_assembler";
	}

	@Override
	public String getRecipeName() {
		return "Armor Assembler";
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (Map.Entry<ArmorAssemblerRecipes.Input, ItemStack> entry : ArmorAssemblerRecipes.getRecipeList().entrySet())
			if (NEIServerUtils.areStacksSameTypeCrafting(entry.getValue(), result)) {
				ArmorAssemblerRecipes.Input input = entry.getKey();
				arecipes.add(new CachedArmorAssemblerRecipe(input.i1, input.i2, entry.getValue()));
			}
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(getRecipeId())) {
			for (Map.Entry<ArmorAssemblerRecipes.Input, ItemStack> entry : ArmorAssemblerRecipes.getRecipeList().entrySet()) {
				ArmorAssemblerRecipes.Input input = entry.getKey();
				arecipes.add(new CachedArmorAssemblerRecipe(input.i1, input.i2, entry.getValue()));
			}
		} else
			super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for (Map.Entry<ArmorAssemblerRecipes.Input, ItemStack> entry : ArmorAssemblerRecipes.getRecipeList().entrySet()) {
			ArmorAssemblerRecipes.Input input = entry.getKey();
			if (input.i1.getItem() == ingredient.getItem() || input.i1.getItem() == ingredient.getItem())
				arecipes.add(new CachedArmorAssemblerRecipe(input.i1, input.i2, entry.getValue()));
		}
	}

	@Override
	public void drawBackground(int i) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 5, 16, 140, 65);
	}

	@Override
	public void drawExtras(int i) {
		float f = ticks >= 20 ? (ticks - 20) % 20 / 20.0F : 0.0F;
		drawProgressBar(74, 19, 176, 14, 25, 16, f, 0);
		f = ticks <= 20 ? ticks / 20.0F : 1.0F;
		drawProgressBar(51, 20, 176, 0, 14, 14, f, 3);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		ticks += 1;
	}
}
