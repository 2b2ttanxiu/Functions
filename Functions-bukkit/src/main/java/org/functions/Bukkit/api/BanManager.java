package org.functions.Bukkit.api;

public interface BanManager {
    void tempBanned(long end,String from,String reason);
    void tempBanned(long end,String from);
    void tempBanned(long end);
    void tempPardon();
    String[] getTempBanned();
    int getTempBannedDay();
    int randomBannedDay();
    boolean enableRandomDay();
    String[] getBanned();
    void Banned(String reason);
    void Banned(String from,String reason);
    void Pardon();
    void AllPardon();
    long getTempBannedTime();
    boolean isBanned();
    String getReason();
    void setReason(String reason);
    boolean exists();
}
