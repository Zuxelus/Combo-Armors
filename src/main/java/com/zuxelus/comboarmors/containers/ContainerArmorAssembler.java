package com.zuxelus.comboarmors.containers;

import com.zuxelus.comboarmors.network.NetworkHelper;
import com.zuxelus.comboarmors.tileentities.TileEntityArmorAssembler;
import com.zuxelus.zlib.containers.ContainerBase;
import com.zuxelus.zlib.containers.slots.SlotFilter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerArmorAssembler extends ContainerBase<TileEntityArmorAssembler> {
	private double lastEnergy = -1;
	private int lastProduction = -1;

	public ContainerArmorAssembler(EntityPlayer player, TileEntityArmorAssembler te) {
		super(te);
		addSlotToContainer(new SlotFilter(te, TileEntityArmorAssembler.SLOT_INPUT1, 42, 17));
		addSlotToContainer(new SlotFilter(te, TileEntityArmorAssembler.SLOT_INPUT2, 68, 17));
		addSlotToContainer(new SlotFilter(te, TileEntityArmorAssembler.SLOT_OUTPUT, 122, 35));
		addSlotToContainer(new SlotFilter(te, TileEntityArmorAssembler.SLOT_UPGRADE, 150, 35));
		addSlotToContainer(new SlotFilter(te, TileEntityArmorAssembler.SLOT_DISCHARGER, 56, 53));
		addPlayerInventorySlots(player, 166);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		double energy = te.getEnergy();
		int production = te.getProduction();
		for (int i = 0; i < listeners.size(); i++)
			if (lastEnergy != energy || lastProduction != production) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("type", 1);
				tag.setDouble("energy", energy);
				tag.setInteger("production", production);
				NetworkHelper.updateClientTileEntity(listeners.get(i), te.getPos(), tag);
			}
		lastEnergy = energy;
		lastProduction = production;
	}
}
