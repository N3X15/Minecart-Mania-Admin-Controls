package com.afforess.minecartmaniaadmincontrols.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class MinecartInfoCommand extends MinecartManiaCommand {
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        MinecartManiaWorld.pruneMinecarts();
        final ArrayList<MinecartManiaMinecart> minecarts = MinecartManiaWorld.getMinecartManiaMinecartList();
        final int total = minecarts.size();
        int empty, passenger, powered, storage, moving, unmoving;
        empty = passenger = powered = storage = moving = unmoving = 0;
        final HashMap<String, Integer> owners = new HashMap<String, Integer>();
        for (final MinecartManiaMinecart minecart : minecarts) {
            if (minecart.isStandardMinecart()) {
                if (minecart.hasPlayerPassenger()) {
                    passenger++;
                } else {
                    empty++;
                }
            } else if (minecart.isPoweredMinecart()) {
                powered++;
            } else if (minecart.isStorageMinecart()) {
                storage++;
            }
            if (minecart.isMoving()) {
                moving++;
            } else {
                unmoving++;
            }
            if (minecart.getOwner() instanceof Player) {
                final String name = ((Player) minecart.getOwner()).getName();
                if (owners.containsKey(name)) {
                    owners.put(name, owners.get(name) + 1);
                } else {
                    owners.put(name, 1);
                }
            }
        }
        
        String most = null;
        int mostCarts = 0;
        final Iterator<Entry<String, Integer>> i = owners.entrySet().iterator();
        while (i.hasNext()) {
            final Entry<String, Integer> e = i.next();
            if ((most == null) || (e.getValue() > mostCarts)) {
                most = e.getKey();
                mostCarts = e.getValue();
            }
        }
        
        sender.sendMessage(LocaleParser.getTextKey("AdminControlsMMHeader"));
        sender.sendMessage(LocaleParser.getTextKey("AdminControlsInfoTotalMinecarts", total));
        sender.sendMessage(LocaleParser.getTextKey("AdminControlsInfoEmptyMinecarts", empty));
        sender.sendMessage(LocaleParser.getTextKey("AdminControlsInfoOccupiedMinecarts", passenger));
        sender.sendMessage(LocaleParser.getTextKey("AdminControlsInfoPoweredMinecarts", powered));
        sender.sendMessage(LocaleParser.getTextKey("AdminControlsInfoStorageMinecarts", storage));
        sender.sendMessage(LocaleParser.getTextKey("AdminControlsInfoMovingMinecarts", moving));
        sender.sendMessage(LocaleParser.getTextKey("AdminControlsInfoStalledMinecarts", unmoving));
        if (most != null) {
            sender.sendMessage(LocaleParser.getTextKey("AdminControlsInfoMostOwnedMinecarts", most, owners.get(most)));
        }
        
        return true;
    }
    
    public boolean isPlayerOnly() {
        return false;
    }
    
    public CommandType getCommand() {
        return CommandType.Info;
    }
    
}
