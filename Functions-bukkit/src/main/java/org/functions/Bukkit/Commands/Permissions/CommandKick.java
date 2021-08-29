package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.Bukkit;
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

public class CommandKick implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("kick",new CommandKick());
        Functions.instance.getAPI().getCommand("kickall",new CommandKick());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("kickall")) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.kickall")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.kickall"));
                    return true;
                }
            }
            if (args.length == 0) {
                for (Player p : api.getOnlinePlayers()) {
                    if (p.isOp()) {
                        continue;
                    }
                    p.kickPlayer(a.getSettings().getString("Kick"));
                }
                sender.sendMessage(api.putLanguage("KickAll","&a踢出所有游戏内的玩家"));
                return true;
            }
            StringBuilder format = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                format.append(args[i]).append(" ");
            }
            if (format.toString().startsWith("'") || format.toString().endsWith("'")) {
                format.replace('\'', '\'', "");
            }
            for (Player p : api.getOnlinePlayers()) {
                if (p.isOp()) {
                    continue;
                }
                p.kickPlayer(format.toString());
            }
            sender.sendMessage(api.putLanguage("KickAll","&a踢出所有游戏内的玩家"));
            return true;
        }
        if (args.length == 0) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.kick.*")) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.kick.me")) {
                        sender.sendMessage(BukkitPermission.noPerms("functions.command.kick.me"));
                        return true;
                    }
                }
                ((Player)sender).kickPlayer(a.getSettings().getString("Kick"));
                return true;
            }
        }
        if (args.length == 1) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.kick.*")) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.kick.target")) {
                        sender.sendMessage(BukkitPermission.noPerms("functions.command.kick.target"));
                        return true;
                    }
                }
            }
            Player other = Bukkit.getPlayer(args[0]);
            if (other.isOnline()) {
                other.kickPlayer(a.getSettings().getString("Kick"));
                return true;
            }
            sender.sendMessage(api.putLanguage("IsOnline","&c玩家是否在线？"));
            return true;
        }
        if (args.length == 2) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.kick.*")) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.kick.target")) {
                        sender.sendMessage(BukkitPermission.noPerms("functions.command.kick.target"));
                        return true;
                    }
                }
            }
            Player other = Bukkit.getPlayer(args[0]);
            StringBuilder format = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                format.append(args[i]).append(" ");
            }
            if (format.toString().startsWith("'") || format.toString().endsWith("'")) {
                format.replace('\'', '\'', "");
            }
            if (other.isOnline()) {
                other.kickPlayer(format.toString());
                return true;
            }
            sender.sendMessage(api.putLanguage("IsOnline","&c玩家是否在线？"));
            return true;
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.kick.*")) {
                return null;
            }
        }
        List<String> ls = new ArrayList<>();
        if (args.length == 1) {
            if (args[0]==null) {
                for (Player p : api.getOnlinePlayers()) {
                    ls.add(p.getName());
                }
                return ls;
            }
            for (Player p : api.getOnlinePlayers()) {
                if (p.getName().startsWith(args[0])) {
                    ls.add(p.getName());
                }
            }
            return ls;
        }
        return null;
    }
}
