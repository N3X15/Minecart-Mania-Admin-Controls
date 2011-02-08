package com.afforess.minecartmaniaadmincontrols;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.afforess.bukkit.minecartmaniacore.ChatUtils;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaMinecart;
import com.afforess.bukkit.minecartmaniacore.MinecartManiaWorld;
import com.afforess.bukkit.minecartmaniacore.StringUtils;

public class AdminCommands {
	public static boolean doEjectPlayer(Player player, String command) {
		String split[] = command.split(" ");
		if (command.toLowerCase().contains("/eject")) {
			if (split.length == 2) {
				List<Player> matchingPlayers = MinecartManiaAdminControls.server.matchPlayer(split[1]);
				if (!matchingPlayers.isEmpty()) {
					for (Player p : matchingPlayers) {
						if (p.isInsideVehicle())
							p.leaveVehicle();
					}
				}
				else {
					ChatUtils.sendMultilineWarning(player, "No player matches that name");
				}
			}
			else {
				ChatUtils.sendMultilineWarning(player, "Proper Usage: '/eject [player name]'");
			}
			return true;
		}
		return false;
	}
	
	public static boolean doPermEjectPlayer(Player player, String command) {
		String split[] = command.split(" ");
		if (command.toLowerCase().contains("/permeject")) {
			if (split.length == 2) {
				List<Player> matchingPlayers = MinecartManiaAdminControls.server.matchPlayer(split[1]);
				if (!matchingPlayers.isEmpty()) {
					for (Player p : matchingPlayers) {
						if (p.isInsideVehicle())
							p.leaveVehicle();
						
						VehicleControl.toggleBlockFromEntering(p.getName());
					}
				}
				else {
					ChatUtils.sendMultilineWarning(player, "No player matches that name");
				}
			}
			else {
				ChatUtils.sendMultilineWarning(player, "Proper Usage: '/permeject [player name]'");
			}
			return true;
		}
		return false;
	}
	
	public static boolean doKillEmptyCarts(Player player, String command) {
		if (command.toLowerCase().contains("/killemptycarts")) {
			ArrayList<MinecartManiaMinecart> minecartList = MinecartManiaWorld.getMinecartManiaMinecartList();
			for (MinecartManiaMinecart minecart : minecartList) {
				if (minecart.isStandardMinecart() && minecart.minecart.getPassenger() == null) {
					minecart.kill();
				}
			}
			return true;
		}
		return false;
	}
	
	public static boolean doKillPoweredCarts(Player player, String command) {
		if (command.toLowerCase().contains("/killpoweredcarts")) {
			ArrayList<MinecartManiaMinecart> minecartList = MinecartManiaWorld.getMinecartManiaMinecartList();
			for (MinecartManiaMinecart minecart : minecartList) {
				if (minecart.isPoweredMinecart()) {
					minecart.kill();
				}
			}
			return true;
		}
		return false;
	}
	
	public static boolean doKillOccupiedCarts(Player player, String command) {
		if (command.toLowerCase().contains("/killoccupiedcarts")) {
			ArrayList<MinecartManiaMinecart> minecartList = MinecartManiaWorld.getMinecartManiaMinecartList();
			for (MinecartManiaMinecart minecart : minecartList) {
				if (minecart.isStandardMinecart() && minecart.minecart.getPassenger() != null) {
					minecart.kill();
				}
			}
			return true;
		}
		return false;
	}
	
	public static boolean doKillStorageCarts(Player player, String command) {
		if (command.toLowerCase().contains("/killstoragecarts")) {
			ArrayList<MinecartManiaMinecart> minecartList = MinecartManiaWorld.getMinecartManiaMinecartList();
			for (MinecartManiaMinecart minecart : minecartList) {
				if (minecart.isStorageMinecart()) {
					minecart.kill();
				}
			}
			return true;
		}
		return false;
	}
	
