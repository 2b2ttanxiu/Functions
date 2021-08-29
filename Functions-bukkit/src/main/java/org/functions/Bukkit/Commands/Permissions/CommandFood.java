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

public class CommandFood implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("food",new CommandFood());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int health = 20;
        if (args.length == 0) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.food")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.food"));
                    return true;
                }
                ((Player) sender).setFoodLevel(health);
                sender.sendMessage(api.putLanguage("SetFood","&a成功设置食物饱食度生命值"));
                return true;
            }
        }
        if (args.length == 1) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.food")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.food"));
                    return true;
                }
            }
            Player other = Bukkit.getPlayer(args[0]);
            other.setFoodLevel(health);
            sender.sendMessage(api.putLanguage("SetFood","&a成功设置食物饱食度生命值"));
            return true;
        }
        if (args.length == 2) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.food")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.food"));
                    return true;
                }
            }
            Player other = Bukkit.getPlayer(args[0]);
            try {
                health = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            other.setHealth(health);
            sender.sendMessage(api.putLanguage("SetFood","&a成功设置食物饱食度生命值"));
            return true;
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.food")) {
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
