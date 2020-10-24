package com.zuxelus.comboarmors.jei;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.recipes.ArmorAssemblerRecipes;
import com.zuxelus.comboarmors.recipes.ArmorAssemblerRecipes.Recipe;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

@JEIPlugin
public class JEIModPlugin implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new ArmorAssemblerRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void register(IModRegistry registry) {
		registry.addRecipes(ArmorAssemblerRecipes.getRecipeList(), ArmorAssemblerRecipeCategory.UID);
		registry.handleRecipes(Recipe.class, recipe -> new ArmorAssemblerRecipeWrapper(recipe), ArmorAssemblerRecipeCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(ModItems.armorAssembler), ArmorAssemblerRecipeCategory.UID);
	}

	public class ArmorAssemblerRecipeCategory implements IRecipeCategory<ArmorAssemblerRecipeWrapper> {
		public static final String UID = ComboArmors.MODID + ":armor_assembler";
		private final IDrawable background;

		public ArmorAssemblerRecipeCategory(IGuiHelper guiHelper) {
			background = guiHelper.createDrawable(new ResourceLocation(ComboArmors.MODID,"textures/gui/gui_armor_assembler.png"), 36, 12, 135, 62);
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}

		@Override
		public String getModName() {
			return ComboArmors.MODID;
		}

		@Override
		public String getTitle() {
			return I18n.format("tile.armor_assembler.name");
		}

		@Override
		public String getUid() {
			return UID;
		}

		@Override
		public void drawExtras(Minecraft mc) { }

		@Override
		public void setRecipe(IRecipeLayout layout, ArmorAssemblerRecipeWrapper recipes, IIngredients ingredients) {
			List<ItemStack> inputs = recipes.getInputs();
			IGuiItemStackGroup isg = layout.getItemStacks();
			isg.init(0, true, 5, 4);
			isg.set(0, inputs.get(0));
			isg.init(1, true, 31, 4);
			isg.set(1, inputs.get(1));
			isg.init(2, false, 85, 22);
			isg.set(2, recipes.getOutput());
		}
	}

	public class ArmorAssemblerRecipeWrapper implements IRecipeWrapper {
		private List<ItemStack> inputs;
		private ItemStack output;

		public ArmorAssemblerRecipeWrapper(Recipe recipe) {
			inputs =  Arrays.asList(recipe.input1, recipe.input2);
			output = recipe.output;
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputLists(ItemStack.class, Collections.singletonList(inputs));
			ingredients.setOutput(ItemStack.class, output);
		}

		public List<ItemStack> getInputs() {
			return inputs;
		}

		public ItemStack getOutput() {
			return output;
		}
	}
}

