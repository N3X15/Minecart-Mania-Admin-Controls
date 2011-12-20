package com.afforess.minecartmaniaadmincontrols.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.utils.StringUtils;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class SetConfigurationKeyCommand extends MinecartManiaCommand {
    
    public boolean isPlayerOnly() {
        return false;
    }
    
    public CommandType getCommand() {
        return CommandType.SetConfigKey;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length != 2) {
            sender.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKeyUsage"));
            return true;
        }
        final String key = args[0];
        String value = args[1];
        if (value.equalsIgnoreCase("null")) {
            MinecartManiaWorld.setConfigurationValue(key, null);
            sender.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKey", key, "null"));
        } else {
            if (value.equalsIgnoreCase("true")) {
                sender.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKey", key, value));
                MinecartManiaWorld.setConfigurationValue(key, Boolean.TRUE);
            } else if (value.equalsIgnoreCase("false")) {
                sender.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKey", key, value));
                MinecartManiaWorld.setConfigurationValue(key, Boolean.FALSE);
            } else if (StringUtils.containsLetters(value)) {
                //save it as a string
                sender.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKey", key, value));
                MinecartManiaWorld.setConfigurationValue(key, value);
            } else {
                try {
                    value = StringUtils.getNumber(value);
                    final Double d = Double.valueOf(value);
                    if (d.intValue() == d) {
                        //try to save it as an int
                        sender.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKey", key, String.valueOf(d.intValue())));
                        MinecartManiaWorld.setConfigurationValue(key, new Integer(d.intValue()));
                    } else {
                        //save it as a double
                        sender.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKey", key, String.valueOf(d.doubleValue())));
                        MinecartManiaWorld.setConfigurationValue(key, d);
                    }
                } catch (final Exception e) {
                    sender.sendMessage(LocaleParser.getTextKey("AdminControlsInvalidValue"));
                }
            }
        }
        return true;
    }
    
}
