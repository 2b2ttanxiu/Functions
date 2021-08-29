package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.Main.Group;
import org.functions.Bukkit.api.API;

import java.util.List;

public class CommandMessage implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    public static void run() {
        Functions.instance.getAPI().getCommand("group", new CommandGroup());
    }
    Data data;
    Group group;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

        } else {

        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
