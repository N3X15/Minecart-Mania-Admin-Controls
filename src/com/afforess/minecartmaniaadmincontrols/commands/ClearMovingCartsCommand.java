package com.afforess.minecartmaniaadmincontrols.commands;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class ClearMovingCartsCommand extends ClearAllCartsCommand {
    
    @Override
    public CommandType getCommand() {
        return CommandType.ClearMovingCarts;
    }
    
    @Override
    public boolean shouldRemoveMinecart(final MinecartManiaMinecart minecart) {
        return minecart.isMoving();
    }
    
}