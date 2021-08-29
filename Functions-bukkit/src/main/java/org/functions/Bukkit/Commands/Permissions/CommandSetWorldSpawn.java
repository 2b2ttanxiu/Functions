package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

public class CommandSetWorldSpawn implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("setworldspawn",new CommandSetWorldSpawn());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        double x = 0.5D;
        double y = 0.0D;
        double z = 0.5D;
        float yaw = 0.0F;
        float pitch = 0.0F;
        if (args.length == 5) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player)sender).getUniqueId(),"functions.command.setworldspawn")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.setworldspawn"));
                    return true;
                }
                Player p = (Player)sender;
                World world = p.getWorld();
                try {
                    x = Double.parseDouble(args[0]) + 0.5D;
                    y = Double.parseDouble(args[1]) + 1.0D;
                    z = Double.parseDouble(args[2]) + 0.5D;
                    yaw = Float.parseFloat(args[3]);
                    pitch = Float.parseFloat(args[4]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return true;
                }
                Location loc = new Location(world,x,y,z,yaw,pitch);
                world.setSpawnLocation(loc);
                a.getSpawn().set("Spawns." + world.getName(),api.changeLocationToString(loc));
                sender.sendMessage(api.putLanguage("SetWorldSpawn","&a成功设置出生点！"));
                return true;
            } else {
                World world = Bukkit.getWorlds().get(0);
                try {
                    x = Double.parseDouble(args[0]) + 0.5D;
                    y = Double.parseDouble(args[1]) + 1.0D;
                    z = Double.parseDouble(args[2]) + 0.5D;
                    yaw = Float.parseFloat(args[3]);
                    pitch = Float.parseFloat(args[4]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return true;
                }
                Location loc = new Location(world,x,y,z,yaw,pitch);
                world.setSpawnLocation(loc);
                a.getSpawn().set("Spawns." + world.getName(),api.changeLocationToString(loc));
                sender.sendMessage(api.putLanguage("SetWorldSpawn","&a成功设置出生点！"));
            }
        }
        if (args.length == 3) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player)sender).getUniqueId(),"functions.command.setworldspawn")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.setworldspawn"));
                    return true;
                }
                Player p = (Player)sender;
                World world = p.getWorld();
                try {
                    x = Double.parseDouble(args[0]) + 0.5D;
                    y = Double.parseDouble(args[1]) + 1.0D;
                    z = Double.parseDouble(args[2]) + 0.5D;
                    yaw = p.getLocation().getYaw();
                    pitch = p.getLocation().getPitch();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return true;
                }
                Location loc = new Location(world,x,y,z,yaw,pitch);
                world.setSpawnLocation(loc);
                a.getSpawn().set("Spawns." + world.getName(),api.changeLocationToString(loc));
                sender.sendMessage(api.putLanguage("SetWorldSpawn","&a成功设置出生点！"));
                return true;
            } else {
                World world = Bukkit.getWorlds().get(0);
                try {
                    x = Double.parseDouble(args[0]) + 0.5D;
                    y = Double.parseDouble(args[1]) + 1.0D;
                    z = Double.parseDouble(args[2]) + 0.5D;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return true;
                }
                Location loc = new Location(world,x,y,z,yaw,pitch);
                world.setSpawnLocation(loc);
                a.getSpawn().set("Spawns." + world.getName(),api.changeLocationToString(loc));
                sender.sendMessage(api.putLanguage("SetWorldSpawn","&a成功设置出生点！"));
            }
        }
        if (args.length == 0) {
            if (sender instanceof Player) {
                if (!BukkitPermission.has(((Player)sender).getUniqueId(),"functions.command.setworldspawn")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.setworldspawn"));
                    return true;
                }
                Player p = (Player)sender;
                World world = p.getWorld();
                x = p.getLocation().getBlockX() + 0.5D;
                y = p.getLocation().getBlockY() + 1.0D;
                z = p.getLocation().getBlockZ() + 0.5D;
                yaw = p.getLocation().getYaw();
                pitch = p.getLocation().getPitch();
                Location loc = new Location(world,x,y,z,yaw,pitch);
                world.setSpawnLocation(loc);
                a.getSpawn().set("Spawns." + world.getName(),api.changeLocationToString(loc));
                sender.sendMessage(api.putLanguage("SetWorldSpawn","&a成功设置出生点！"));
                return true;
            }
            return true;
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
