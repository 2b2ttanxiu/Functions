package org.functions.Bukkit.Main;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.functions.Bukkit.api.Economy.PlayerBalance;
import org.functions.Bukkit.api.Economy.YamlEconomy;

import java.util.List;

public class VaultHook {
    static org.functions.Bukkit.api.Economy.Economy econ;
    static Economy eco = null;
    static Permission perms = null;
    static Chat chat = null;
    public static boolean hasVault() {
        if (Functions.instance.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> econ = Functions.instance.getServer().getServicesManager().getRegistration(Economy.class);
            RegisteredServiceProvider<Permission> permission = Functions.instance.getServer().getServicesManager().getRegistration(Permission.class);
            RegisteredServiceProvider<Chat> Chat = Functions.instance.getServer().getServicesManager().getRegistration(Chat.class);
            if (econ !=null) {
                chat = Chat.getProvider();
                perms = permission.getProvider();
                eco = econ.getProvider();
                return eco !=null;
            }
            return false;
        }
        return false;
    }
    public static org.functions.Bukkit.api.Economy.Economy getEco() {
        return new YamlEconomy();
    }
}
