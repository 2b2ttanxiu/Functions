package org.functions.Bukkit.api;

import org.bukkit.Location;

public interface BackManager {
    void create(Location location);
    void delete();
    boolean exists();
    void teleport();
    String toLocation();
}
