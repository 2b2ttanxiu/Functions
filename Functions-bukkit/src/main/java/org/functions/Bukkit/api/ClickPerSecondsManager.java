package org.functions.Bukkit.api;

public interface ClickPerSecondsManager {
    void countCPS();
    int getCPS();
    void removeCPSTimeout();
    void reset();
    void resetMax();
    void set();
    int getMaxCPS();
    int getCountCPS();
    void delete();
}
