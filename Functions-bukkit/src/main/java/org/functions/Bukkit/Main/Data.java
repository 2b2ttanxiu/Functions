package org.functions.Bukkit.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.functions.Bukkit.api.*;
import org.functions.Bukkit.api.Economy.Economy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public static Economy getEco() {
        return as.getVault().getEco();
    }
    public EconomyManager getEconomy() {
        return null;
    }
    public int setGroup(String Group) {
        if (a.getAPI().EmptyGroup(Group)) {
            set.set("Group",Group);
            return 0;
        }
        return 1;
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
                if (set.get("Mute.tempTime")!=null) {
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

            @Override
            public boolean exists() {
                return getMute();
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
            @Override
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
            @Override
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
        };
    }
}
