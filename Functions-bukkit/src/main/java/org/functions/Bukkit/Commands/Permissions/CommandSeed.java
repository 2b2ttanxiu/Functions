package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.World;
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

public class CommandSeed implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("seed",new CommandSeed());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.seed")) {
                sender.sendMessage(BukkitPermission.noPerms("functions.command.seed"));
                return true;
            }
        }
        List<String> ls = new ArrayList<>();
        String format = a.getSettings().getString("Seed");
        for (World w : Bukkit.getWorlds()) {
            ls.add(format.replace("%world%",w.getName()).replace("%seed%",w.getSeed()+""));

        }
        for (String s : ls) {
            sender.sendMessage(s);
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
