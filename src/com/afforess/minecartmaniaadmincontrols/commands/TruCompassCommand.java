package com.afforess.minecartmaniaadmincontrols.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.utils.DirectionUtils;

public class TruCompassCommand extends MinecartManiaCommand {
    
    public boolean isPlayerOnly() {
        return true;
    }
    
    public CommandType getCommand() {
        return CommandType.TruCompass;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        
        final int bearing = (int) ((Player) sender).getLocation().getYaw();
        final int corrBearing = (bearing + 360 + 180) % 360;
        
        final DirectionUtils.CompassDirection facingDir = DirectionUtils.getDirectionFromRotation(corrBearing);
        sender.sendMessage(ChatColor.YELLOW + "Bearing: " + facingDir.toString() + " (" + corrBearing + " degrees)");
        return true;
    }
    
}
