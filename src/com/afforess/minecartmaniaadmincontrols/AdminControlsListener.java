package com.afforess.minecartmaniaadmincontrols;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import com.afforess.minecartmaniaadmincontrols.permissions.SignTextUpdater;
import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.event.MinecartManiaSignFoundEvent;
import com.afforess.minecartmaniacore.event.MinecartManiaSignUpdatedEvent;
import com.afforess.minecartmaniacore.event.MinecartTimeEvent;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.minecart.MinecartManiaStorageCart;
import com.afforess.minecartmaniacore.signs.MinecartTypeSign;
import com.afforess.minecartmaniacore.signs.Sign;
import com.afforess.minecartmaniacore.signs.SignAction;
import com.afforess.minecartmaniacore.signs.SignManager;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;
import com.afforess.minecartmaniacore.world.SpecificMaterial;

public class AdminControlsListener implements Listener {
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleEnter(final VehicleEnterEvent event) {
        if (event.isCancelled())
            return;
        if (event.getVehicle() instanceof Minecart) {
            if (event.getEntered() instanceof Player) {
                if (VehicleControl.isBlockedFromEntering((Player) event.getEntered())) {
                    event.setCancelled(true);
                    ((Player) event.getEntered()).sendMessage(LocaleParser.getTextKey("AdminControlsBlockMinecartEntry"));
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onMinecartTimeEvent(final MinecartTimeEvent event) {
        final MinecartManiaMinecart minecart = event.getMinecart();
        
        int timer = -1;
        if (minecart.isStandardMinecart()) {
            timer = VehicleControl.getMinecartKillTimer();
        } else if (minecart.isStorageMinecart()) {
            timer = VehicleControl.getStorageMinecartKillTimer();
        } else {
            timer = VehicleControl.getPoweredMinecartKillTimer();
        }
        final boolean kill = (minecart.minecart.getPassenger() == null) && (!minecart.isStorageMinecart() || ((MinecartManiaStorageCart) minecart).isEmpty());
        
        if (timer > 0) {
            if (kill) {
                //No timer, start counting
                if (minecart.getDataValue("Empty Timer") == null) {
                    minecart.setDataValue("Empty Timer", new Integer(timer));
                } else {
                    //Decrement timer
                    final Integer timeLeft = (Integer) minecart.getDataValue("Empty Timer");
                    if (timeLeft > 1) {
                        minecart.setDataValue("Empty Timer", new Integer(timeLeft.intValue() - 1));
                    } else {
                        minecart.kill();
                    }
                }
            }
            //has passenger, resent counter if already set
            else {
                if (minecart.getDataValue("Empty Timer") != null) {
                    minecart.setDataValue("Empty Timer", null);
                }
            }
        } else if (timer == 0) {
            if (kill) {
                minecart.kill();
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.isCancelled())
            return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (MinecartManiaWorld.getConfigurationValue("MinecartTrackAdjuster") == null)
            return;
        if (event.getItem() == null)
            return;
        final Material type = event.getItem().getType();
        if (type == null)
            return;
        final SpecificMaterial mat = (SpecificMaterial) MinecartManiaWorld.getConfigurationValue("MinecartTrackAdjuster");
        if (type.getId() == mat.getId()) {
            if ((event.getClickedBlock() != null) && (event.getClickedBlock().getTypeId() == Material.RAILS.getId())) {
                final int oldData = event.getClickedBlock().getData();
                int data = oldData + 1;
                if (data > 9) {
                    data = 0;
                }
                MinecartManiaWorld.setBlockData(event.getPlayer().getWorld(), event.getClickedBlock().getX(), event.getClickedBlock().getY(), event.getClickedBlock().getZ(), data);
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChange(final SignChangeEvent event) {
        if (event.isCancelled())
            return;
        
        Logger.getLogger("Minecraft").info("ONSIGNUPDATE: " + event.getBlock().getState().toString());
        
        if (!(event.getBlock().getState() instanceof org.bukkit.block.Sign))
            return;
        Sign sign = SignManager.getSignAt(event.getBlock().getLocation(), event.getPlayer());
        final String[] old = new String[4];
        for (int i = 0; i < 4; i++) {
            old[i] = ((org.bukkit.block.Sign) event.getBlock().getState()).getLine(i);
            sign.setLine(i, event.getLine(i), false);
        }
        
        final MinecartManiaSignFoundEvent mmsfe = new MinecartManiaSignUpdatedEvent(sign, event.getPlayer());
        MinecartManiaCore.callEvent(mmsfe);
        sign = mmsfe.getSign();
        
        final Collection<SignAction> actions = sign.getSignActions();
        final Iterator<SignAction> i = actions.iterator();
        final Player player = event.getPlayer();
        while (i.hasNext()) {
            final SignAction action = i.next();
            if (!MinecartManiaAdminControls.permissions.canCreateSign(player, action.getName())) {
                event.setCancelled(true);
                player.sendMessage(LocaleParser.getTextKey("LackPermissionForSign", action.getFriendlyName()));
                SignManager.updateSign(sign.getLocation(), null);
                break;
            }
        }
        
        if (!event.isCancelled() && (sign instanceof MinecartTypeSign)) {
            if (!MinecartManiaAdminControls.permissions.canCreateSign(player, "minecarttypesign")) {
                player.sendMessage(LocaleParser.getTextKey("LackPermissionForSign", "Minecart Type Sign"));
                SignManager.updateSign(sign.getLocation(), null);
                event.setCancelled(true);
            }
        }
        
        if (event.isCancelled()) {
            for (int j = 0; j < 4; j++) {
                sign.setLine(j, old[j], false);
            }
        } else {
            SignManager.updateSign(sign.getLocation(), sign);
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(final BlockBreakEvent event) {
        if (event.isCancelled())
            return;
        if (event.getBlock().getState() instanceof org.bukkit.block.Sign) {
            final Sign sign = SignManager.getSignAt(event.getBlock().getLocation());
            final Collection<SignAction> actions = sign.getSignActions();
            final Iterator<SignAction> i = actions.iterator();
            final Player player = event.getPlayer();
            while (i.hasNext()) {
                final SignAction action = i.next();
                if (!MinecartManiaAdminControls.permissions.canBreakSign(player, action.getName())) {
                    event.setCancelled(true);
                    player.sendMessage(LocaleParser.getTextKey("LackPermissionToRemoveSign", action.getFriendlyName()));
                    break;
                }
            }
            
            if (sign instanceof MinecartTypeSign) {
                if (!MinecartManiaAdminControls.permissions.canBreakSign(player, "minecarttypesign")) {
                    player.sendMessage(LocaleParser.getTextKey("LackPermissionToRemoveSign", "Minecart Type Sign"));
                    event.setCancelled(true);
                }
            }
        }
        if (event.isCancelled()) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(MinecartManiaCore.getInstance(), new SignTextUpdater(event.getBlock().getLocation()), 5);
        }
    }
}
