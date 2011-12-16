package com.afforess.minecartmaniaadmincontrols.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class GetConfigurationKeyCommand extends MinecartManiaCommand {
    
    public boolean isPlayerOnly() {
        return false;
    }
    
    public CommandType getCommand() {
        return CommandType.GetConfigKey;
    }
    
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(LocaleParser.getTextKey("AdminControlsConfigKeyUsage"));
            return true;
        }
        String key = args[0];
        Object value = MinecartManiaWorld.getConfigurationValue(key);
        sender.sendMessage(LocaleParser.getTextKey("AdminControlsConfigKey", key, value));
        return true;
    }
    
}
