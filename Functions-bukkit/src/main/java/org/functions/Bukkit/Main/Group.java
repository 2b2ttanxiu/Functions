package org.functions.Bukkit.Main;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Events.PlayerSendMessageEvent;
import org.functions.Bukkit.api.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Group implements GroupManager {
    String path = null;
    String Group = null;
    FileConfiguration set = Functions.instance.getGroup();
    public Group(String Group) {
        path = "Groups." + Group + ".";
        this.Group = "Groups." + Group;
    }
    public String getDisplayName() {
        return set.getString(path + "DisplayName");
    }

    public String getName() {
        return set.getString(path + "Name");
    }

    public Prefixes getPrefix() {
        return new Prefixes() {
            @Override
            public void setPrefix(String prefix) {
                set.set(path+"Prefix",prefix);
            }

            @Override
            public String getPrefix() {
                return set.getString(path+"Prefix");
            }

            @Override
            public void setPrefixes(List<String> prefixes) {
                set.set(path+"Prefixes",prefixes);
            }

            @Override
            public List<String> getPrefixes() {
                return set.getStringList(path+"Prefixes");
            }
        };
    }

    public Suffixes getSuffix() {
        return new Suffixes() {
            @Override
            public void setSuffix(String prefix) {
                set.set(path+"Suffix",prefix);
            }

            @Override
            public String getSuffix() {
                return set.getString(path+"Suffix");
            }

            @Override
            public void setSuffixes(List<String> prefixes) {
                set.set(path+"Suffixes",prefixes);
            }

            @Override
            public List<String> getSuffixes() {
                return set.getStringList(path+"Suffixes");
            }
        };
    }

    public String getJoinFormat() {
        if (set.getString(path + "Format.Join").equals("none")) {
            return "";
        }
        return set.getString(path + "Format.Join");
    }

    public String getQuitFormat() {
        if (set.getString(path + "Format.Quit").equals("none")) {
            return "";
        }
        return set.getString(path + "Format.Quit");
    }

    public String getChatFormat() {
        return set.getString(path + "Format.Chat");
    }

    public Message getMessage() {
        path = path + "Format.Message";
        return new Message() {
            @Override
            public String getFormatFrom() {
                return set.getString(path + ".From");
            }

            @Override
            public String getFormatTo() {
                return set.getString(path + ".To");
            }

            @Override
            public int send(Player from,Player to,String message) {
                if (getMute().exists()) {
                    return 1;
                }
                PlayerSendMessageEvent event = new PlayerSendMessageEvent(from,to,message);
                event.schedule(event);
                return 0;
            }
        };
    }

    public List<String> getPermissions() {
        return set.getStringList(path + "Permissions");
    }

    public long getChatDelay() {
        return set.getLong(path+".ChatDelay");
    }

    public long getCommandDelay() {
        return set.getLong(path+".CommandDelay");
    }

    public Mute getMute() {
        return new Mute() {
            public boolean getMute() {
                return set.getBoolean(path + "Mute.enable");
            }

            public void setMute(boolean enable) {
                set.set(path + "Mute.enable",enable);
            }

            public boolean getTempMute() {
                if (set.getBoolean(path + "Mute.enable")) {
                    return true;
                }
                if (set.getLong(path + "Mute.tempTime") >= System.currentTimeMillis()) {
                    return true;
                }
                return false;
            }
            public void setUnTempMute() {
                set.set(path + "Mute.tempTime_start",null);
                set.set(path + "Mute.tempTime",null);
            }
            public void setTempMute(long start, long end) {
                set.set(path + "Mute.tempTime_start",start);
                set.set(path + "Mute.tempTime",end);
            }

            public long getTempTime() {
                if (set.getLong(path + "Mute.tempTime") <= System.currentTimeMillis()) {
                    return 0;
                }
                return set.getLong(path + "Mute.tempTime");
            }
            public boolean exists() {
                return getTempMute();
            }
        };
    }
    public void save() {
        try {
            set.save(Functions.instance.group_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean needOP() {
        return set.getBoolean(path+"needOP");
    }
    public Anti_Word getAnti_Word() {
        return new Anti_Word() {
            public List<String> getAntiWord() {
                return set.getStringList(path+"Anti_Settings.Words");
            }
            public boolean hasAntiWord(String word) {
                List<String> ls = set.getStringList(path+"Anti_Settings.Words");
                for (String s : ls) {
                    if (word.toLowerCase().contains(s)) {
                        return true;
                    }
                }
                return false;
            }

            public boolean hasReplaceAntiWord(String word) {
                List<String> ls = set.getStringList(path+"Anti_Settings.Words");
                Set<String> keys = set.getConfigurationSection(path+"Anti_Settings.Replace").getKeys(false);
                for (String s : ls) {
                    if (word.toLowerCase().contains(s)) {
                        for (String key : keys) {
                            if (s.equalsIgnoreCase(key)) {
                                return true;
                            }
                        }
                        return false;
                    }
                };
                return false;
            }
            public String replace(String text) {
                for (String s : getAntiWord()) {
                    if (text.contains(s)) {
                        text = text.replace(s,set.getString(path+"Anti_Settings.Replace." + s));
                    }
                }
                return text;
            }
        };
    }

    public void setDisplayName(String name) {
        set.set(path+"DisplayName",name);
    }

    public void setName(String name) {
        if (set.get(path+"DisplayName")==null) {
            set.set(path+"DisplayName",name);
            set.set(path+"Name",name);
        }
        set.set(path+"Name",name);
    }

    public void setJoinFormat(String format) {
        set.set(path + "Format.Join",format);
    }

    public void setQuitFormat(String format) {
        set.set(path + "Format.Quit",format);
    }

    public void setChatFormat(String format) {
        set.set(path + "Format.Chat",format);
    }

    public void setTellMessage(String format) {
        set.set(path + "Format.Message",format);
    }

    public void setPermissions(List<String> format) {
        set.set(path + "Permissions",format);
    }
}
