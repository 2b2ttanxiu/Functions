package org.functions.Bukkit.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;

import java.util.ArrayList;
import java.util.UUID;

public class ClickPerSeconds implements ClickPerSecondsManager {
    UUID uuid;
    int max;
    int CPS;
    long cpsTime;
    long cpsMin;
    FileConfiguration set;
    ArrayList<Long> cps = new ArrayList();
    public int getMax() {
        return this.max;
    }
    public ClickPerSeconds(UUID uuid) {
        set = Functions.instance.getData_Data(uuid);
        this.uuid = uuid;
    }
    public void countCPS() {
        this.cps.add(System.currentTimeMillis());
        this.removeCPSTimeout();
        if (this.cps.size() > this.max) {
            this.max = this.cps.size();
        }

    }

    public int getCPS() {
        return this.cps.size();
    }

    public void removeCPSTimeout() {
        while(!this.cps.isEmpty() && System.currentTimeMillis() - (Long)this.cps.get(0) > 1000L) {
            this.cps.remove(0);
        }

    }

    public void reset() {
        this.cps.clear();
        this.max = 0;
    }
    public void delete() {
        set.set("ClickPerSecond.Max",null);
        set.set("ClickPerSecond.Count",null);
    }

    public void resetMax() {
        this.max = 0;
    }

    public void set() {
        set.set("ClickPerSecond.Max",max);
        set.set("ClickPerSecond.Count",cps.size());
    }

    public int getMaxCPS() {
        return max;
    }

    public int getCountCPS() {
        return cps.size();
    }

}
