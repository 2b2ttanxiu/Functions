package org.functions.Bukkit.Main;

import org.functions.Bukkit.Listeners.Listeners;

public class RegisterListeners {
    public static void run() {
        Functions.instance.getServer().getPluginManager().registerEvents(new Listeners(), Functions.instance);
        Functions.instance.getAPI().sendConsole("&aEvent listeners successfully!");
    }
}
