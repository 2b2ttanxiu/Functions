package org.functions.Bukkit.api;

public interface Mute {
    public boolean getMute();
    public void setMute(boolean enable);
    public boolean getTempMute();
    public void setTempMute(long start,long end);
    public long getTempTime();
    public void setUnTempMute();
    boolean exists();
}
