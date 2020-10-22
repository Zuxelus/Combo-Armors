package com.zuxelus.comboarmors.items;
/*package ic2ca.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.item.armor.ItemArmorCFPack;
import ic2.core.item.tool.ItemSprayer;
import ic2.core.util.LiquidUtil;

public class ItemExoSprayer extends ItemSprayer
{
	static enum Target { Any,  Scaffold,  Cable; }

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset)
	{
		if (IC2.keyboard.isModeSwitchKeyDown(player))
		{
			return false;
		}
		if (!IC2.platform.isSimulating())
		{
			return true;
		}
		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);
		if (movingobjectposition == null)
		{
			return false;
		}
		if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			int fx = movingobjectposition.blockX;
			int fy = movingobjectposition.blockY;
			int fz = movingobjectposition.blockZ;

			Block block = world.getBlock(fx, fy, fz);
			if ((block instanceof IFluidBlock))
			{
				IFluidBlock liquid = (IFluidBlock)block;
				if (liquid.canDrain(world, fx, fy, fz))
				{
					FluidStack fluid = liquid.drain(world, fx, fy, fz, false);
					int amount = LiquidUtil.fillContainerStack(stack, player, fluid, true);
					if (amount == fluid.amount)
					{
						LiquidUtil.fillContainerStack(stack, player, fluid, false);
						liquid.drain(world, fx, fy, fz, true);
						return true;
					}
				}
			}
		}
		int maxFoamBlocks = 0;

		FluidStack fluid = getFluid(stack);
		if (fluid != null && fluid.amount > 0)
		{
			maxFoamBlocks += fluid.amount / getFluidPerFoam();
		}
		ItemStack pack = player.inventory.armorInventory[2];
		if (pack != null && pack.getItem() == Ic2Items.cfPack.getItem())
		{
			fluid = ((ItemArmorCFPack)pack.getItem()).getFluid(pack);
			if (fluid != null && fluid.amount > 0) 
			{
				maxFoamBlocks += fluid.amount / getFluidPerFoam();
			} else {
				pack = null;
			}
		}
		else
		{
			pack = null;
		}
		if (maxFoamBlocks == 0)
		{
			return false;
		}
		maxFoamBlocks = Math.min(maxFoamBlocks, getMaxFoamBlocks(stack));
		ChunkPosition pos = new ChunkPosition(x, y, z);
		Target target;
		if (canPlaceFoam(world, pos, Target.Scaffold))
		{
			target = Target.Scaffold;
		}
		else
		{
			if (canPlaceFoam(world, pos, Target.Cable))
			{
				target = Target.Cable;
			}
			else
			{
				switch (side)
				{
				case 0: 
					y--; break;
				case 1: 
					y++; break;
				case 2: 
					z--; break;
				case 3: 
					z++; break;
				case 4: 
					x--; break;
				case 5: 
					x++; break;
				default: 
					if (!$assertionsDisabled) {
						throw new AssertionError();
					}
					break;
				}
				target = Target.Any;
			}
		}
		int amount = sprayFoam(world, x, y, z, calculateDirectionsFromPlayer(player), target, maxFoamBlocks);
		amount *= getFluidPerFoam();
		if (amount > 0)
		{
			if (pack != null)
			{
				fluid = ((ItemArmorCFPack)pack.getItem()).drain(pack, amount, true);
				amount -= fluid.amount;
			}
			if (amount > 0) {
				drain(stack, amount, true);
			}
			return true;
		}
		return false;
	}

}
*/