package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import ic2.core.IC2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public abstract class ItemArmorBaseJetpack extends ItemArmorElectricUtility implements IJetpack {

	public ItemArmorBaseJetpack(EntityEquipmentSlot slot, int maxCharge, int transferLimit, int tier, boolean share) {
		super(slot, maxCharge, transferLimit, tier, share);
	}

	@Override
	public double getDamageAbsorptionRatio() {
		return 0.0D;
	}

	@Override
	public int getEnergyPerDamage() {
		return 0;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (player.inventory.armorItemInSlot(2) != stack)
			return;

		NBTTagCompound nbtData = ItemNBTHelper.getOrCreateNbtData(stack);
		if (nbtData.getBoolean("isFlyActive"))
			return;
		if (!nbtData.hasKey("jetpack"))
			nbtData.setBoolean("jetpack", true);
		boolean jetpack = nbtData.getBoolean("jetpack");

		byte toggleTimer = nbtData.getByte("toggleTimer");
		boolean hoverMode = nbtData.getBoolean("hoverMode");
		boolean jetpackUsed = false;
		if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
			toggleTimer = 10;
			hoverMode = !hoverMode;
			if (IC2.platform.isSimulating()) {
				nbtData.setBoolean("hoverMode", hoverMode);
				if (hoverMode)
					player.sendMessage(new TextComponentTranslation("info.hover_mode_enabled"));
				else
					player.sendMessage(new TextComponentTranslation("info.hover_mode_disabled"));
			}
		}
		if (IC2.keyboard.isBoostKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
			toggleTimer = 10;
			jetpack = !jetpack;
			if (IC2.platform.isSimulating()) {
				nbtData.setBoolean("jetpack", jetpack);
				if (jetpack)
					player.sendMessage(new TextComponentTranslation("info.jetpack_enabled"));
				else
					player.sendMessage(new TextComponentTranslation("info.jetpack_disabled"));
			}
		}
		if (jetpack && (IC2.keyboard.isJumpKeyDown(player) || hoverMode))
			jetpackUsed = useJetpack(player, hoverMode, true, nbtData.getBoolean("isFlyActive"));
		if (IC2.platform.isSimulating() && toggleTimer > 0) {
			toggleTimer--;
			nbtData.setByte("toggleTimer", toggleTimer);
		}
		playAudioSource(player, jetpackUsed);
		if (jetpackUsed)
			player.inventoryContainer.detectAndSendChanges();
	}

	private boolean useJetpack(EntityPlayer player, boolean hoverMode, boolean electric, boolean boost) {
		ItemStack jetpack = player.inventory.armorItemInSlot(2);

		if (getCharge(jetpack) <= 0)
			return false;

		float power = electric? 0.7F : 1.0F;
		float dropPercentage = electric? 0.05F : 0.2F;
		if (getCharge(jetpack) / getMaxCharge(jetpack) <= dropPercentage)
			power *= getCharge(jetpack) / getMaxCharge(jetpack) * dropPercentage;
		
		if (IC2.keyboard.isForwardKeyDown(player)) {
			float retruster = hoverMode ? 1.0F : 0.15F;
			if (electric) 
				retruster += 0.15F;
			float forwardpower = power * retruster * 2.0F;
			if (forwardpower > 0.0F) {
				if (boost)
					player.moveRelative(0.0F, 0.0F, 0.4F * forwardpower, 0.10F);
				else
					player.moveRelative(0.0F, 0.0F, 0.4F * forwardpower, 0.02F);
			}
		}
		int worldHeight = IC2.getWorldHeight(player.getEntityWorld());
		int maxFlightHeight = electric ? (int) (worldHeight / 1.28F) : worldHeight;

		float y = (float) player.posY;
		if (y > maxFlightHeight - 25) {
			if (y > maxFlightHeight)
				y = maxFlightHeight;
			power = power * (maxFlightHeight - y) / 25.0F;
		}
		double prevmotion = player.motionY;
		player.motionY = Math.min(player.motionY + power * 0.2F, 0.6000000238418579D);
		if (hoverMode) {
			float maxHoverY = 0.0F;
			if (IC2.keyboard.isJumpKeyDown(player))
				maxHoverY = electric ? 0.1F : 0.2F;
			if (IC2.keyboard.isSneakKeyDown(player))
				maxHoverY = electric ? -0.1F : -0.2F;
			if (player.motionY > maxHoverY) {
				player.motionY = maxHoverY;
				if (prevmotion > player.motionY)
					player.motionY = prevmotion;
			}
		}
		double consume = hoverMode ? 1 : 2;
		if (electric)
			consume = ComboArmors.config.jetpackEUAmount;
		if (boost)
			consume += ComboArmors.config.turbineEUAmount;
		if (!player.onGround)
			discharge(jetpack, consume);

		player.fallDistance = 0.0F;
		player.distanceWalkedModified = 0.0F;
		IC2.platform.resetPlayerInAirTime(player);

		return true;
	}

	@Override
	public void onFlyKeyPressed(EntityPlayer player, ItemStack stack) {
		flyKeyPressed(player, stack);
	}
}
