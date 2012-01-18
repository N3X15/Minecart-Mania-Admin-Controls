package com.afforess.minecartmaniaadmincontrols.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.afforess.minecartmaniacore.minecart.ChunkManager;

public class ChunksCommand extends MinecartManiaCommand {
    
    public boolean isPlayerOnly() {
        return false;
    }
    
    public CommandType getCommand() {
        return CommandType.Chunks;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        sender.sendMessage("Minecart Mania currently has " + ChunkManager.chunksLoaded() + " chunks loaded.");
        return true;
    }
    
}
