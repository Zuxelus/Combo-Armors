package com.zuxelus.comboarmors.gui;

import org.lwjgl.opengl.GL11;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.containers.ContainerArmorAssembler;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiArmorAssembler extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(ComboArmors.MODID, "textures/gui/gui_armor_assembler.png");
	private String name;
	private ContainerArmorAssembler container;

	public GuiArmorAssembler(ContainerArmorAssembler container) {
		super(container);
		this.container = container;
		name = I18n.format("tile.armor_assembler.name");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(TEXTURE);
		int left = (width - xSize) / 2;
		int top = (height - ySize) / 2;
		drawTexturedModalRect(left, top, 0, 0, xSize, ySize);

		int energyHeight = container.te.getEnergyFactor();
		if (energyHeight > 0)
			drawTexturedModalRect(left + 56, top + 36 + 14 - energyHeight, 176, 14 - energyHeight, 14, energyHeight);
		int productionWidth = container.te.getProductionFactor();
		if (productionWidth > 0)
			drawTexturedModalRect(left + 79, top + 34, 176, 14, productionWidth + 1, 16);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString(name, (xSize - fontRenderer.getStringWidth(name)) / 2, 6, 0x404040);
		fontRenderer.drawString(I18n.format("info.inventory"), 8, this.ySize - 96 + 2, 0x404040);
		fontRenderer.drawString(container.te.getEnergyString(), 56 - fontRenderer.getStringWidth(container.te.getEnergyString()), 39, 0x404040);
		//fontRendererObj.drawString(container.te.getProgressString(), xSize - fontRendererObj.getStringWidth(container.te.getProgressString()) - 8, ySize - 96 - 8, 0x404040);
		fontRenderer.drawString(container.te.getTimeString(), xSize - fontRenderer.getStringWidth(container.te.getTimeString()) - 8, ySize - 96 + 2, 0x404040);
	}
}
