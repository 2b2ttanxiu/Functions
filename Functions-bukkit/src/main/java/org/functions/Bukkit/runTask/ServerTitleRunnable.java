package org.functions.Bukkit.runTask;

import org.bukkit.configuration.file.FileConfiguration;
import org.functions.Bukkit.Main.Functions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ServerTitleRunnable implements Runnable{
    private Functions a = Functions.instance;
    String x;
    FileConfiguration set = a.getServerTitle();
    public void run() {
        String path = a.getServerTitle().getString("Enabled");
        int i = Functions.instance.titles;
        List<String> ls = set.getStringList(path+".Line");
        if (set.getBoolean(path+".random")) {
            Random random = new Random();
            Functions.instance.titles = random.nextInt(ls.size());
            return;
        }
        if (ls.size() != 1) {
            if (ls.size() != i) {
                ++i;
            }

            if (ls.size() == i) {
                i = 0;
            }
        }
        Functions.instance.titles = i;
    }
    public static int getMax() {
        String path = Functions.instance.getServerTitle().getString("Enabled");
        FileConfiguration set = Functions.instance.getServerTitle();
        return set.getInt(path+".MaxPlayers");
    }
    public static String getString() {
        String title = "The server no init!\n  &cPlease wait for server init and start";
        String path = Functions.instance.getServerTitle().getString("Enabled");
        FileConfiguration set = Functions.instance.getServerTitle();
        title = set.getStringList(path+".Line").get(Functions.instance.titles);
        return title;
    }
}
