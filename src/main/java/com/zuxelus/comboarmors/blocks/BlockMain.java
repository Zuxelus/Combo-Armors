package com.zuxelus.comboarmors.blocks;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.utils.Util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.tile.IWrenchable;
import ic2.core.item.tool.ItemToolWrench;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockMain extends BlockContainer {
	private IIcon[] icons = new IIcon[4];

	public BlockMain() {
		super(Material.iron);
		setHardness(3.0F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeMetal);
		setCreativeTab(ComboArmors.creativeTab);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!(tile instanceof IWrenchable))
			return;
		IWrenchable te = (IWrenchable) tile;
		if (player == null) {
			te.setFacing((short) 2);
			return;
		} 
		int l = MathHelper.floor_double(player.rotationYaw * 4 / 360 + 0.5) & 3;
		switch (l) {
		case 0:
			te.setFacing((short) 2);
			break;
		case 1:
			te.setFacing((short) 5);
			break;
		case 2:
			te.setFacing((short) 3);
			break;
		case 3:
			te.setFacing((short) 4);
			break;
		}
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float what, float these, float are) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te == null || player.isSneaking())
			return false;

		ItemStack stack = player.getHeldItem();
		if (stack != null && stack.getItem() instanceof ItemToolWrench)
			return false;
		player.openGui(ComboArmors.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		if (side < 2)
			return icons[side];
		TileEntity te = iBlockAccess.getTileEntity(x, y, z);
		if (te instanceof IWrenchable)
			if (side == ((IWrenchable) te).getFacing())
				return icons[2]; // front
		return icons[3];
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side < 3)
			return icons[side];
		if (side == 3)
			return icons[2];
		return icons[3];
	}

	@Override
	public void registerBlockIcons(IIconRegister ir) {
		icons = Util.registerSides(ir, this);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		Util.dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}
}