	public static boolean doKillAllCarts(Player player, String command) {
		if (command.toLowerCase().contains("/killallcarts")) {
			ArrayList<MinecartManiaMinecart> minecartList = MinecartManiaWorld.getMinecartManiaMinecartList();
			for (MinecartManiaMinecart minecart : minecartList) {
				minecart.kill();
			}
			return true;
		}
		return false;
	}
	
	public static boolean reloadConfig(Player player, String command) {
		String split[] = command.split(" ");
		if (command.toLowerCase().contains("/reloadconfig")) {
			if (split.length > 1) {
				
				String pluginName = "";
				for (int i = 1; i < split.length; i++) {
					pluginName += split[i];
					if (i != split.length - 1) {
						pluginName += " ";
					}
				}
				if (MinecartManiaAdminControls.server.getPluginManager().getPlugin(pluginName) != null) {
					MinecartManiaAdminControls.server.getPluginManager().getPlugin(pluginName).onCommand(player, null, "reloadconfig", new String[0]);
				}
			}
			else {
				ChatUtils.sendMultilineWarning(player, "Proper Usage: '/reloadconfig [plugin name]'. [NEWLINE] Hint: Use spaces.");
			}
			return true;
		}
		return false;
	}
	
	public static boolean setConfigurationKey(Player player, String command) {
		if (command.toLowerCase().contains("/setconfigkey")) {
			String key;
			String value;
			//key is wrapped by []
			int start = command.indexOf('[');
			int end = command.indexOf(']');
			if (start > -1 && end > -1) {
				key = command.substring(start+1, end);
				//value is wrapped by []
				start = command.indexOf('[', end+1);
				end = command.indexOf(']', end+1);
				if (start > -1 && end > -1) {
					value = command.substring(start+1, end);
					if (value.equalsIgnoreCase("null")) {
						MinecartManiaWorld.setConfigurationValue(key, null);
						ChatUtils.sendMultilineMessage(player, "Key '" + key + "' set to 'null'", ChatColor.GREEN.toString());
					}
					else {
						if (value.equalsIgnoreCase("true")) {
							ChatUtils.sendMultilineMessage(player, "Key '" + key + "' set to '" + value + "'", ChatColor.GREEN.toString());
							MinecartManiaWorld.setConfigurationValue(key, Boolean.TRUE);
						}
						else if (value.equalsIgnoreCase("false")) {
							ChatUtils.sendMultilineMessage(player, "Key '" + key + "' set to '" + value + "'", ChatColor.GREEN.toString());
							MinecartManiaWorld.setConfigurationValue(key, Boolean.FALSE);
						}
						else if (StringUtils.containsLetters(value)) {
							//save it as a string
							ChatUtils.sendMultilineMessage(player, "Key '" + key + "' set to '" + value + "'", ChatColor.GREEN.toString());
							MinecartManiaWorld.setConfigurationValue(key, value);
						}
						else {
							try {
								value = StringUtils.getNumber(value);
								Double d = Double.valueOf(value);
								if (d.intValue() == d) {
									//try to save it as an int
									ChatUtils.sendMultilineMessage(player, "Key '" + key + "' set to '" + d.intValue() + "'", ChatColor.GREEN.toString());
									MinecartManiaWorld.setConfigurationValue(key, new Integer(d.intValue()));
								}
								else {
									//save it as a double
									ChatUtils.sendMultilineMessage(player, "Key '" + key + "' set to '" + d.doubleValue() + "'", ChatColor.GREEN.toString());
									MinecartManiaWorld.setConfigurationValue(key, d);
								}
							}
							catch (Exception e) {
								ChatUtils.sendMultilineWarning(player, "Invalid Configuration Value");
							}
						}
					}
				}
				else {
					ChatUtils.sendMultilineWarning(player, "Proper Usage: '/setconfigkey [key] [value]'. [NEWLINE] Hint: The key and value should be surrounded by brackets.");
				}
			}
			else {
				ChatUtils.sendMultilineWarning(player, "Proper Usage: '/setconfigkey [key] [value]'. [NEWLINE] Hint: The key and value should be surrounded by brackets.");
			}
			return true;
		}
		return false;
	}
}