package org.functions.Bukkit.Main;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.functions.Bukkit.api.*;
import org.functions.Bukkit.api.Economy.Economy;
import org.functions.Bukkit.api.Economy.YamlEconomy;
import org.functions.Bukkit.api.Permissions.BukkitPermission;

import java.util.*;

public class Data implements DataManager {
    static Functions as = Functions.instance;
    Functions a = Functions.instance;
    UUID uuid;
    Player player;
    FileConfiguration set;
    public Data(UUID uuid) {
        this.uuid = uuid;
        player = a.getAPI().getPlayer(uuid);
        create();
        set = a.getData_Data(uuid);
    }
    public BukkitPermission getBukkitPermissions() {
        return Functions.instance.getAPI().getBukkitPermissions();
    }
    public static Economy getEco() {
        return as.getVault().getEco();
    }
    public YamlEconomy getEconomy() {
        return new YamlEconomy();
    }
    String home_path = "Homes.";
    String home_name;
    public HomeManager getHomes() {
        return new HomeManager() {
            public List<String> getLists() {
                return new ArrayList<>(set.getConfigurationSection("Homes").getKeys(false));
            }

            public void create(String name, Location location) {
                set.set(home_path+name,Functions.instance.getAPI().changeLocationToString(location));
            }

            public void delete(String name) {
                for (String s : getLists()) {
                    if (s.equalsIgnoreCase(name)) {
                        set.set(home_path + name, null);
                    }
                }
            }

            public Location getLocation(String name) {
                return Functions.instance.getAPI().changeStringToLocation(set.getString(home_path+name));
            }

            public void setLocation(String name,Location location) {
                create(name,location);
            }

            public String toLocation(String name) {
                return Functions.instance.getAPI().toLocation(getLocation(name));
            }

            public boolean exists(String name) {
                for (String s : getLists()) {
                    if (s.equalsIgnoreCase(name)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public BackManager getBacks() {
        return new BackManager() {
            public void create(Location location) {
                set.set("Back",Functions.instance.getAPI().changeLocationToString(location));
            }

            public void delete() {
                set.set("Back",null);
            }

            public boolean exists() {
                if (set.getString("Back")!=null) {
                    return true;
                }
                return false;
            }
            public void teleport() {
                if (exists()) {
                    getPlayer().teleport(Functions.instance.getAPI().changeStringToLocation(set.getString("Back")));
                }
            }
            public String toLocation() {
                return Functions.instance.getAPI().toLocation(Functions.instance.getAPI().changeStringToLocation(set.getString("Back")));
            }
        };
    }

    public void kill() {
        getPlayer().setHealth(0.0);
    }

    public Functions getMainClass() {
        return Functions.instance;
    }
    public Player getPlayer() {
        return Functions.instance.getServer().getPlayer(uuid);
    }

    public int setGroup(String Group) {
        if (a.getAPI().EmptyGroup(Group)) {
            set.set("Group",Group);
            return 0;
        }
        return 1;
    }

    public boolean hasGroup(String group) {
        if (set.getString("Group").equalsIgnoreCase(group)) {
            return true;
        }
        return false;
    }

    public FileConfiguration getFile() {
        return set;
    }

    public ClickPerSecondsManager getClick_Per_Seconds() {
        return new ClickPerSeconds(uuid);
    }

    public String getgroup() {
        return set.getString("Group");
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean hasOperator() {
        return set.getBoolean("op");
    }
    public void setOperator(boolean operator) {
        set.set("op",operator);
        player.setOp(operator);
    }

    public boolean AllowFlight() {
        return set.getBoolean("Fly");
    }
    public void save() { a.saveData(uuid);}
    public void create() {
        a.onData(uuid);
    }

    public boolean enableDisplay() {
        return set.getBoolean("DisplayName.enable");
    }

    public boolean enableScoreBoard() {
        return set.getBoolean("ScoreBoard");

    }

    public void setScoreboard(boolean enable) {
        set.set("ScoreBoard",enable);
    }

    public void setEnableDisplay(boolean enable) {
        set.set("DisplayName.enable",enable);
    }

    public void addChatTime() {
        set.set("ChatTime",System.currentTimeMillis() + getGroup().getChatDelay());
    }

    public long getChatTime() {
        return set.getLong("ChatTime");
    }

    public boolean existsCanChat() {
        if (getChatTime() == 0) {
            return true;
        }
        if (System.currentTimeMillis() <= getChatTime()) {
            return false;
        }
        return true;
    }

    public String getPing() {
        return Functions.instance.getAPI().getPing(player.getPlayer())+"";
    }

    public BanManager getBanned() {
        return new BanManager() {
            public boolean enableRandomDay() {
                return Functions.instance.getSettings().getBoolean("Banned.randomTempDay");
            }

            public int getTempBannedDay() {
                if (enableRandomDay()) {
                    return randomBannedDay();
                }
                return Functions.instance.getSettings().getInt("Banned.tempBannedDay");
            }
            public int randomBannedDay() {
                Random random = new Random();
                random.setSeed(player.getWorld().getSeed());
                return random.nextInt(31);
            }
            public void tempBanned(long end, String from, String reason) {
                set.set("TempBanned.reason",reason);
                tempBanned(end,from);
            }

            public void tempBanned(long end, String from) {
                set.set("TempBanned.from",from);
                tempBanned(end);
            }

            public void tempBanned(long end) {
                set.set("TempBanned.time",end);
            }

            public void tempPardon() {
                set.set("TempBanned",null);
            }

            public String[] getTempBanned() {
                return (set.getLong("TempBanned.time") + "," + set.getString("TempBanned.from") + "," + set.getString("TempBanned.reason")).split(",");
            }

            public String[] getBanned() {
                return (set.getBoolean("Banned.enable") + "," + set.getString("Banned.from") + "," + set.getString("Banned.reason")).split(",");
            }

            public void Banned(String reason) {
                set.set("Banned.reason",reason);
            }

            public void Banned(String from, String reason) {
                set.set("Banned.from",from);
                Banned(reason);
            }

            public void Pardon() {
                set.set("Banned.enable",false);
            }

            public void AllPardon() {
                tempPardon();
                Pardon();
            }

            public long getTempBannedTime() {
                return set.getLong("TempBanned.time");
            }

            public boolean isBanned() {
                if (set.get("TempBanned.time")==null) {
                    return set.getBoolean("Banned.enable");
                }
                if (getTempBannedTime() >= System.currentTimeMillis()) {
                    return true;
                }
                if (set.getBoolean("Banned.enable")) {
                    return true;
                }
                return false;
            }

            public String getReason() {
                return set.getString("Reason_Banned");
            }

            public void setReason(String reason) {
                set.set("Reason_Banned",reason);
            }

            public boolean exists() {
                return isBanned();
            }
        };
    }
    public ReportManager getReport() {
        return new ReportManager() {
            public int getReportMin() {
                return Functions.instance.getSettings().getInt("Report_MinCount");
            }

            public int getReportMax() {
                return Functions.instance.getSettings().getInt("Report_MaxCount");
            }

            public int getReportRandom() {
                Random random = new Random();
                random.setSeed(player.getWorld().getSeed());
                return random.nextInt(getReportMax());
            }

            public void report(String report, String reason) {
                set.set("Reports.reason",reason);
                report(report);
            }

            public void report(String report) {
                List<String> ls = set.getStringList("Reports.report");
                ls.add(report);
                set.set("Reports.report",ls);
            }
            public String[] getReport() {
                return (set.getStringList("Reports.report").size() + "," + set.getString("Reports.reason")).split(",");
            }
            public void check() {
                if (getBanned().exists()) {
                    return;
                }
                int random = getReportRandom();
                int size = Integer.parseInt(getReport()[0]);
                if (size == 0) {
                    return;
                }
                if (size == random) {
                    long time = System.currentTimeMillis() + (getBanned().getTempBannedDay() * 24 * 60 * 60L);
                    getBanned().tempBanned(time,"CONSOLE",getReport()[1]);
                }
            }
        };
    }
    public Group getGroup() {
        return new Group(set.getString("Group"));
    }

    public Mute getMute() {
        return new Mute() {
            @Override
            public boolean getMute() {
                if (getGroup().getMute().exists()) {
                    return true;
                }
                if (set.getBoolean("Mute.enable")) {
                    return true;
                }
                if (set.getLong("Mute.tempTime") <= System.currentTimeMillis()) {
                    return true;
                }
                return false;
            }

            @Override
            public void setMute(boolean enable) {
                set.set("Mute.enable",enable);
            }

            @Override
            public boolean getTempMute() {
                if (getMute()) {
                    return false;
                }
                if (set.getLong("mute.temp") <= System.currentTimeMillis()) {
                    return true;
                }
                return false;
            }

            @Override
            public void setTempMute(long start, long end) {
                set.set("Mute.tempTime_start",start);
                set.set("Mute.tempTime",end);
            }

            @Override
            public long getTempTime() {
                if (set.get("Mute.tempTime")!=null) {
                    return -1;
                }
                return set.getLong("Mute.tempTime");
            }

            public void setUnTempMute() {
                set.set("Mute.tempTime_start",null);
                set.set("Mute.tempTime",null);
            }

            @Override
            public boolean exists() {
                return getTempMute();
            }
        };
    }

    public Prefixes getPrefixes() {
        return new Prefixes() {
            @Override
            public void setPrefix(String prefix) {
                set.set("Prefix",prefix);
            }

            @Override
            public String getPrefix() {
                if (set.getString("Prefix")==null) {
                    return getGroup().getPrefix().getPrefix();
                }
                return set.getString("Prefix");
            }

            @Override
            public void setPrefixes(List<String> prefixes) {
                set.set("Prefixes",prefixes);
            }

            @Override
            public List<String> getPrefixes() {
                if (set.getStringList("Prefixes")==null) {
                    if (getGroup().getPrefix().getPrefixes()==null) {
                        List<String> ls = new ArrayList<>();
                        ls.add(getPrefix());
                        return ls;
                    }
                    return getGroup().getPrefix().getPrefixes();
                }
                return set.getStringList("Prefixes");
            }
        };
    }

    public Suffixes getSuffixes() {
        return new Suffixes() {
            @Override
            public void setSuffix(String suffix) {
                set.set("Suffix",suffix);
            }

            @Override
            public String getSuffix() {
                if (set.getString("Suffix")==null) {
                    return getGroup().getSuffix().getSuffix();
                }
                return set.getString("Suffix");
            }

            @Override
            public void setSuffixes(List<String> suffixes) {
                set.set("Suffixes",suffixes);
            }

            @Override
            public List<String> getSuffixes() {
                if (set.getStringList("Suffixes")==null) {
                    if (getGroup().getPrefix().getPrefixes()==null) {
                        List<String> ls = new ArrayList<>();
                        ls.add(getSuffix());
                        return ls;
                    }
                    return getGroup().getSuffix().getSuffixes();
                }
                return set.getStringList("Suffixes");
            }
        };
    }

    public PermissionManager getPermissionManager() {
        return new PermissionManager() {
            @Override
            public void addPermission(String name) {
                set.set("Permissions",getPermissions().add(name));
            }

            @Override
            public void addTempPermission(String name, long end) {
                set.set("TempPermissions",getTempPermissions().add(name));

            }

            @Override
            public void removePermission(String name) {
                set.set("Permissions",getPermissions().remove(name));

            }

            @Override
            public void removeTempPermission(String name) {
                set.set("TempPermissions",getPermissions().remove(name));

            }

            @Override
            public List<String> getTempPermissions() {
                return set.getStringList("TempPermissions");
            }

            @Override
            public List<String> getPermissions() {
                return set.getStringList("Permissions");
            }

            @Override
            public void setTempPermissions(List<String> names) {
                set.set("TempPermissions",names);
            }

            @Override
            public void setPermissions(List<String> names) {
                set.set("Permissions",names);
            }

            @Override
            public boolean equals(String name) {
                if (name.endsWith(".*")) {
                    name = name.replace(".*","");
                }
                for (String s : getGroup().getPermissions()) {
                    if (s.contains(name)) {
                        return true;
                    }
                }
                for (String s : getTempPermissions()) {
                    if (s.contains(name)) {
                        return true;
                    }
                }
                for (String s : getPermissions()) {
                    if (s.contains(name)) {
                        return true;
                    }
                }
                return false;
            }
            @Deprecated
            public boolean equalsIgnoreCase(String name) {
                for (String s : getTempPermissions()) {
                    if (s.equalsIgnoreCase(name)) {
                        return true;
                    }
                }
                for (String s : getPermissions()) {
                    if (s.equalsIgnoreCase(name)) {
                        return true;
                    }
                }
                return false;
            }
            @Deprecated
            public boolean hasPermission(String name) {
                for (String s : getTempPermissions()) {
                    if (s.equalsIgnoreCase(name)) {
                        return true;
                    }
                }
                for (String s : getPermissions()) {
                    if (s.equalsIgnoreCase(name)) {
                        return true;
                    }
                }
                return false;
            }
            public boolean exists() {
                if (getTempPermissions().size() != 0 || getPermissions().size() != 0) {
                    return true;
                }
                return getGroup().getPermissions().size() != 0;
            }
        };
    }
}
