package com.zuxelus.comboarmors.blocks;

import java.util.Collections;
import java.util.List;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.tileentities.TileEntityArmorAssembler;
import com.zuxelus.zlib.blocks.FacingHorizontalActive;
import com.zuxelus.zlib.tileentities.TileEntityFacing;
import com.zuxelus.zlib.tileentities.TileEntityInventory;

import ic2.core.item.tool.ItemToolWrench;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockArmorAssembler extends FacingHorizontalActive {

	public BlockArmorAssembler() {
		super(Material.IRON);
		setHardness(3.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
		setCreativeTab(ComboArmors.creativeTab);
	}

	@Override
	protected TileEntityFacing createTileEntity(int meta) {
		return new TileEntityArmorAssembler();
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(pos);
		if (te == null || player.isSneaking())
			return false;

		ItemStack stack = player.getHeldItem(hand);
		if (!stack.isEmpty() && stack.getItem() instanceof ItemToolWrench)
			return false;
		if (!world.isRemote)
			player.openGui(ComboArmors.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}