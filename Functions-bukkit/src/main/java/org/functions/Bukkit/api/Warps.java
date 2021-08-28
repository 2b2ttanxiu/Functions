package org.functions.Bukkit.api;

import org.bukkit.Location;
import org.functions.Bukkit.Main.Functions;

public class Warps {
    private String name;
    private Location loc;
    private String toLocation;
    public Warps(String name, Location location) {
        this.name = name;
        this.loc = location;
        toLocation = Functions.instance.getAPI().toLocation(location);
    }

    public String getName() {
        return name;
    }
    public Location getLocation() {
        return loc;
    }
    public String toLocation() {
        return toLocation;
    }
}
