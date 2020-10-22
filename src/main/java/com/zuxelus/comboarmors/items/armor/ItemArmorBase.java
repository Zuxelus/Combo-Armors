package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.utils.Util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.util.StackUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

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
		if (player.isClientWorld() || !isSunVisible(player.worldObj, (int) player.posX, (int) player.posY + 1, (int) player.posZ))
			return false;
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
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
		return ElectricItem.manager.charge(player.inventory.armorInventory[slot], prod, Integer.MAX_VALUE, true, false) > 0;
	}

	protected void flyKeyPressed(EntityPlayer player, ItemStack stack) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		if (!nbt.hasKey("jetpack"))
			nbt.setBoolean("jetpack", true);
		nbt.setBoolean("jetpack", !nbt.getBoolean("jetpack"));
		if (IC2.platform.isSimulating())
			if (nbt.getBoolean("jetpack"))
				player.addChatMessage(new ChatComponentTranslation("info.jetpack_enabled"));
			else
				player.addChatMessage(new ChatComponentTranslation("info.jetpack_disabled"));

	}
}
