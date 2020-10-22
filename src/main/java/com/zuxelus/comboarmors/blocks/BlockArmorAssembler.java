package com.zuxelus.comboarmors.blocks;

import com.zuxelus.comboarmors.tileentities.TileEntityArmorAssembler;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockArmorAssembler extends BlockMain {

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityArmorAssembler();
	}
}
