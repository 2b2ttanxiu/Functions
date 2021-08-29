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
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CommandWeather implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("weather",new CommandWeather());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            int Weather = (300 + (new Random()).nextInt(600)) * 20;
            if ("clear".equalsIgnoreCase(args[0])) {
                World world;
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player)sender).getUniqueId(),"functions.command.weather")) {
                        sender.sendMessage(BukkitPermission.noPerms("functions.command.weather"));
                        return true;
                    }
                    world = ((Player)sender).getWorld();
                } else {
                    world = Bukkit.getWorlds().get(0);
                }
                world.setWeatherDuration(0);
                world.setThunderDuration(0);
                world.setStorm(false);
                world.setThundering(false);
                sender.sendMessage(api.putLanguage("Weather-clear","&a天气设置为晴朗！"));
                return true;
            } else if ("rain".equalsIgnoreCase(args[0])) {
                World world;
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player)sender).getUniqueId(),"functions.command.weather")) {
                        sender.sendMessage(BukkitPermission.noPerms("functions.command.weather"));
                        return true;
                    }
                    world = ((Player)sender).getWorld();
                } else {
                    world = Bukkit.getWorlds().get(0);
                }
                world.setWeatherDuration(Weather);
                world.setStorm(true);
                world.setThundering(false);
                sender.sendMessage(api.putLanguage("Weather-clear","&a天气设置为雨天！"));
                return true;
            } else if ("thunder".equalsIgnoreCase(args[0])) {
                World world;
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player)sender).getUniqueId(),"functions.command.weather")) {
                        sender.sendMessage(BukkitPermission.noPerms("functions.command.weather"));
                        return true;
                    }
                    world = ((Player)sender).getWorld();
                } else {
                    world = Bukkit.getWorlds().get(0);
                }
                world.setWeatherDuration(Weather);
                world.setThunderDuration(Weather);
                world.setStorm(true);
                world.setThundering(true);
                sender.sendMessage(api.putLanguage("Weather-clear","&a天气设置为雷天！"));
                return true;
            }
            return true;
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player)sender).getUniqueId(),"functions.command.weather")) {
                return ls;
            }
        }
        ls.add("clear");
        ls.add("rain");
        ls.add("thunder");
        Collections.sort(ls);
        List<String> temp = new ArrayList<>();
        if (args.length == 1) {
            if (args[0] == null) {
                return ls;
            }
            for (String s : ls) {
                if (s.startsWith(args[0])) {
                    temp.add(s);
                }
            }
            return temp;
        }
        return null;
    }
}
