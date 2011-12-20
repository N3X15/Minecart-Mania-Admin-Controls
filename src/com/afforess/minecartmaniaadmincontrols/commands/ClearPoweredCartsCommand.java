package com.afforess.minecartmaniaadmincontrols.commands;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class ClearPoweredCartsCommand extends ClearAllCartsCommand {
    
    @Override
    public CommandType getCommand() {
        return CommandType.ClearPoweredCarts;
    }
    
    @Override
    public boolean shouldRemoveMinecart(final MinecartManiaMinecart minecart) {
        return minecart.isPoweredMinecart();
    }
    
}