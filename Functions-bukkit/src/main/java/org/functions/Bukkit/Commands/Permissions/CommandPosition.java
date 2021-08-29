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

public class CommandPosition implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("position",new CommandPosition());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String loc;
        if (args.length > 1) {
            return true;
        }
        if (args.length == 0) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.position.*")) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.position.me")) {
                        sender.sendMessage(BukkitPermission.noPerms("functions.command.position.me"));
                        return true;
                    }
                }
                loc = api.changeLocationToString(((Player) sender).getLocation());
                sender.sendMessage(api.putLanguage("ShowPosition","玩家位置：%position%").replace("%player%",sender.getName()).replace("%target%",sender.getName()).replace("%position%",loc));
                return true;
            }
        }
        if (args.length == 1) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.position.*")) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.position.other")) {
                        sender.sendMessage(BukkitPermission.noPerms("functions.command.position.other"));
                        return true;
                    }
                }
            }
            loc = api.changeLocationToString(Bukkit.getServer().getPlayer(args[0]).getLocation());
            sender.sendMessage(api.putLanguage("ShowPosition","玩家位置：%position%").replace("%player%",sender.getName()).replace("%target%",Bukkit.getServer().getPlayer(args[0]).getName()).replace("%position%",loc));
            return true;
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.position")) {
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
