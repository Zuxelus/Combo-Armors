package com.zuxelus.comboarmors.client;

import org.lwjgl.input.Keyboard;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.items.armor.IJetpack;
import com.zuxelus.comboarmors.network.ChannelHandler;
import com.zuxelus.comboarmors.network.PacketCloakKeyPressed;
import com.zuxelus.comboarmors.network.PacketFlyKeyPressed;
import com.zuxelus.comboarmors.network.PacketOverchargeKeyPressed;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import ic2.core.IC2;
import ic2.core.util.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class KeyHandler {
	public static KeyBinding flyKey = new KeyBinding("key.ic2ca_upgrades_key", Keyboard.KEY_GRAVE, "IC2CA");
	public Minecraft mc = Minecraft.getMinecraft();

	public KeyHandler() {
		ClientRegistry.registerKeyBinding(this.flyKey);
	}

	@SubscribeEvent
	public void onKeyPress(InputEvent.KeyInputEvent event) {
		if (!flyKey.isPressed() || !mc.inGameHasFocus)
			return;

		// Flight toggle
		if (!IC2.keyboard.isModeSwitchKeyDown(mc.thePlayer) && !mc.gameSettings.keyBindAttack.getIsKeyPressed())
			ChannelHandler.network.sendToServer(new PacketFlyKeyPressed());

		// Overcharge
		if (!IC2.keyboard.isModeSwitchKeyDown(mc.thePlayer) && mc.gameSettings.keyBindAttack.getIsKeyPressed()) {
			ItemStack armor = mc.thePlayer.inventory.armorInventory[2];
			if (armor != null && ComboArmors.chests.contains(armor.getUnlocalizedName())) {
				NBTTagCompound nbt = StackUtil.getOrCreateNbtData(armor);
				if (nbt.getBoolean("overcharge")) {
					double x = 0;
					double y = 0;
					double z = 0;
					MovingObjectPosition mop = mc.thePlayer.rayTrace(75.0D, 1.0F);
					if (mop != null) {
						Vec3 vec3 = mop.hitVec;
						x = vec3.xCoord;
						y = vec3.yCoord;
						z = vec3.zCoord;
					}
					ChannelHandler.network.sendToServer(new PacketOverchargeKeyPressed(x, y, z));
				}
			}
		}

		// Cloak toggle
		if (IC2.keyboard.isModeSwitchKeyDown(mc.thePlayer) && !mc.gameSettings.keyBindAttack.getIsKeyPressed())
			ChannelHandler.network.sendToServer(new PacketCloakKeyPressed());
	}
}
