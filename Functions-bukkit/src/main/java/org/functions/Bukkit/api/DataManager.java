package org.functions.Bukkit.api;

import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Group;

import java.util.UUID;

public interface DataManager {
    EconomyManager getEconomy();
    //Tab getTab(Player player, String header, String footer, String listName);
    int setGroup(String Group);
    @Deprecated
    String getgroup();
    UUID getUUID();
    boolean hasOperator();
    void setOperator(boolean operator);
    boolean AllowFlight();
    void create();
    Group getGroup();
    Mute getMute();
    Prefixes getPrefixes();
    Suffixes getSuffixes();
    PermissionManager getPermissionManager();
}
