package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.util.BiomeUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraft.item.ItemStack;

public class ItemArmorBase extends ItemArmor {
	private static boolean lastJetpackUsed = false;
	private static AudioSource audioSource;

	public ItemArmorBase(EntityEquipmentSlot slot) {
		super(ArmorMaterial.DIAMOND, -1, slot);
		setMaxStackSize(1);
		setCreativeTab(ComboArmors.creativeTab);
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		return false;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	protected void playAudioSource(EntityPlayer player, boolean jetpackUsed) {
		if (!IC2.platform.isRendering() || player != IC2.platform.getPlayerInstance())
			return;
		if (lastJetpackUsed != jetpackUsed) {
			if (jetpackUsed) {
				if (audioSource == null)
					audioSource = IC2.audioManager.createSource(player, PositionSpec.Backpack, "Tools/Jetpack/JetpackLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
				if (audioSource != null)
					audioSource.play();
			} else if (audioSource != null) {
				audioSource.remove();
				audioSource = null;
			}
			lastJetpackUsed = jetpackUsed;
		}
		if (audioSource != null)
			audioSource.updatePosition();
	}

	protected static boolean onHelmetSolarTick(EntityPlayer player, ItemStack stack) {
		double chargeAmount = getSkyLight(player.getEntityWorld(), player.getPosition());
		if (chargeAmount == 0.0D)
			return false;

		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		double prod = nbt.getInteger("solarProd") > 0 ? (nbt.getInteger("solarProd") + 1) * chargeAmount : chargeAmount;
		boolean result = false;
		if (tryChargeSolar(player, ComboArmors.config.soPriority[0], prod))
			result = true;
		else if (tryChargeSolar(player, ComboArmors.config.soPriority[1], prod))
			result = true;
		else if (tryChargeSolar(player, ComboArmors.config.soPriority[2], prod))
			result = true;
		else if (tryChargeSolar(player, ComboArmors.config.soPriority[3], prod))
			result = true;
		return result;
	}

	private static float getSkyLight(World world, BlockPos pos) {
		if (world.provider.isNether())
			return 0.0F;
		float sunBrightness = Util.limit((float) Math.cos(world.getCelestialAngleRadians(1.0F)) * 2.0F + 0.2F, 0.0F, 1.0F);
		if (!BiomeDictionary.hasType(BiomeUtil.getBiome(world, pos), BiomeDictionary.Type.SANDY)) {
			sunBrightness *= 1.0F - world.getRainStrength(1.0F) * 5.0F / 16.0F;
			sunBrightness *= 1.0F - world.getThunderStrength(1.0F) * 5.0F / 16.0F;
			sunBrightness = Util.limit(sunBrightness, 0.0F, 1.0F);
		}
		return world.getLightFor(EnumSkyBlock.SKY, pos) / 15.0F * sunBrightness;
	}

	private static boolean tryChargeSolar(EntityPlayer player, int slot, double prod) {
		return ElectricItem.manager.charge(player.inventory.armorItemInSlot(slot), prod, Integer.MAX_VALUE, true, false) > 0;
	}

	protected void flyKeyPressed(EntityPlayer player, ItemStack stack) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		if (!nbt.hasKey("jetpack"))
			nbt.setBoolean("jetpack", true);
		nbt.setBoolean("jetpack", !nbt.getBoolean("jetpack"));
		if (IC2.platform.isSimulating())
			if (nbt.getBoolean("jetpack"))
				player.sendMessage(new TextComponentTranslation("info.jetpack_enabled"));
			else
				player.sendMessage(new TextComponentTranslation("info.jetpack_disabled"));

	}

	protected static boolean doStatic(EntityPlayer player, ItemStack stack) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		int prod = nbt.getInteger("staticProd");
		boolean isNotWalking = player.getRidingEntity() != null || player.isInWater();
		if (!nbt.hasKey("x") || isNotWalking)
			nbt.setInteger("x", (int) player.posX);
		if (!nbt.hasKey("z") || isNotWalking)
			nbt.setInteger("z", (int) player.posZ);
		double distance = Math.sqrt((nbt.getInteger("x") - player.posX) * (nbt.getInteger("x") - player.posX) + (nbt.getInteger("z") - player.posZ) * (nbt.getInteger("z") - player.posZ));
		if (distance < 5.0D)
			return false;

		nbt.setInteger("x", (int) player.posX);
		nbt.setInteger("z", (int) player.posZ);

		boolean result = false;
		NonNullList<ItemStack> armor = player.inventory.armorInventory;
		if (tryChargeStatic(armor, ComboArmors.config.stPriority[0], distance, prod))
			result = true;
		else if (tryChargeStatic(armor, ComboArmors.config.stPriority[1], distance, prod))
			result = true;
		else if (tryChargeStatic(armor, ComboArmors.config.stPriority[2], distance, prod))
			result = true;
		else if (tryChargeStatic(armor, ComboArmors.config.stPriority[3], distance, prod))
			result = true;
		return result;
	}

	private static boolean tryChargeStatic(NonNullList<ItemStack> armor, int slot, double distance, int prod) {
		if (!armor.get(slot).isEmpty() || !(armor.get(slot).getItem() instanceof IElectricItem))
			return false;
		return ElectricItem.manager.charge(armor.get(slot), Math.min(3, (int) distance / 5) + prod, Integer.MAX_VALUE, true, false) > 0;
	}
}
