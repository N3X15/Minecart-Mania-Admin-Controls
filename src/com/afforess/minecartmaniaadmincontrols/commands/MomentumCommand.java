package com.afforess.minecartmaniaadmincontrols.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class MomentumCommand extends MinecartManiaCommand {
    
    public boolean isPlayerOnly() {
        return true;
    }
    
    public CommandType getCommand() {
        return CommandType.Momentum;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player) sender;
        if (player.getVehicle() instanceof Minecart) {
            final MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart) player.getVehicle());
            player.sendMessage(LocaleParser.getTextKey("AdminControlsMomentum", (float) minecart.getMotionX(), (float) minecart.getMotionY(), (float) minecart.getMotionZ()));
        } else {
            player.sendMessage(LocaleParser.getTextKey("AdminControlsMomentumInvalid"));
        }
        return true;
    }
    
}
