package com.afforess.minecartmaniaadmincontrols.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.entity.MinecartManiaPlayer;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class StationCommand extends MinecartManiaCommand {
    
    public boolean isPlayerOnly() {
        return true;
    }
    
    public CommandType getCommand() {
        return CommandType.St;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player) sender;
        if (args.length < 1)
            return false;
        final MinecartManiaPlayer mmp = MinecartManiaWorld.getMinecartManiaPlayer(player);
        final String station = args[0];
        mmp.setLastStation(station);
        if (args.length > 1) {
            if (args[1].contains("s")) {
                mmp.setDataValue("Reset Station Data", Boolean.TRUE);
            }
        } else {
            mmp.setDataValue("Reset Station Data", null);
        }
        mmp.sendMessage(LocaleParser.getTextKey("AdminControlsStation", station));
        return true;
    }
    
}
