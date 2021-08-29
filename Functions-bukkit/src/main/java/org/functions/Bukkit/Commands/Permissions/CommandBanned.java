package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.BanList;
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
import java.util.List;

public class CommandBanned implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("ban",new CommandBanned());
        Functions.instance.getAPI().getCommand("ban-ip",new CommandBanned());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
       // BanList banList;
     //   if (label.equalsIgnoreCase("ban-ip")) {
//
        //} else if (label.equalsIgnoreCase("ban")) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.banned.*")) {
                        sender.sendMessage(BukkitPermission.noPerms("functions.command.banned.*"));
                        return true;
                    }
                }
                OfflinePlayer other = Bukkit.getOfflinePlayer(args[0]);
                data = new Data(other.getUniqueId());
                if (!data.getBanned().exists()) {
                    data.getBanned().Banned(sender.getName(),a.getSettings().getString("Banned.Reason"));
                    sender.sendMessage(api.putLanguage("setBanned","&a已被封禁！"));
                    return true;
                }
                sender.sendMessage(api.putLanguage("IsBanned","&c是否被封禁？"));
                return true;
            }
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.banned.*")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.banned.*"));
                    return true;
                }
            }
            OfflinePlayer other = Bukkit.getOfflinePlayer(args[0]);
            data = new Data(other.getUniqueId());
            if (!data.getBanned().exists()) {
                StringBuilder format = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    format.append(args[i]).append(" ");
                }
                if (format.toString().startsWith("'") || format.toString().endsWith("'")) {
                    format.replace('\'', '\'', "");
                }
                data.getBanned().Banned(sender.getName(),format.toString());
                sender.sendMessage(api.putLanguage("setBanned","&a已被封禁！"));
                return true;
            }
            sender.sendMessage(api.putLanguage("IsBanned","&c是否被封禁？"));
            return true;
      //  }
        //return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.banned.*")) {
                return null;
            }
        }
        List<String> ls = new ArrayList<>();
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            data = new Data(player.getUniqueId());
            if (!data.getBanned().exists()) {
                ls.add(player.getName());
            }
        }
        return ls;
    }
}
