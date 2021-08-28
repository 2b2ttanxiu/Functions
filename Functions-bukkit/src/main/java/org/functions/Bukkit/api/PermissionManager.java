package org.functions.Bukkit.api;

import java.util.List;

public interface PermissionManager {
    void addPermission(String name);
    void addTempPermission(String name,long end);
    void removePermission(String name);
    void removeTempPermission(String name);
    List<String> getTempPermissions();
    List<String> getPermissions();
    void setTempPermissions(List<String> names);
    void setPermissions(List<String> names);
    boolean equals(String name);
    boolean equalsIgnoreCase(String name);
    boolean hasPermission(String name);
    boolean exists();
}
