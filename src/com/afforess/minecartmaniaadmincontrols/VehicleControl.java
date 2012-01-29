package com.afforess.minecartmaniaadmincontrols;

import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class VehicleControl {
    
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
}
