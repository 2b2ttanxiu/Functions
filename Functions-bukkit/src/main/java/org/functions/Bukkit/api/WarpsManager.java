package org.functions.Bukkit.api;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.functions.Bukkit.Main.Functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WarpsManager {
    FileConfiguration set = Functions.instance.getWarps();
    public List<Warps> getWarps() {
        List<Warps> warps = new ArrayList<>();
        Set<String> key = set.getConfigurationSection("Warps").getKeys(false);
        List<String> ls = new ArrayList<>(key);
        for (String s : ls) {
            warps.add(new Warps(s,Functions.instance.getAPI().formatLocation(set.getString("Warps." + s))));
        }
        return warps;
    }
    public boolean exists(String name) {
        for (Warps warp : getWarps()) {
            if (name.equalsIgnoreCase(warp.getName())) {
                return true;
            }
        }
        return false;
    }
    public Warps getWarp() {
        for (Warps warp : getWarps()) {
            return warp;
        }
        return null;
    }
    public void removeWarp(String name) {
        set.set("Warps." + name,null);
    }
    public void setWarp(Warps warp) {
        set.set("Warps." + warp.getName(),warp.toLocation());
    }
    public void addWarp(Warps warp) {
        setWarp(warp);
    }
}
