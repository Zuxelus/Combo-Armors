package com.zuxelus.zlib.tileentities;

import com.zuxelus.zlib.containers.slots.ISlotItemFilter;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;

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

	@Override
	public void onLoad() {
		if (!addedToEnet && world != null && !world.isRemote && Info.isIc2Available()) {
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
		if (addedToEnet && world != null && !world.isRemote && Info.isIc2Available()) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			addedToEnet = false;
		}
	}

	protected void handleDischarger(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (!stack.isEmpty() && energy < capacity && stack.getItem() instanceof IElectricItem) {
			IElectricItem ielectricitem = (IElectricItem) stack.getItem();
			if (ielectricitem.canProvideEnergy(stack))
				energy += ElectricItem.manager.discharge(stack, capacity - energy, tier, false, false, false);
		}
	}

	// IEnergySink
	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing side) {
		return side != getFacing();
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
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
		if (energy >= capacity)
			return amount;
		energy += amount;
		return 0.0D;
	}
}
