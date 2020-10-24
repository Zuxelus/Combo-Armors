package com.zuxelus.comboarmors;

import java.util.ArrayList;
import java.util.List;

import com.zuxelus.comboarmors.items.armor.IJetpack;
import com.zuxelus.comboarmors.utils.InvisiblePotionEffect;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import ic2.api.item.ElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentTranslation;

public class ServerTickHandler {
	public static List<EntityPlayer> cloakingList = new ArrayList<EntityPlayer>();
	public static List<EntityPlayer> flyList = new ArrayList<EntityPlayer>();
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.START || event.side != event.side.SERVER)
			return;

		if (!(event.player instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) event.player;

		ItemStack armor = player.inventory.armorItemInSlot(2);
		if (flyList.contains(player))
			if (armor != null && armor.getItem() instanceof IJetpack) {
				NBTTagCompound tag = ItemNBTHelper.getOrCreateNbtData(armor);
				if (tag.getBoolean("flight") && tag.getBoolean("isFlyActive")) {
					if (!player.capabilities.isCreativeMode) {
						if (tag.getInteger("charge") < 10) {
							player.addChatMessage(new ChatComponentTranslation("Out of energy!"));
							disableFlyMode(player, tag);
						} else if (player.capabilities.isFlying)
							ElectricItem.manager.discharge(armor, ComboArmors.config.turbineEUAmount, 4, true, false, false);
					}
					player.fallDistance = 0.0F;
					if (player.posY > 262 && !player.capabilities.isCreativeMode)
						player.setPosition(player.posX, 262.0D, player.posZ);
				}
			} else
				disableFlyMode(player, new NBTTagCompound());

		if (cloakingList.contains(player)) {
			if (armor != null && ComboArmors.chests.contains(armor.getUnlocalizedName())) {
				NBTTagCompound tag = ItemNBTHelper.getOrCreateNbtData(armor);
				if (tag.getBoolean("cloaking") && tag.getBoolean("isCloakActive")) {
					if (!player.capabilities.isCreativeMode)
						if (tag.getInteger("charge") < 10) {
							player.addChatMessage(new ChatComponentTranslation("info.out_of_energy"));
							disableCloakMode(player, tag);
						} else
							ElectricItem.manager.discharge(armor, 10, 4, true, false, false);
				}
			} else
				disableCloakMode(player, new NBTTagCompound());
		}
	}

	public static void onPlayerLogin(EntityPlayer player) {
		ItemStack armor = player.inventory.armorItemInSlot(2);
		if (armor == null)
			return;

		NBTTagCompound tag = ItemNBTHelper.getOrCreateNbtData(armor);
		if (!player.isInvisible() && armor != null && ComboArmors.chests.contains(armor.getUnlocalizedName()))
			if (tag.getBoolean("cloaking") && tag.getBoolean("isCloakActive"))
				enableCloakMode(player, tag);
		if (armor != null && armor.getItem() instanceof IJetpack)
			if (tag.getBoolean("flight") && tag.getBoolean("isFlyActive"))
				enableFlyMode(player, tag);
	}

	public static boolean switchCloakMode(EntityPlayer player) {
		ItemStack armor = player.inventory.armorItemInSlot(2);
		if (armor == null || !ComboArmors.chests.contains(armor.getUnlocalizedName()))
			return false;
		
		NBTTagCompound tag = ItemNBTHelper.getOrCreateNbtData(armor);
		if (!tag.getBoolean("cloaking"))
			return false;
		
		if (tag.getBoolean("isCloakActive")) {
			disableCloakMode(player, tag);
			return false;
		}

		if (tag.getInteger("charge") < 10 && !player.capabilities.isCreativeMode) {
			player.addChatMessage(new ChatComponentTranslation("info.not_enough_energy_cloaking"));
			return false;
		}

		if (player.isInvisible())
			return false;

		enableCloakMode(player, tag);
		return true;
	}

	public static void disableCloakMode(EntityPlayer player, NBTTagCompound tag) {
		//player.setInvisible(false);
		player.removePotionEffect(Potion.invisibility.id);
		tag.setBoolean("isCloakActive", false);
		cloakingList.remove(player);
		player.addChatMessage(new ChatComponentTranslation("info.cloaking_module_disabled"));
	}

	public static void enableCloakMode(EntityPlayer player, NBTTagCompound tag) {
		//player.setInvisible(true);
		player.addPotionEffect(new InvisiblePotionEffect());
		tag.setBoolean("isCloakActive", true);
		cloakingList.add(player);
		player.addChatMessage(new ChatComponentTranslation("info.cloaking_module_enabled"));
	}

	public static void switchFlyMode(EntityPlayer player) {
		ItemStack armor = player.inventory.armorItemInSlot(2);
		if (armor == null || !(armor.getItem() instanceof IJetpack))
			return;
		
		NBTTagCompound tag = ItemNBTHelper.getOrCreateNbtData(armor);
		if (tag.getBoolean("flight")) {
			if (tag.getBoolean("isFlyActive"))
				disableFlyMode(player, tag);
			else {
				if (tag.getInteger("charge") < 10 && !player.capabilities.isCreativeMode)
					player.addChatMessage(new ChatComponentTranslation("info.not_enough_energy_flight"));
				else
					enableFlyMode(player, tag);
			}
		}/* else {
			IJetpack jet = (IJetpack) armor.getItem();
			jet.onFlyKeyPressed(player, armor);
		}*/
	}

	public static void disableFlyMode(EntityPlayer player, NBTTagCompound tag) {
		if (!player.capabilities.isCreativeMode) {
			player.capabilities.allowFlying = false;
			player.capabilities.isFlying = false;
			((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S39PacketPlayerAbilities(player.capabilities));
		}
		tag.setBoolean("isFlyActive", false);
		flyList.remove(player);
		player.addChatMessage(new ChatComponentTranslation("info.flight_mode_disabled"));
	}

	public static void enableFlyMode(EntityPlayer player, NBTTagCompound tag) {
		player.capabilities.allowFlying = true;
		player.capabilities.isFlying = true;
		((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S39PacketPlayerAbilities(player.capabilities));
		tag.setBoolean("isFlyActive", true);
		flyList.add(player);
		player.addChatMessage(new ChatComponentTranslation("info.flight_mode_enabled"));
	}
}
