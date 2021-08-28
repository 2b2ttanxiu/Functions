package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.Economy.YamlEconomy;
import org.functions.Bukkit.api.Permissions.BukkitPermission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandPay implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    YamlEconomy yaml;
    public static void run() {
        Functions.instance.getAPI().getCommand("pay",new CommandPay());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(api.putLanguage("NoPlayer","&c这条指令只能由玩家来执行"));
            return true;
        }
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.pay")) {
                sender.sendMessage(BukkitPermission.noPerms("functions.command.pay"));
                return true;
            }
        if (args.length == 2) {
            if (!yaml.hasAccount(((Player) sender).getUniqueId())) {
                sender.sendMessage(api.putLanguage("EconomyIsHasAccount","&c这名玩家是否有了经济账号？"));
                return true;
            }
            if (sender.getName().equalsIgnoreCase(args[0])) {

            }
            OfflinePlayer other = Bukkit.getOfflinePlayer(args[0]);
            if (!yaml.hasAccount(other.getUniqueId())) {
                sender.sendMessage(api.putLanguage("EconomyIsHasAccount","&c这名玩家是否有了经济账号？"));
                return true;
            }
            data = new Data(((Player) sender).getUniqueId());
            Data target = new Data(other.getUniqueId());
            //.replace("%target%",other.getName()).replace("%player%",sender.getName()).replace("%economy%",count+"").replace("%account_economy%",data.getEconomy().getBalance(data.getUUID()).getBalance()+"")
            double count = 0.0D;
            try {
                count = a.getAmountFromString(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(api.putLanguage("FormatEconomyNumber","&c数字格式化失败，是否写错？"));
                e.printStackTrace();
                return true;
            }
            if (count <= 0.0D) {
                sender.sendMessage(api.putLanguage("NumberError","&c数字有错误！"));
                return true;
            }
            data.getEconomy().withdraw(data.getUUID(),count);
            sender.sendMessage(api.putLanguage("me-payEconomy","&a成功转账给 %target% 的 %economy%").replace("%target%",other.getName()).replace("%player%",sender.getName()).replace("%economy%",count+"").replace("%account_economy%",data.getEconomy().getBalance(data.getUUID()).getBalance()+""));
            target.getEconomy().deposit(target.getUUID(),count);
            if (other.isOnline()) {
                other.getPlayer().sendMessage(api.putLanguage("other-copyEconomy","").replace("%target%",other.getName()).replace("%player%",sender.getName()).replace("%economy%",count+"").replace("%account_economy%",target.getEconomy().getBalance(target.getUUID()).getBalance()+""));
                return true;
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.pay")) {
                return ls;
            }
        }
        for (Player p : api.getOnlinePlayers()) {
            ls.add(p.getName());
        }
        Collections.sort(ls);
        return ls;
    }
}
