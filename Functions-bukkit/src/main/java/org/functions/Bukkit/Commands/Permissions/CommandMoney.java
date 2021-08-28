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
import org.functions.Bukkit.api.Permissions.BukkitPermission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandMoney implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("money",new CommandMoney());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                data = new Data(((Player) sender).getUniqueId());
                sender.sendMessage(api.putLanguage("ShowMoney","&a%target%有 %money%").replace("%money%",data.getEconomy().getBalance(data.getUUID()).getBalance()+"").replace("%target%",sender.getName()).replace("%player%",sender.getName()));
                return true;
            }
            if (args.length == 1) {
                OfflinePlayer other = Bukkit.getOfflinePlayer(args[0]);
                data = new Data(other.getUniqueId());
                sender.sendMessage(api.putLanguage("ShowMoney","&a%target%有 %money%").replace("%money%",data.getEconomy().getBalance(data.getUUID()).getBalance()+"").replace("%target%",other.getName()).replace("%player%",sender.getName()));
                return true;
            }
        } else {
            if (args.length == 1) {
                OfflinePlayer other = Bukkit.getOfflinePlayer(args[0]);
                data = new Data(other.getUniqueId());
                sender.sendMessage(api.putLanguage("ShowMoney","&a%target%有 %money%").replace("%money%",data.getEconomy().getBalance(data.getUUID()).getBalance()+"").replace("%target%",other.getName()).replace("%player%",sender.getName()));
                return true;
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.money")) {
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
