package com.zuxelus.comboarmors.client;

import org.lwjgl.input.Keyboard;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.network.PacketCloakKeyPressed;
import com.zuxelus.comboarmors.network.PacketFlyKeyPressed;
import com.zuxelus.comboarmors.network.PacketOverchargeKeyPressed;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;
import com.zuxelus.zlib.network.NetworkHelper;

import ic2.core.IC2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyHandler {
	public static KeyBinding flyKey = new KeyBinding("key.ca_upgrades_key", Keyboard.KEY_GRAVE, ComboArmors.NAME);
	public Minecraft mc = Minecraft.getMinecraft();

	public KeyHandler() {
		ClientRegistry.registerKeyBinding(this.flyKey);
	}

	@SubscribeEvent
	public void onKeyPress(InputEvent.KeyInputEvent event) {
		if (!flyKey.isPressed() || !mc.inGameHasFocus)
			return;

		// Flight toggle
		if (!IC2.keyboard.isModeSwitchKeyDown(mc.player) && !mc.gameSettings.keyBindAttack.isKeyDown())
			NetworkHelper.network.sendToServer(new PacketFlyKeyPressed());

		// Overcharge
		if (!IC2.keyboard.isModeSwitchKeyDown(mc.player) && mc.gameSettings.keyBindAttack.isKeyDown()) {
			ItemStack armor = mc.player.inventory.armorInventory.get(2);
			if (!armor.isEmpty() && ComboArmors.chests.contains(armor.getUnlocalizedName())) {
				NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(armor);
				if (nbt.getBoolean("overcharge")) {
					double x = 0;
					double y = 0;
					double z = 0;
					RayTraceResult mop = mc.player.rayTrace(75.0D, 1.0F);
					if (mop != null) {
						Vec3d vec3 = mop.hitVec;
						x = vec3.x;
						y = vec3.y;
						z = vec3.z;
					}
					NetworkHelper.network.sendToServer(new PacketOverchargeKeyPressed(x, y, z));
				}
			}
		}

		// Cloak toggle
		if (IC2.keyboard.isModeSwitchKeyDown(mc.player) && !mc.gameSettings.keyBindAttack.isKeyDown())
			NetworkHelper.network.sendToServer(new PacketCloakKeyPressed());
	}
}
