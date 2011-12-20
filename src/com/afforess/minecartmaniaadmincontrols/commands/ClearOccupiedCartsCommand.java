package com.afforess.minecartmaniaadmincontrols.commands;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class ClearOccupiedCartsCommand extends ClearAllCartsCommand {
    
    @Override
    public CommandType getCommand() {
        return CommandType.ClearOccupiedCarts;
    }
    
    @Override
    public boolean shouldRemoveMinecart(final MinecartManiaMinecart minecart) {
        return minecart.isStandardMinecart() && (minecart.minecart.getPassenger() != null);
    }
    
}