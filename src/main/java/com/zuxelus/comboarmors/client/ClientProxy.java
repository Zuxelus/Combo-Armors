package com.zuxelus.comboarmors.client;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.ServerProxy;
import com.zuxelus.comboarmors.config.ConfigHandler;
import com.zuxelus.comboarmors.containers.ContainerArmorAssembler;
import com.zuxelus.comboarmors.entities.EntityTechArrow;
import com.zuxelus.comboarmors.gui.GuiArmorAssembler;
import com.zuxelus.comboarmors.tileentities.TileEntityArmorAssembler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderTippedArrow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends ServerProxy {

	@Override
	public void loadConfig(FMLPreInitializationEvent event) {
		ComboArmors.config = new ConfigHandler();
		MinecraftForge.EVENT_BUS.register(ComboArmors.config);
		FMLCommonHandler.instance().bus().register(ComboArmors.config);
		ComboArmors.config.init(event.getSuggestedConfigurationFile());
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if (te instanceof TileEntityArmorAssembler)
			return new GuiArmorAssembler(new ContainerArmorAssembler(player, (TileEntityArmorAssembler) te));
		return null;
	}

	@Override
	public void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}
	
	@Override
	public void registerEntities() {
		RenderingRegistry.registerEntityRenderingHandler(EntityTechArrow.class, manager -> new RenderArrow<EntityTechArrow>(manager) {
					@Override
					protected ResourceLocation getEntityTexture(EntityTechArrow entity) {
						return RenderTippedArrow.RES_ARROW;
					}
				});
	}
}
