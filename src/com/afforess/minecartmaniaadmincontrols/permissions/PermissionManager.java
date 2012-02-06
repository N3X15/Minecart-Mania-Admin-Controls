package com.afforess.minecartmaniaadmincontrols.permissions;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class PermissionManager {
    
    private Plugin permissions = null;
    private PermissionHandler handler = null;
    
    public PermissionManager(final Server server) {
        permissions = server.getPluginManager().getPlugin("Permissions");
        
        if (permissions == null) {
            MinecartManiaLogger.getInstance().info("Permissions not found. Using OP for admin commands.");
        } else {
            
            try {
                handler = ((Permissions) permissions).getHandler();
                MinecartManiaLogger.getInstance().info("Permissions detected. Using permissions.");
            } catch (final Exception e) {
                MinecartManiaLogger.getInstance().severe("Permissions failed to load properly!");
            }
        }
    }
    
    public boolean isHasPermissions() {
        return handler != null;
    }
    
    public boolean canCreateSign(final Player player, final String sign) {
        if (player.isOp())
            return true;
        if (isHasPermissions())
            return handler.has(player, "minecartmania.signs.create." + sign);
        return true;
    }
    
    public boolean canBreakSign(final Player player, final String sign) {
        if (player.isOp())
            return true;
        if (isHasPermissions())
            return handler.has(player, "minecartmania.signs.break." + sign);
        return true;
    }
    
    public boolean canUseAdminCommand(final Player player, final String command) {
        if (player.isOp())
            return true;
        if (isHasPermissions())
            return handler.has(player, "minecartmania.commands." + command.toLowerCase());
        return false;
    }
    
    public boolean canUseCommand(final Player player, final String command) {
        if (player.isOp())
            return true;
        if (isHasPermissions())
            return handler.has(player, "minecartmania.commands." + command.toLowerCase());
        return true;
    }
    
}
