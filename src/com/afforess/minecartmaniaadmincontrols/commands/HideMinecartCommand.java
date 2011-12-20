package com.afforess.minecartmaniaadmincontrols.commands;

import java.util.ArrayList;

import net.minecraft.server.Packet;
import net.minecraft.server.Packet29DestroyEntity;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class HideMinecartCommand extends MinecartManiaCommand {
    
    public boolean isPlayerOnly() {
        return false;
    }
    
    public CommandType getCommand() {
        return CommandType.Hide;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player[] online = Bukkit.getServer().getOnlinePlayers();
        final ArrayList<MinecartManiaMinecart> minecarts = MinecartManiaWorld.getMinecartManiaMinecartList();
        for (final Player p : online) {
            final CraftPlayer player = (CraftPlayer) p;
            for (final MinecartManiaMinecart minecart : minecarts) {
                final Packet packet = new Packet29DestroyEntity(minecart.minecart.getEntityId());
                player.getHandle().netServerHandler.sendPacket(packet);
            }
        }
        sender.sendMessage(LocaleParser.getTextKey("AdminControlsHideMinecarts"));
        return true;
    }
    
}
