package com.afforess.minecartmaniaadmincontrols;

import com.afforess.minecartmaniacore.event.MinecartManiaListener;
import com.afforess.minecartmaniacore.event.MinecartTimeEvent;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.minecart.MinecartManiaStorageCart;

public class MinecartTimer extends MinecartManiaListener {
    @Override
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
}
