package com.zuxelus.comboarmors.items.armor;

import java.util.List;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;
import ic2.core.util.StackUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class ItemArmorExoJet extends ItemArmorTankUtility implements IJetpack {

	public ItemArmorExoJet(int renderIndex) {
		super(renderIndex, 1, BlocksItems.getFluid(InternalName.fluidBiogas), 30000);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return ComboArmors.MODID + ":textures/armor/exo_jet.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
		list.add(I18n.format("info.upgrade_module_installed"));
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		if (nbt.getBoolean("flight"))
			list.add(I18n.format("info.flight_turbine_installed"));
		super.addInformation(stack, player, list, advanced);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		ItemStack itemStack = new ItemStack(ModItems.exoJet, 1);
		fillTank(itemStack);
		itemStack.setItemDamage(1);
		list.add(itemStack);

		itemStack = new ItemStack(ModItems.exoJet, 1);
		itemStack.setItemDamage(getMaxDamage());
		list.add(itemStack);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (player.inventory.armorItemInSlot(2) != stack)
			return;
		NBTTagCompound nbtData = ItemNBTHelper.getOrCreateNbtData(stack);
		if (nbtData.getBoolean("isFlyActive"))
			return;
		boolean hoverMode = nbtData.getBoolean("hoverMode");
		byte toggleTimer = nbtData.getByte("toggleTimer");
		boolean jetpackUsed = false;
		if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
			toggleTimer = 10;
			hoverMode = !hoverMode;
			if (IC2.platform.isSimulating()) {
				nbtData.setBoolean("hoverMode", hoverMode);
				if (hoverMode)
					player.addChatMessage(new ChatComponentTranslation("info.hover_mode_enabled"));
				else
					player.addChatMessage(new ChatComponentTranslation("info.hover_mode_disabled"));
			}
		}
		if (IC2.keyboard.isJumpKeyDown(player) || hoverMode)
			jetpackUsed = useJetpack(player, stack, hoverMode, nbtData.getBoolean("isFlyActive"));
		if (IC2.platform.isSimulating() && toggleTimer > 0) {
			--toggleTimer;
			nbtData.setByte("toggleTimer", toggleTimer);
		}
		playAudioSource(player, jetpackUsed);
		if (jetpackUsed)
			player.inventoryContainer.detectAndSendChanges();
	}

	public boolean useJetpack(EntityPlayer player, ItemStack jetpack, boolean hoverMode, boolean boost) {
		if (getCharge(jetpack) <= 0.0D)
			return false;

		float power = 1.0F;
		float dropPercentage = 0.2F;
		if (getCharge(jetpack) / getCapacity(jetpack) <= dropPercentage)
			power = power * getCharge(jetpack) / getCapacity(jetpack) * dropPercentage;
		if (IC2.keyboard.isForwardKeyDown(player)) {
			float retruster = hoverMode ? 0.5F : 0.15F;
			float forwardpower = power * retruster * 2.0F;
			if (forwardpower > 0.0F) {
				if (boost)
					player.moveFlying(0.0F, 0.4F * forwardpower, 0.10F);
				else
					player.moveFlying(0.0F, 0.4F * forwardpower, 0.02F);
			}
		}
		int worldHeight = IC2.getWorldHeight(player.getEntityWorld());

		float y = (float) player.posY;
		if (y > worldHeight - 25) {
			if (y > worldHeight)
				y = worldHeight;
			power = power * (worldHeight - y) / 25.0F;
		}
		double prevmotion = player.motionY;
		player.motionY = Math.min(player.motionY + power * 0.2F, 0.6000000238418579D);
		if (hoverMode) {
			float maxHoverY = 0.0F;
			if (IC2.keyboard.isJumpKeyDown(player))
				maxHoverY = 0.2F;
			if (IC2.keyboard.isSneakKeyDown(player))
				maxHoverY = -0.2F;
			if (player.motionY > maxHoverY) {
				player.motionY = maxHoverY;
				if (prevmotion > player.motionY)
					player.motionY = prevmotion;
			}
		}
		int consume = hoverMode ? 1 : 2;
		drainFromJetpack(jetpack, consume);
		updateDamage(jetpack);

		player.fallDistance = 0.0F;
		player.distanceWalkedModified = 0.0F;
		IC2.platform.resetPlayerInAirTime(player);

		return true;
	}

	private boolean drainFromJetpack(ItemStack stack, int amount) {
		if (getFluid(stack) == null)
			return false;
		if (drain(stack, amount, false).amount < amount)
			return false;
		drain(stack, amount, true);
		return true;
	}

	@Override
	public void onFlyKeyPressed(EntityPlayer player, ItemStack stack) {
		flyKeyPressed(player, stack);
	}
}
