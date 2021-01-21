package com.zuxelus.comboarmors.items.armor;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.zuxelus.comboarmors.utils.ItemNBTHelper;
import com.zuxelus.comboarmors.utils.TankFluidHandlerItemStack;

import ic2.core.util.Util;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemArmorTankUtility extends ItemArmorBase implements ISpecialArmor {
	protected final int capacity;
	protected final Fluid allowfluid;

	public ItemArmorTankUtility(EntityEquipmentSlot slot, Fluid allowfluid, int capacity) {
		super(slot);
		this.capacity = capacity;
		this.allowfluid = allowfluid;
		setMaxDamage(27);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		FluidStack fs = FluidUtil.getFluidContained(stack);
		if (fs != null)
			tooltip.add("< " + fs.getLocalizedName() + ", " + fs.amount + "/" + getCapacity(stack) + " mB >");
		else
			tooltip.add(I18n.format("ic2.item.FluidContainer.Empty"));
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
		NBTTagCompound tag = ItemNBTHelper.getOrCreateNbtData(stack);
		NBTTagCompound fluidTag = tag.getCompoundTag("Fluid");
		FluidStack fs = new FluidStack(allowfluid, getCapacity(stack));
		fs.writeToNBT(fluidTag);
		tag.setTag("Fluid", fluidTag);
	}

	@Override
	public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new TankFluidHandlerItemStack(stack, capacity);
	}

	protected double getCharge(ItemStack stack) {
		FluidStack fs = FluidUtil.getFluidContained(stack);
		if (fs == null)
			return 0.0D;
		double ret = fs.amount;
		return ret > 0.0D ? ret : 0.0D;
	}

	public int getCapacity(ItemStack stack) {
		NBTTagCompound tag = ItemNBTHelper.getOrCreateNbtData(stack);
		if (tag.hasKey("addCapacity"))
			return capacity + tag.getInteger("addCapacity");
		tag.setInteger("addCapacity", 0);
		return capacity;
	}

	public void updateDamage(ItemStack stack) {
		stack.setItemDamage(stack.getMaxDamage() - 1 - (int) Util.map(getCharge(stack), getCapacity(stack), stack.getMaxDamage() - 2));
	}
}
