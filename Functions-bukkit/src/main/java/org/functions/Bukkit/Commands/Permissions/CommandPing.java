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

public class CommandPing implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("ping",new CommandPing());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.ping")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.ping"));
                    return true;
                }
                sender.sendMessage(api.putLanguage("ShowPing","%target%的延迟：%ping%").replace("%target%",sender.getName()).replace("%player%",sender.getName()).replace("%ping%",api.getPing((Player) sender)+""));
                return true;
            }
        }
        if (args.length == 1) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(), "functions.command.ping")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.ping"));
                    return true;
                }
            }
            sender.sendMessage(api.putLanguage("ShowPing","%target%的延迟：%ping%").replace("%target%",args[0]).replace("%player%",sender.getName()).replace("%ping%",api.getPing(Bukkit.getPlayer(args[0]))+""));
            return true;
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.ping")) {
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
