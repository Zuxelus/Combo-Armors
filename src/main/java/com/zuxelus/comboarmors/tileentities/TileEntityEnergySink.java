package com.zuxelus.comboarmors.tileentities;

import com.zuxelus.comboarmors.containers.ISlotItemFilter;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityEnergySink extends TileEntityInventory implements IEnergySink, ISlotItemFilter, ITilePacketHandler {
	protected boolean addedToEnet;
	protected int tier;
	protected int output;
	protected double energy;
	protected double capacity;

	public TileEntityEnergySink(String name, int tier, int output, int capacity) {
		super(name);
		addedToEnet = false;
		energy = 0;
		this.tier = tier;
		this.output = output;
		this.capacity = capacity;
	}

	public double getEnergy() {
		return energy;
	}

	public int getEnergyFactor() {
		return (int) Math.round(energy / capacity * 14);
	}

	public String getEnergyString() {
		return "" + (int) (Math.min(1.0F, energy / capacity) * 100) + "%";
	}

	public int getOutput() {
		return output;
	}

	@Override
	public void setFacing(int meta) {
		onChunkUnload();
		super.setFacing(meta);
		onLoad();
	}

	public void setEnergy(double value) {
		energy = Math.min(value, capacity);
	}

	@Override
	protected void readProperties(NBTTagCompound tag) {
		super.readProperties(tag);
		energy = tag.getDouble("energy");
	}

	@Override
	protected NBTTagCompound writeProperties(NBTTagCompound tag) {
		tag = super.writeProperties(tag);
		tag.setDouble("energy", energy);
		return tag;
	}

	public void onLoad() {
		if (!addedToEnet && worldObj != null && !worldObj.isRemote && Info.isIc2Available()) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToEnet = true;
		}
	}

	@Override
	public void invalidate() {
		onChunkUnload();
		super.invalidate();
	}

	@Override
	public void onChunkUnload() {
		if (addedToEnet && worldObj != null && !worldObj.isRemote && Info.isIc2Available()) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			addedToEnet = false;
		}
	}

	protected void handleDischarger(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null && energy < capacity && stack.getItem() instanceof IElectricItem) {
			IElectricItem ielectricitem = (IElectricItem) stack.getItem();
			if (ielectricitem.canProvideEnergy(stack))
				energy += ElectricItem.manager.discharge(stack, capacity - energy, tier, false, false, false);
		}
	}

	// IEnergySink
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection dir) {
		return dir != getFacingForge();
	}

	@Override
	public double getDemandedEnergy() {
		return Math.min(capacity - energy, output);
	}

	@Override
	public int getSinkTier() {
		return tier;
	}

	@Override
	public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
		if (energy >= capacity)
			return amount;
		energy += amount;
		return 0.0D;
	}
}