package com.afforess.minecartmaniaadmincontrols.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.utils.StringUtils;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class ThrottleCommand extends MinecartManiaCommand {
    
    public boolean isPlayerOnly() {
        return true;
    }
    
    public CommandType getCommand() {
        return CommandType.Throttle;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length != 1) {
            sender.sendMessage(LocaleParser.getTextKey("AdminControlsThrottleUsage"));
            return true;
        }
        final Player player = (Player) sender;
        if ((player.getVehicle() != null) && (player.getVehicle() instanceof Minecart)) {
            try {
                final String num = StringUtils.getNumber(args[0]);
                final double throttle = Double.valueOf(num);
                if (throttle >= 0.0D) {
                    final MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart) player.getVehicle());
                    minecart.setDataValue("throttle", throttle);
                    if (throttle <= 100D) {
                        sender.sendMessage(LocaleParser.getTextKey("AdminControlsThrottleSet"));
                    } else {
                        sender.sendMessage(LocaleParser.getTextKey("AdminControlsThrottleSetOverdrive"));
                    }
                } else {
                    sender.sendMessage(LocaleParser.getTextKey("AdminControlsThrottleUsage"));
                }
            } catch (final Exception e) {
                sender.sendMessage(LocaleParser.getTextKey("AdminControlsThrottleUsage"));
            }
            return true;
        }
        sender.sendMessage(LocaleParser.getTextKey("AdminControlsThrottleUsage"));
        return true;
    }
    
}
