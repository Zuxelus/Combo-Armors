package com.zuxelus.comboarmors.items.armor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.items.IItemUpgradeable;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;
import com.zuxelus.comboarmors.utils.Util;

import ic2.api.item.ElectricItem;
import ic2.api.item.HudMode;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import ic2.core.IC2Potion;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.item.ItemTinCan;
import ic2.core.util.StackUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemArmorElectricUtility extends ItemArmorBase implements ISpecialArmor, IElectricItem, IItemUpgradeable {
	public int defaultMaxCharge;
	public int transferLimit;
	public int tier;
	public boolean shareEnergy;

	private float jumpCharge;
	private static final Map<Potion, Integer> potionRemovalCost;

	static {
		potionRemovalCost = new HashMap();
		potionRemovalCost.put(MobEffects.POISON, 10000);
		potionRemovalCost.put(IC2Potion.radiation, 10000);
		potionRemovalCost.put(MobEffects.WITHER, 25000);
	}

	public ItemArmorElectricUtility(EntityEquipmentSlot slot, int maxCharge, int transferLimit, int tier, boolean shareEnergy) {
		super(slot);
		this.tier = tier;
		this.defaultMaxCharge = maxCharge;
		this.transferLimit = transferLimit;
		this.shareEnergy = shareEnergy;
		setMaxDamage(27);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		if (!nbt.getBoolean("loaded")) {
			if (nbt.getInteger("tier") == 0)
				nbt.setInteger("tier", getDefaultTier());
			if (nbt.getInteger("transferLimit") == 0)
				nbt.setInteger("transferLimit", getDefaultTransferLimit());
			if (nbt.getInteger("maxCharge") == 0)
				nbt.setInteger("maxCharge", getDefaultMaxCharge());
			nbt.setBoolean("loaded", true);
		}
		tooltip.add(I18n.format("info.upgrade_module_installed"));
		if (nbt.getBoolean("flight"))
			tooltip.add(I18n.format("info.flight_turbine_installed"));
		if (nbt.getBoolean("cloaking"))
			tooltip.add(I18n.format("info.cloaking_module_installed"));
		if (nbt.getBoolean("isCloakActive"))
			tooltip.add(TextFormatting.YELLOW + I18n.format("info.cloaking_module_active"));
		if (nbt.getBoolean("overcharge"))
			tooltip.add(I18n.format("info.discharge_module_installed"));
		if (nbt.getInteger("solarProd") > 0)
			tooltip.add(I18n.format("info.solar_produces", nbt.getInteger("solarProd") + 1));
		if (nbt.getInteger("staticProd") > 0)
			tooltip.add(I18n.format("info.static_produces", nbt.getInteger("staticProd") + 1));
		if (nbt.getInteger("transferLimit") != getDefaultTransferLimit())
			tooltip.add(I18n.format("info.transfer_speed", nbt.getInteger("transferLimit")));
		tooltip.add(I18n.format("info.power_tier", nbt.getInteger("tier")));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		ItemStack charged = new ItemStack(this, 1);
		ElectricItem.manager.charge(charged, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false);
		items.add(charged);
		items.add(new ItemStack(this, 1, getMaxDamage()));
	}

	private double getBaseAbsorptionRatio() {
		switch (armorType) {
		case HEAD:
			return 0.15D;
		case CHEST:
			return 0.4D;
		case LEGS:
			return 0.3D;
		case FEET:
			return 0.15D;
		default:
			return 0.0D;
		}
	}

	public abstract double getDamageAbsorptionRatio();

	public abstract int getEnergyPerDamage();

	public static double getCharge(ItemStack stack) {
		return ElectricItem.manager.getCharge(stack);
	}

	public static void discharge(ItemStack stack, double amount) {
		ElectricItem.manager.discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
	}

	private boolean useQuantumJetpack(EntityPlayer player, boolean hoverMode, boolean hoverModeQ, boolean boost) {
		ItemStack jetpack = player.inventory.armorItemInSlot(2);

		if (getCharge(jetpack) == 0)
			return false;

		float power = 1.0F;
		float dropPercentage = 0.05F;
		if (getCharge(jetpack) / getMaxCharge(jetpack) <= dropPercentage)
			power *= getCharge(jetpack) / getMaxCharge(jetpack) * dropPercentage;
		if (IC2.keyboard.isForwardKeyDown(player)) {
			float retruster = hoverModeQ ? 0.5F : hoverMode ? 1.0F : 3.5F;
			float forwardpower = power * retruster * 2.0F;
			if (forwardpower > 0) {
				if (boost)
					player.moveRelative(0.0F, 0, 0.4F * forwardpower, 0.10F);
				else
					player.moveRelative(0.0F, 0, 0.4F * forwardpower, 0.02F);
			}
		}
		int worldHeight = IC2.getWorldHeight(player.getEntityWorld());

		float y = (float) player.posY;
		if (y > worldHeight - 25) {
			if (y > worldHeight)
				y = worldHeight;
			power = power * (worldHeight - y) / 25;
		}
		double prevmotion = player.motionY;
		player.motionY = Math.min(player.motionY + power * 0.2F, 0.6000000238418579D);
		if (hoverMode || hoverModeQ) {
			float maxHoverY = hoverModeQ ? -0.025F : 0.0F;
			if (IC2.keyboard.isJumpKeyDown(player))
				maxHoverY = hoverModeQ ? 0.1F : 0.2F;
			if (IC2.keyboard.isSneakKeyDown(player))
				maxHoverY = hoverModeQ ? -0.1F : -0.2F;
			if (player.motionY > maxHoverY) {
				player.motionY = maxHoverY;
				if (prevmotion > player.motionY)
					player.motionY = prevmotion;
			}
		}
		double consume = hoverMode || hoverModeQ ? ComboArmors.config.jetpackEUAmount * 1.25D : ComboArmors.config.jetpackEUAmount;
		if (boost)
			consume += ComboArmors.config.turbineEUAmount;
		discharge(jetpack, consume);

		player.fallDistance = 0.0F;
		player.distanceWalkedModified = 0.0F;
		IC2.platform.resetPlayerInAirTime(player);

		return true;
	}

	protected void onFall(LivingFallEvent event, boolean isQuantum) {
		if (!IC2.platform.isSimulating() || !(event.getEntity() instanceof EntityLivingBase))
			return;
		
		EntityLivingBase entity = (EntityLivingBase) event.getEntity();
		ItemStack armor = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET);
		if (!armor.isEmpty() && armor.getItem() == this) {
			int distance = (int) event.getDistance();
			int fallDamage = isQuantum ? Math.max(distance - 10, 0) : distance - 3;
			if (!isQuantum && fallDamage >= 8)
				return;
			double energyCost = getEnergyPerDamage() * fallDamage;
			if (energyCost <= ElectricItem.manager.getCharge(armor)) {
				discharge(armor, energyCost);
				event.setCanceled(true);
			}
		}
	}

	protected boolean onQuantumJetpackTick(EntityPlayer player, NBTTagCompound nbtData) {
		if (nbtData.getBoolean("isFlyActive"))
			return false;
		byte toggleTimer = nbtData.getByte("toggleTimer");
		if (!nbtData.hasKey("jetpack"))
			nbtData.setBoolean("jetpack", true);
		boolean jetpack = nbtData.getBoolean("jetpack");
		boolean hoverMode = nbtData.getBoolean("hoverMode");
		boolean hoverModeQ = nbtData.getBoolean("hoverModeQ");
		boolean jetpackUsed = false;
		if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
			toggleTimer = 10;
			if (!hoverMode && !hoverModeQ) {
				hoverModeQ = !hoverModeQ;
				if (IC2.platform.isSimulating()) {
					nbtData.setBoolean("hoverModeQ", hoverModeQ);
					player.sendMessage(new TextComponentTranslation("info.quantum_hover_mode_enabled"));
				}
			} else if (!hoverMode && hoverModeQ) {
				hoverMode = !hoverMode;
				hoverModeQ = !hoverModeQ;
				if (IC2.platform.isSimulating()) {
					nbtData.setBoolean("hoverMode", hoverMode);
					nbtData.setBoolean("hoverModeQ", hoverModeQ);
					player.sendMessage(new TextComponentTranslation("info.hover_mode_enabled"));
				}
			} else {
				hoverMode = false;
				hoverModeQ = false;
				if (IC2.platform.isSimulating()) {
					nbtData.setBoolean("hoverMode", hoverMode);
					nbtData.setBoolean("hoverModeQ", hoverModeQ);
					player.sendMessage(new TextComponentTranslation("info.hover_mode_disabled"));
				}
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
		if (jetpack && (IC2.keyboard.isJumpKeyDown(player) || hoverMode || (hoverModeQ && player.motionY < -0.02999999932944775D)))
			jetpackUsed = useQuantumJetpack(player, hoverMode, hoverModeQ, nbtData.getBoolean("isFlyActive"));
		if (IC2.platform.isSimulating() && toggleTimer > 0) {
			--toggleTimer;
			nbtData.setByte("toggleTimer", toggleTimer);
		}
		playAudioSource(player, jetpackUsed);
		player.extinguish();
		return jetpackUsed;
	}

	protected boolean onQuantumBootsTick(EntityPlayer player, ItemStack stack) {
		NBTTagCompound nbtData = ItemNBTHelper.getOrCreateNbtData(stack);
		boolean flag = false;
		if (IC2.platform.isSimulating()) {
			boolean wasOnGround = nbtData.hasKey("wasOnGround") ? nbtData.getBoolean("wasOnGround") : true;
			if (wasOnGround && !player.onGround && IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
				ElectricItem.manager.use(stack, 4000, null);
				flag = true;
			}
			if (player.onGround != wasOnGround)
				nbtData.setBoolean("wasOnGround", player.onGround);
		} else {
			if (ElectricItem.manager.canUse(stack, 4000) && player.onGround)
				jumpCharge = 1.0F;
			if (player.motionY >= 0.0D && this.jumpCharge > 0.0F && !player.isInWater()) {
				if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
					if (this.jumpCharge == 1.0F) {
						player.motionX *= 3.5D;
						player.motionZ *= 3.5D;
					}
					player.motionY += jumpCharge * 0.3F;
					jumpCharge = jumpCharge * 0.75F;
				} else if (jumpCharge < 1.0F)
					jumpCharge = 0.0F;
			}
		}
		return flag;
	}

	protected static boolean onHelmetTick(EntityPlayer player, ItemStack stack) {
		boolean result = false;
		// Air
		int air = player.getAir();
		if (ElectricItem.manager.canUse(stack, 1000) && air < 100) {
			player.setAir(air + 200);
			ElectricItem.manager.use(stack, 1000, null);
			result = true;
		} else if (air <= 0)
			IC2.achievements.issueAchievement(player, "starveWithQHelmet");
		// Food
		if (ElectricItem.manager.canUse(stack, 1000) && player.getFoodStats().needFood()) {
			for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
				ItemStack can = player.inventory.mainInventory.get(i);
				if (!can.isEmpty() && can.isItemEqual(ComboArmors.ic2.getItemStack("filledTinCan"))) {
					ActionResult<ItemStack> res = ((ItemTinCan) can.getItem()).onEaten(player, can);
					can = (ItemStack) res.getResult();
					if (can.isEmpty())
						player.inventory.mainInventory.set(i, ItemStack.EMPTY);
					if (res.getType() == EnumActionResult.SUCCESS)
						ElectricItem.manager.use(stack, 1000.0D, null);
					result = true;
					break;
				}
			}
		} else if (player.getFoodStats().getFoodLevel() <= 0)
			IC2.achievements.issueAchievement(player, "starveWithQHelmet");
		// Potion
		for (PotionEffect effect : new LinkedList<PotionEffect>(player.getActivePotionEffects())) {
			Potion potion = effect.getPotion();
			Integer cost = potionRemovalCost.get(potion);
			if (cost != null) {
				cost = Integer.valueOf(cost.intValue() * (effect.getAmplifier() + 1));
				if (ElectricItem.manager.canUse(stack, cost.intValue())) {
					ElectricItem.manager.use(stack, cost.intValue(), null);
					IC2.platform.removePotion(player, potion);
				}
			}
		}
		return result;
	}

	protected static boolean onNightvisionTick(EntityPlayer player, ItemStack stack) {
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
		byte toggleTimer = nbtData.getByte("toggleTimer");
		boolean Nightvision = nbtData.getBoolean("Nightvision");
		if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
			toggleTimer = 10;
			Nightvision = !Nightvision;
			if (IC2.platform.isSimulating()) {
				nbtData.setBoolean("Nightvision", Nightvision);
				if (Nightvision)
					player.sendMessage(new TextComponentTranslation("info.nightvision_enabled"));
				else
					player.sendMessage(new TextComponentTranslation("info.nightvision_disabled"));
			}
		}
		int hubmode = nbtData.getInteger("HudMode");
		if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isHudModeKeyDown(player) && toggleTimer == 0) {
			toggleTimer = 10;
			hubmode = hubmode == HudMode.getMaxMode() ? 0 : hubmode + 1;
			if (IC2.platform.isSimulating()) {
				nbtData.setInteger("HudMode", hubmode);
				player.sendMessage(new TextComponentTranslation(HudMode.getFromID(hubmode).getTranslationKey()));
			}
		}
		if (IC2.platform.isSimulating() && toggleTimer > 0) {
			toggleTimer--;
			nbtData.setByte("toggleTimer", toggleTimer);
		}
		boolean result = false;
		if (Nightvision && IC2.platform.isSimulating() && ElectricItem.manager.use(stack, 1.0D, player)) {
			BlockPos pos = new BlockPos((int) Math.floor(player.posX), (int) Math.floor(player.posY), (int) Math.floor(player.posZ));
			int skylight = player.getEntityWorld().getLightFromNeighbors(pos);
			if (skylight > 8) {
				IC2.platform.removePotion(player, MobEffects.NIGHT_VISION);
				player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0, true, true));
			} else {
				IC2.platform.removePotion(player, MobEffects.BLINDNESS);
				player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, true, true));
			}
			result = true;
		}
		return result;
	}

	// ISpecialArmor
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if (source.isUnblockable())
			return new ArmorProperties(0, 0.0D, 0);
		double absorptionRatio = getBaseAbsorptionRatio() * getDamageAbsorptionRatio();
		int energyPerDamage = getEnergyPerDamage();
		int damageLimit = Integer.MAX_VALUE;
		if (energyPerDamage > 0)
			damageLimit = (int) Math.min(damageLimit, 25.0D * ElectricItem.manager.getCharge(armor) / energyPerDamage);
		return new ArmorProperties(0, absorptionRatio, damageLimit);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack stack, int slot) {
		if (ElectricItem.manager.getCharge(stack) >= getEnergyPerDamage())
			return (int) Math.round(20.0D * getBaseAbsorptionRatio() * getDamageAbsorptionRatio());
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		ElectricItem.manager.discharge(stack, damage * getEnergyPerDamage(), Integer.MAX_VALUE, true, false, false);
	}

	// IElectricItem
	@Override
	public boolean canProvideEnergy(ItemStack stack) {
		return shareEnergy;
	}

	@Override
	public double getMaxCharge(ItemStack stack) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		if (nbt.getInteger("maxCharge") == 0)
			nbt.setInteger("maxCharge", getDefaultMaxCharge());
		return nbt.getInteger("maxCharge");
	}

	@Override
	public int getTier(ItemStack stack) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		if (nbt.getInteger("tier") == 0)
			nbt.setInteger("tier", getDefaultTier());
		return nbt.getInteger("tier");
	}

	@Override
	public double getTransferLimit(ItemStack stack) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		if (nbt.getInteger("transferLimit") == 0)
			nbt.setInteger("transferLimit", getDefaultTransferLimit());
		return nbt.getInteger("transferLimit");
	}

	// IItemUpgradeable
	@Override
	public int getDefaultMaxCharge() {
		return defaultMaxCharge;
	}

	@Override
	public int getDefaultTier() {
		return tier;
	}

	@Override
	public int getDefaultTransferLimit() {
		return transferLimit;
	}

	@Override
	public int getMaxUpgradeableCharge() {
		return ComboArmors.config.maxEnergyUpgrades - getDefaultMaxCharge();
	}

	@Override
	public int getMaxUpgradeableTransfer() {
		return ComboArmors.config.maxTransferUpgrades - getDefaultTransferLimit();
	}
}
