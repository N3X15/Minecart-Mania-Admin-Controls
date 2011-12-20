package com.afforess.minecartmaniaadmincontrols.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniaadmincontrols.MinecartManiaAdminControls;
import com.afforess.minecartmaniaadmincontrols.VehicleControl;
import com.afforess.minecartmaniacore.config.LocaleParser;

public class EjectCommand extends MinecartManiaCommand {
    
    public boolean isPlayerOnly() {
        return false;
    }
    
    public CommandType getCommand() {
        return CommandType.Eject;
    }
    
    public boolean isPermenantEject() {
        return false;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length == 1) {
            final List<Player> matchingPlayers = MinecartManiaAdminControls.server.matchPlayer(args[0]);
            if (!matchingPlayers.isEmpty()) {
                for (final Player p : matchingPlayers) {
                    if (p.isInsideVehicle()) {
                        p.leaveVehicle();
                    }
                    if (isPermenantEject()) {
                        VehicleControl.toggleBlockFromEntering(p);
                    }
                }
            } else {
                sender.sendMessage(LocaleParser.getTextKey("AdminControlsNoPlayerFound"));
            }
        } else {
            sender.sendMessage(LocaleParser.getTextKey("AdminControlsEjectUsage"));
        }
        return true;
    }
}
