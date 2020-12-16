package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;
import com.zuxelus.comboarmors.utils.Util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class ItemArmorBase extends ItemArmor {
	private static boolean lastJetpackUsed = false;
	private static AudioSource audioSource;

	public ItemArmorBase(int renderIndex, int piece) {
		super(ArmorMaterial.DIAMOND, renderIndex, piece);
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

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		itemIcon = Util.register(ir, this);
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
		if (FMLCommonHandler.instance().getEffectiveSide().isClient() || !isSunVisible(player.worldObj, (int) player.posX, (int) player.posY + 1, (int) player.posZ))
			return false;
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		int prod = nbt.getInteger("solarProd") > 0 ? nbt.getInteger("solarProd") + 1 : 1;
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

	private static boolean isSunVisible(World world, int x, int y, int z) {
		return (world.getBlockLightValue(x, 255, z) > 4 && !world.provider.hasNoSky && world.canBlockSeeTheSky(x, y, z)
				&& (world.getWorldChunkManager().getBiomeGenAt(x, z) instanceof net.minecraft.world.biome.BiomeGenDesert || (!world.isRaining() && !world.isThundering())));
	}

	private static boolean tryChargeSolar(EntityPlayer player, int slot, int prod) {
		if (player.inventory.armorInventory[slot] == null)
			return false;
		return ElectricItem.manager.charge(player.inventory.armorInventory[slot], prod, Integer.MAX_VALUE, true, false) > 0;
	}

	protected void flyKeyPressed(EntityPlayer player, ItemStack stack) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		if (!nbt.hasKey("jetpack"))
			nbt.setBoolean("jetpack", true);
		nbt.setBoolean("jetpack", !nbt.getBoolean("jetpack"));
		if (IC2.platform.isSimulating())
			if (nbt.getBoolean("jetpack"))
				player.addChatMessage(new ChatComponentTranslation("info.jetpack_enabled"));
			else
				player.addChatMessage(new ChatComponentTranslation("info.jetpack_disabled"));

	}

	protected static boolean doStatic(EntityPlayer player, ItemStack stack) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		int prod = nbt.getInteger("staticProd");
		boolean isNotWalking = player.ridingEntity != null || player.isInWater();
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
		ItemStack[] armor = player.inventory.armorInventory;
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

	private static boolean tryChargeStatic(ItemStack[] armor, int slot, double distance, int prod) {
		if (armor[slot] == null || !(armor[slot].getItem() instanceof IElectricItem))
			return false;
		return ElectricItem.manager.charge(armor[slot], Math.min(3, (int) distance / 5) + prod, Integer.MAX_VALUE, true, false) > 0;
	}
}
