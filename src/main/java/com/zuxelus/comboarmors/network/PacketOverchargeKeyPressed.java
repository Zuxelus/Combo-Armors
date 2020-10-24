package com.zuxelus.comboarmors.network;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import ic2.api.item.ElectricItem;
import ic2.core.util.StackUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOverchargeKeyPressed implements IMessage, IMessageHandler<PacketOverchargeKeyPressed, IMessage> {
	private double x;
	private double y;
	private double z;

	public PacketOverchargeKeyPressed() { }

	public PacketOverchargeKeyPressed(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
	}

	@Override
	public IMessage onMessage(PacketOverchargeKeyPressed message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().player;
		ItemStack armor = player.inventory.armorItemInSlot(2);
		if (!armor.isEmpty() && ComboArmors.chests.contains(armor.getUnlocalizedName()))
			overcharge(player, armor, message.x, message.y, message.z);
		return null;
	}

	private static void overcharge(EntityPlayer player, ItemStack stack, double x, double y, double z) {
		NBTTagCompound tag = ItemNBTHelper.getOrCreateNbtData(stack);
		int charge = tag.getInteger("charge");
		int maxcharge = tag.getInteger("maxCharge");
		int overchargenumber = maxcharge / 10;
		int boltnumber = overchargenumber / 10000;
		if (boltnumber < 1) {
			player.sendMessage(new TextComponentTranslation("info.not_have_charge", stack.getDisplayName()));
			return;
		}
		if (boltnumber >= 10)
			boltnumber = 10;
		if (charge >= boltnumber * 10000) {
			for (int i = 1; i <= boltnumber; i++) {
				EntityLightningBolt elb = new EntityLightningBolt(player.world, x, y, z, false);
				player.world.spawnEntity(elb);
				ElectricItem.manager.discharge(stack, 10000, 4, true, false, false);
			}
			player.sendMessage(new TextComponentTranslation("info.discharged", boltnumber * 10000));
		} else
			player.sendMessage(new TextComponentTranslation("info.not_enough_energy_discharge"));
	}
}