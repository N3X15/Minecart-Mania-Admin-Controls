package com.afforess.minecartmaniaadmincontrols.commands;

import java.lang.reflect.Constructor;

public enum CommandType {
    MM(false, MinecartInfoCommand.class),
    Debug(true, DebugCommand.class),
    Eject(true, EjectCommand.class),
    PermEject(true, PermanentEjectCommand.class),
    ClearEmptyCarts(true, ClearEmptyCartsCommand.class),
    ClearStandardCarts(true, ClearStandardCartsCommand.class),
    ClearPoweredCarts(true, ClearPoweredCartsCommand.class),
    ClearOccupiedCarts(true, ClearOccupiedCartsCommand.class),
    ClearStorageCarts(true, ClearStorageCartsCommand.class),
    ClearEmptyStorageCarts(true, ClearEmptyStorageCartsCommand.class),
    ClearMovingCarts(true, ClearMovingCartsCommand.class),
    ClearStalledCarts(true, ClearStalledCartsCommand.class),
    ClearAllCarts(true, ClearAllCartsCommand.class),
    SetConfigKey(true, SetConfigurationKeyCommand.class),
    GetConfigKey(false, GetConfigurationKeyCommand.class),
    ListConfigKeys(false, ListConfigurationKeysCommand.class),
    TruCompass(false, TruCompassCommand.class),
    St(false, StationCommand.class),
    Throttle(false, ThrottleCommand.class),
    Momentum(false, MomentumCommand.class),
    Redraw(true, RedrawMinecartCommand.class),
    Hide(true, HideMinecartCommand.class),
    Info(false, MinecartInfoCommand.class);
    
    private boolean admin = false;
    private Class<? extends Command> command = null;
    
    private CommandType(final boolean admin, final Class<? extends Command> command) {
        this.admin = admin;
        this.command = command;
    }
    
    public boolean isAdminCommand() {
        return admin;
    }
    
    public Command getCommand() {
        try {
            final Constructor<? extends Command> c = command.getConstructor();
            return c.newInstance((Object[]) null);
        } catch (final Exception e) {
        }
        return null;
    }
    
    @Override
    public String toString() {
        return name().toLowerCase();
    }
    
    public static boolean isAdminCommand(final String command) {
        for (final CommandType c : values()) {
            if (c.toString().equalsIgnoreCase(command))
                return c.isAdminCommand();
        }
        return false;
    }
    
}
