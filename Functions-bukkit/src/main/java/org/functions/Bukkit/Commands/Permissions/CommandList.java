package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.Permissions.BukkitPermission;

import java.util.List;

public class CommandList implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    PluginManager pm;
    Plugin[] plugins = pm.getPlugins();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("list",new CommandList());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.list")) {
                sender.sendMessage(BukkitPermission.noPerms("functions.command.list"));
                return true;
            }
        }
        sender.sendMessage(api.getPlayerList());
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
