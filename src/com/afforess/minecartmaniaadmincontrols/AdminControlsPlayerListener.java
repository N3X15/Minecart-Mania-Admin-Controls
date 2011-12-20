package com.afforess.minecartmaniaadmincontrols;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import com.afforess.minecartmaniacore.world.MinecartManiaWorld;
import com.afforess.minecartmaniacore.world.SpecificMaterial;

public class AdminControlsPlayerListener extends PlayerListener {
    
    @Override
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
}
