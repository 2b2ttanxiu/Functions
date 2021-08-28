package org.functions.Bukkit.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Group;
import org.functions.Bukkit.api.Economy.Economy;
import org.functions.Bukkit.api.Economy.YamlEconomy;

import java.nio.channels.FileChannel;
import java.util.UUID;

public interface DataManager {
    YamlEconomy getEconomy();
    HomeManager getHomes();
    BackManager getBacks();
    Player getPlayer();
    //Tab getTab(Player player, String header, String footer, String listName);
    int setGroup(String Group);
    boolean hasGroup(String group);
    FileConfiguration getFile();
    void kill();
    ClickPerSecondsManager getClick_Per_Seconds();
    @Deprecated
    String getgroup();
    UUID getUUID();
    boolean hasOperator();
    void setOperator(boolean operator);
    boolean AllowFlight();
    void create();
    boolean enableDisplay();
    boolean enableScoreBoard();
    void setScoreboard(boolean enable);
    void setEnableDisplay(boolean enable);
    void addChatTime();
    long getChatTime();
    boolean existsCanChat();
    String getPing();
    BanManager getBanned();
    ReportManager getReport();
    GroupManager getGroup();
    Mute getMute();
    Prefixes getPrefixes();
    Suffixes getSuffixes();
    PermissionManager getPermissionManager();
}
