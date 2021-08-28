package org.functions.Bukkit.Commands.Defaults;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.Main.Help;
import org.functions.Bukkit.api.Permissions.BukkitPermission;

import java.util.List;

public class CommandHelp implements TabExecutor {
    Functions a = Functions.instance;
    Help help = new Help();
    public static void run() {
        Functions.instance.getAPI().getCommand("help",new CommandHelp());
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (!BukkitPermission.has(p.getUniqueId(), "functions.command.help.*")) {
                    if (!BukkitPermission.has(p.getUniqueId(), "functions.command.help.Main")) {
                        sender.sendMessage(BukkitPermission.noPerms("functions.command.help.Main"));
                        return true;
                    }
                }
                sender.sendMessage(a.getAPI().message(help.getMain()));
                return true;
            }
            if (!BukkitPermission.has(p.getUniqueId(), "functions.command.help.*")) {
                if (!BukkitPermission.has(p.getUniqueId(), "functions.command.help.pages." + args[0])) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.help.pages." + args[0]));
                    return true;
                }
            }
            for (String s : help.getSubMessage(args[0])) {
                sender.sendMessage(a.getAPI().message(s));
            }
            return true;
        } else {
            if (args.length == 0) {
                sender.sendMessage(a.getAPI().message(help.getMain()));
                return true;
            }
            for (String s : help.getSubMessage(args[0])) {
                sender.sendMessage(a.getAPI().message(s));
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player p = (Player) sender;
                if (!BukkitPermission.has(p.getUniqueId(), "functions.command.help.*")) {
                    if (!BukkitPermission.have(p.getUniqueId(), "functions.command.help.pages." + args[0])) {
                        return null;
                    }
                }
                return help.getSubHas(args[0]);
            }
        } else {
            return help.getSubHas(args[0]);
        }
        return null;
    }
}
