package com.zuxelus.comboarmors.items.armor;

import java.util.List;

import com.zuxelus.comboarmors.ComboArmors;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public abstract class ItemArmorTankUtility extends ItemArmorBase implements ISpecialArmor, IFluidContainerItem {
	protected final int capacity;
	protected final Fluid allowfluid;

	public ItemArmorTankUtility(int renderIndex, int piece, Fluid allowfluid, int capacity) {
		super(renderIndex, piece);
		this.capacity = capacity;
		this.allowfluid = allowfluid;
		setMaxDamage(27);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		FluidStack fs = getFluid(stack);
		if (fs != null)
			list.add("< " + FluidRegistry.getFluidName(fs) + ", " + fs.amount + "/" + getCapacity(stack) + " mB >");
		else
			list.add("< 0/" + getCapacity(stack) + " mB >");
	}

	// ISpecialArmor
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		return new ArmorProperties(0, 0.0D, 0);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) { }

	protected void fillTank(ItemStack stack) {
		NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
		NBTTagCompound fluidTag = tag.getCompoundTag("Fluid");
		FluidStack fs = new FluidStack(allowfluid, getCapacity(stack));
		fs.writeToNBT(fluidTag);
		tag.setTag("Fluid", fluidTag);
	}

	protected int getCharge(ItemStack stack) {
		if (getFluid(stack) == null)
			return 0;
		int ret = getFluid(stack).amount;
		return ret > 0 ? ret : 0;
	}

	// IFluidContainerItem
	@Override
	public FluidStack getFluid(ItemStack stack) {
		NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
		return FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Fluid"));
	}

	@Override
	public int getCapacity(ItemStack stack) {
		NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
		if (tag.hasKey("addCapacity"))
			return capacity + tag.getInteger("addCapacity");
		tag.setInteger("addCapacity", 0);
		return capacity;
	}

	@Override
	public int fill(ItemStack stack, FluidStack resource, boolean doFill) {
		if (stack.stackSize != 1)
			return 0;
		if (resource == null)
			return 0;
		if (resource.getFluid() != allowfluid)
			return 0;
		NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
		NBTTagCompound fluidTag = tag.getCompoundTag("Fluid");

		FluidStack fs = FluidStack.loadFluidStackFromNBT(fluidTag);
		if (fs == null)
			fs = new FluidStack(resource, 0);
		if (!fs.isFluidEqual(resource))
			return 0;
		int amount = Math.min(getCapacity(stack) - fs.amount, resource.amount);
		if (doFill && amount > 0) {
			fs.amount += amount;
			fs.writeToNBT(fluidTag);
			tag.setTag("Fluid", fluidTag);
		}
		updateDamage(stack);
		return amount;
	}

	@Override
	public FluidStack drain(ItemStack stack, int maxDrain, boolean doDrain) {
		if (stack.stackSize != 1)
			return null;

		NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
		NBTTagCompound fluidTag = tag.getCompoundTag("Fluid");
		FluidStack fs = FluidStack.loadFluidStackFromNBT(fluidTag);
		if (fs == null)
			return null;

		maxDrain = Math.min(fs.amount, maxDrain);
		if (doDrain) {
			fs.amount -= maxDrain;
			if (fs.amount <= 0)
				tag.removeTag("Fluid");
			else {
				fs.writeToNBT(fluidTag);
				tag.setTag("Fluid", fluidTag);
			}
		}
		updateDamage(stack);
		return new FluidStack(fs, maxDrain);
	}

	public void updateDamage(ItemStack stack) {
		stack.setItemDamage(stack.getMaxDamage() - 1 - (int) Util.map(getCharge(stack), getCapacity(stack), stack.getMaxDamage() - 2));
	}
}
