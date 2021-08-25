package org.functions.Bukkit.runTask;

import org.functions.Bukkit.Main.Functions;

public class AutoSaveConfiguration implements Runnable{
    public void run() {
        Functions.instance.saveConfiguration();
    }
}
