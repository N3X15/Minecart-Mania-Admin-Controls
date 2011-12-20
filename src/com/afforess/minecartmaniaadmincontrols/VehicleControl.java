package com.afforess.minecartmaniaadmincontrols;

import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleListener;

import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class VehicleControl extends VehicleListener {
    
    public static void toggleBlockFromEntering(final Player player) {
        if (isBlockedFromEntering(player)) {
            MinecartManiaWorld.getMinecartManiaPlayer(player).setDataValue("Blocked From Entering Minecarts", null);
        } else {
            MinecartManiaWorld.getMinecartManiaPlayer(player).setDataValue("Blocked From Entering Minecarts", Boolean.TRUE);
        }
    }
    
    public static boolean isBlockedFromEntering(final Player player) {
        return MinecartManiaWorld.getMinecartManiaPlayer(player).getDataValue("Blocked From Entering Minecarts") != null;
    }
    
    public static int getMinecartKillTimer() {
        return (Integer) MinecartManiaWorld.getConfigurationValue("EmptyMinecartKillTimer");
    }
    
    public static int getStorageMinecartKillTimer() {
        return (Integer) MinecartManiaWorld.getConfigurationValue("EmptyStorageMinecartKillTimer");
    }
    
    public static int getPoweredMinecartKillTimer() {
        return (Integer) MinecartManiaWorld.getConfigurationValue("EmptyPoweredMinecartKillTimer");
    }
    
    @Override
    public void onVehicleEnter(final VehicleEnterEvent event) {
        if (event.isCancelled())
            return;
        if (event.getVehicle() instanceof Minecart) {
            if (event.getEntered() instanceof Player) {
                if (isBlockedFromEntering((Player) event.getEntered())) {
                    event.setCancelled(true);
                    ((Player) event.getEntered()).sendMessage(LocaleParser.getTextKey("AdminControlsBlockMinecartEntry"));
                }
            }
        }
    }
}
