package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IMetalArmor;
import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class ItemArmorExoQuantum extends ItemArmorElectricUtility implements IMetalArmor, IJetpack {

	public ItemArmorExoQuantum(int renderIndex, int piece) {
		super(renderIndex, piece, 10000000, 12000, 4, false);
		if (piece == 3)
			MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (stack.getItem() == ModItems.exoQuantumHelm || stack.getItem() == ModItems.exoQuantumChest || stack.getItem() == ModItems.exoQuantumBoots)
			return ComboArmors.MODID + ":textures/armor/exo_quantum_1.png";
		return ComboArmors.MODID + ":textures/armor/exo_quantum_2.png";
	}

	@Override
	public double getDamageAbsorptionRatio() {
		return this.armorType == 1 ? 1.1D : 1.0D;
	}

	@Override
	public int getEnergyPerDamage() {
		return 20000;
	}

	@Override
	public int getItemTier() {
		return 4;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase entity, ItemStack armor, DamageSource source, double damage, int slot) {
		if (source == DamageSource.fall && armorType == 3) {
			int energyPerDamage = getEnergyPerDamage();
			int damageLimit = Integer.MAX_VALUE;
			if (energyPerDamage > 0)
				damageLimit = (int) Math.min(damageLimit, 25.0D * ElectricItem.manager.getCharge(armor) / energyPerDamage);
			return new ArmorProperties(10, 1.0D, damageLimit);
		}
		return super.getProperties(entity, armor, source, damage, slot);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.rare;
	}

	@Override
	public boolean isMetalArmor(ItemStack stack, EntityPlayer player) {
		return true;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		NBTTagCompound nbtData = ItemNBTHelper.getOrCreateNbtData(stack);
		boolean updated = false;
		switch (armorType) {
		case 0:
			if (onHelmetTick(player, stack))
				updated = true;
			if (onNightvisionTick(player, stack))
				updated = true;
			break;
		case 1:
			updated = onQuantumJetpackTick(player, nbtData);
			break;
		case 2:
			updated = onQuantumLeggingsTick(player, stack, nbtData);
			break;
		case 3:
			updated = onQuantumBootsTick(player, stack);
			break;
		}
		if (updated)
			player.inventoryContainer.detectAndSendChanges();
	}

	@SubscribeEvent
	public void onEntityLivingFallEvent(LivingFallEvent event) {
		onFall(event, true);
	}

	private boolean onQuantumLeggingsTick(EntityPlayer player, ItemStack stack, NBTTagCompound nbtData) {
		boolean result = false;
		boolean enableQuantumSpeedOnSprint = true;
		if (IC2.platform.isRendering())
			enableQuantumSpeedOnSprint = ConfigUtil.getBool(MainConfig.get(), "misc/quantumSpeedOnSprint");
		if (ElectricItem.manager.canUse(stack, 1000) && (player.onGround || player.isInWater()) && IC2.keyboard.isForwardKeyDown(player) && 
				((enableQuantumSpeedOnSprint && player.isSprinting()) || (!enableQuantumSpeedOnSprint && IC2.keyboard.isBoostKeyDown(player)))) {
			byte speedTicker = nbtData.getByte("speedTicker");
			speedTicker++;
			if (speedTicker >= 10) {
				speedTicker = 0;
				ElectricItem.manager.use(stack, 1000, null);
				result = true;
			}
			nbtData.setByte("speedTicker", speedTicker);
			float speed = 0.22F;
			if (player.isInWater()) {
				speed = 0.1F;
				if (IC2.keyboard.isJumpKeyDown(player))
					player.motionY += 0.10000000149011612D;
			}
			if (speed > 0.0F)
				player.moveFlying(0.0F, 1.0F, speed);
		}
		return result;
	}

	@Override
	public void onFlyKeyPressed(EntityPlayer player, ItemStack stack) {
		flyKeyPressed(player, stack);
	}
}
