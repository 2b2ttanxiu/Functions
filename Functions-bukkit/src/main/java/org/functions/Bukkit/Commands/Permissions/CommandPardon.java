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

public class CommandPardon implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("pardon",new CommandPardon());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // BanList banList;
        //   if (label.equalsIgnoreCase("ban-ip")) {
//
        //} else if (label.equalsIgnoreCase("ban")) {
        if (args.length == 1) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.pardon.*")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.pardon.*"));
                    return true;
                }
            }
            OfflinePlayer other = Bukkit.getOfflinePlayer(args[0]);
            data = new Data(other.getUniqueId());
            if (data.getBanned().exists()) {
                data.getBanned().Pardon();
                sender.sendMessage(api.putLanguage("UnBanned","&a取消封禁！"));
                return true;
            }
            sender.sendMessage(api.putLanguage("IsBanned","&c是否被封禁？"));
            return true;
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.pardon.*")) {
                return ls;
            }
        }
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            data = new Data(player.getUniqueId());
            if (data.getBanned().exists()) {
                ls.add(player.getName());
            }
        }
        return ls;
    }
}
