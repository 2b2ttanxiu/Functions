package org.functions.Bukkit.Main;

import org.functions.Bukkit.Commands.Permissions.CommandMain;

public class AllRegister {
    public static void run() {
        RegisterCommands.run();
        RegisterListeners.run();
        Functions.instance.getAPI().sendConsole("§aAll Register successfully!");
    }
}
