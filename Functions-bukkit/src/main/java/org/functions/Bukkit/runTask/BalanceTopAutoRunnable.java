package org.functions.Bukkit.runTask;

import org.bukkit.scheduler.BukkitRunnable;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.Main.VaultHook;
import org.functions.Bukkit.api.Economy.Economy;
import org.functions.Bukkit.api.Economy.PlayerBalance;
import org.functions.Bukkit.api.Economy.YamlEconomy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BalanceTopAutoRunnable extends BukkitRunnable {
    private List<PlayerBalance> balanceTop = new ArrayList();

    public void run() {
        List<PlayerBalance> btop = new ArrayList(YamlEconomy.getFilePlayers());
        btop.sort(Comparator.comparingDouble(PlayerBalance::getBalance).reversed());
        this.balanceTop = btop;
    }

    public void start(int interval) {
        this.runTaskTimerAsynchronously(Functions.instance, 1L, (long)interval);
    }

    public List<PlayerBalance> getBalanceTop() {
        return this.balanceTop;
    }
}
