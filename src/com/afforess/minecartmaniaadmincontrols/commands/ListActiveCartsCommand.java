package com.afforess.minecartmaniaadmincontrols.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class ListActiveCartsCommand extends MinecartManiaCommand {
    
    public boolean isPlayerOnly() {
        return false;
    }
    
    public CommandType getCommand() {
        return CommandType.ListActiveCarts;
    }
    
    // /mm listactivecarts [AutoMine]
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        String searchFor = "";
        if (args.length > 0) {
            searchFor = args[0];
        }
        final ArrayList<MinecartManiaMinecart> minecartList = MinecartManiaWorld.getMinecartManiaMinecartList();
        for (final MinecartManiaMinecart minecart : minecartList) {
            if (!minecart.isDead() && !minecart.minecart.isDead()) {
                if (fitsCriteria(minecart, searchFor)) {
                    sender.sendMessage(minecart.getLocation().toString());
                }
            }
        }
        return true;
    }
    
    private boolean fitsCriteria(final MinecartManiaMinecart minecart, final String searchFor) {
        return (searchFor.isEmpty() || (minecart.getDataValue(searchFor) != null));
    }
    
}
