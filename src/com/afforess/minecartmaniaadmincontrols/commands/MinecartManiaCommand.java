package com.afforess.minecartmaniaadmincontrols.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniaadmincontrols.MinecartManiaAdminControls;

public abstract class MinecartManiaCommand implements Command {
    
    public boolean canExecuteCommand(final CommandSender sender) {
        if (!(sender instanceof Player) && isPlayerOnly())
            return false;
        if (sender.isOp())
            return true;
        if (!(sender instanceof Player))
            return false;
        final String command = getCommand().name();
        if (getCommand().isAdminCommand())
            return MinecartManiaAdminControls.permissions.canUseAdminCommand((Player) sender, command);
        return MinecartManiaAdminControls.permissions.canUseCommand((Player) sender, command);
    }
    
    public boolean isValidCommand(final String command, final String[] args) {
        return command.equalsIgnoreCase(getCommand().name());
    }
    
}
