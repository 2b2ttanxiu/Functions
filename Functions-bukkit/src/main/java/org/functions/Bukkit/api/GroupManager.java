package org.functions.Bukkit.api;

import java.util.List;

public interface GroupManager {
    public String getDisplayName();
    public String getName();
    public Prefixes getPrefix();
    public Suffixes getSuffix();
    public String getJoinFormat();
    public String getQuitFormat();
    public String getChatFormat();
    public Message getMessage();
    public List<String> getPermissions();
    public boolean needOP();
    public Mute getMute();
    public Anti_Word getAnti_Word();
    public void setDisplayName(String name);
    public void setName(String name);
    public void setJoinFormat(String format);
    public void setQuitFormat(String format);
    public void setChatFormat(String format);
    public void setTellMessage(String format);
    public void setPermissions(List<String> format);
}
