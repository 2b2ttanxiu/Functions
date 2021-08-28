package org.functions.Bukkit.api;

import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public interface HomeManager {
    List<String> getLists();
    void create(String name,Location location);
    void delete(String name);
    Location getLocation(String name);
    void setLocation(String name,Location location);
    String toLocation(String name);
    boolean exists(String name);
}
