package com.afforess.minecartmaniaadmincontrols.permissions;

import org.bukkit.Location;

public class SignTextUpdater implements Runnable {
    private final Location sign;
    
    public SignTextUpdater(final Location location) {
        sign = location;
    }
    
    public void run() {
        sign.getBlock().getState().update(true);
    }
    
}
