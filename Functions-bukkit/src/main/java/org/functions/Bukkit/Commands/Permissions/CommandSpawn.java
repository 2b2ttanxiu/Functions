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

import java.util.List;

public class CommandSpawn implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("report",new CommandReport());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(api.putLanguage("NoPlayer", "&c这条指令只能由玩家来执行"));
            return true;
        }
        if (args.length == 0) {
            Player p = (Player)sender;
            if (!BukkitPermission.has(p.getUniqueId(),"functions.command.spawn.*")) {
                if (!BukkitPermission.has(p.getUniqueId(),"functions.command.spawn.main")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.spawn.main"));
                    return true;
                }
            }
            p.teleport(a.getSpawnLocation(a.getSpawns().get(0)));
            sender.sendMessage(api.putLanguage("TeleportSpawn","成功传送至出生点！"));
            return true;
        }
        if (args.length == 1) {
            for (String s : a.getSpawns()) {
                if (s.equalsIgnoreCase(args[0])) {
                    Player p = (Player)sender;
                    if (!BukkitPermission.has(p.getUniqueId(),"functions.command.spawn.*")) {
                        if (!BukkitPermission.has(p.getUniqueId(),"functions.command.spawn." + s)) {
                            sender.sendMessage(BukkitPermission.noPerms("functions.command.spawn." + s));
                            return true;
                        }
                    }
                    p.teleport(a.getSpawnLocation(s));
                    sender.sendMessage(api.putLanguage("TeleportSpawn","成功传送至出生点！"));
                    return true;
                }
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return a.getSpawns();
    }
}
