package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.Permissions.BukkitPermission;
import org.functions.Bukkit.api.Warps;
import org.functions.Bukkit.api.WarpsManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandWarps implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("warps",new CommandWarps());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(api.putLanguage("NoPlayer","&c这条指令只能由玩家来执行"));
            return true;
        }
        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.warp")) {
            sender.sendMessage(BukkitPermission.noPerms("functions.command.warp"));
            return true;
        }
        WarpsManager warp = new WarpsManager();
        Warps w;
        String f = a.getSettings().getString("Warp");
        Player p = (Player)sender;
        if (args.length == 0) {
            String format = a.getSettings().getString("Warps");
            String list = "";
            int i = 0;
            for (Warps s : warp.getWarps()) {
                i++;
                list = list + format.replace("%warp%", s.getName()).replace("%position%",api.toLocation(s.getLocation())).replace("%count%",i+"");
            }
            sender.sendMessage(api.putLanguage("ShowWarps","&a地标：%warps%").replace("%warps%",list));
            return true;
        }
        if (args.length == 1) {
            for (Warps s : warp.getWarps()) {
                if (s.getName().equalsIgnoreCase(args[0])) {
                    p.teleport(s.getLocation());
                    sender.sendMessage(api.putLanguage("TeleportWarp","&a成功传送到 %warp% 地标").replace("%warp%",f.replace("%name%",s.getName()).replace("%position%",api.toLocation(s.getLocation()))));
                    return true;
                }
            }
            sender.sendMessage(api.putLanguage("IsWarp","&c是否有了地标？"));
            return true;
        }
        Warps c;
        if (args.length == 2) {
            for (Warps s : warp.getWarps()) {
                if ("teleport".equalsIgnoreCase(args[0])) {
                    if (s.getName().equalsIgnoreCase(args[1])) {
                        p.teleport(s.getLocation());
                        sender.sendMessage(api.putLanguage("TeleportWarp","&a成功传送到 %warp% 地标").replace("%warp%",f.replace("%name%",s.getName()).replace("%position%",api.toLocation(s.getLocation()))));
                        return true;
                    }
                    sender.sendMessage(api.putLanguage("IsWarp","&c是否有了地标？"));
                    return true;
                }
                if ("remove".equalsIgnoreCase(args[0])) {
                    if (s.getName().equalsIgnoreCase(args[1])) {
                        w = s;
                        warp.removeWarp(s.getName());
                        sender.sendMessage(api.putLanguage("removeWarp","&a成功移除 %warp% 地标").replace("%warp%",f.replace("%name%",w.getName()).replace("%position%",api.toLocation(w.getLocation()))));
                        return true;
                    }
                    sender.sendMessage(api.putLanguage("IsWarp","&c是否有了地标？"));
                    return true;
                }
                if ("reset".equalsIgnoreCase(args[0])) {
                    if (s.getName().equalsIgnoreCase(args[1])) {
                        w = new Warps(args[1],p.getLocation());
                        warp.setWarp(w);
                        sender.sendMessage(api.putLanguage("resetWarp","&a成功重置 %warp% 地标到你脚下").replace("%warp%",f.replace("%name%",w.getName()).replace("%position%",api.toLocation(w.getLocation()))));
                        return true;
                    }
                    sender.sendMessage(api.putLanguage("IsWarp","&c是否有了地标？"));
                    return true;
                }
                if ("add".equalsIgnoreCase(args[0])) {
                    if (!s.getName().equalsIgnoreCase(args[1])) {
                        w = new Warps(args[1],p.getLocation());
                        warp.addWarp(w);
                        sender.sendMessage(api.putLanguage("addWarp","&a成功添加 %warp% 地标").replace("%warp%",f.replace("%name%",s.getName()).replace("%position%",api.toLocation(s.getLocation()))));
                        return true;
                    }
                    sender.sendMessage(api.putLanguage("IsWarp","&c是否有了地标？"));
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        ls.add("add");
        ls.add("remove");
        ls.add("reset");
        ls.add("teleport");
        if (args.length == 1) {
            if (args[0]==null) {
                return ls;
            }
            for (String s : ls) {
                if (s.startsWith(args[0])) {
                    temp.add(s);
                }
            }
            return temp;
        }
        if (args.length == 2) {
            for (Warps warp : new WarpsManager().getWarps()) {
                ls.add(warp.getName());
            }
            if (args[1]==null) {
                return ls;
            }
            for (String s : ls) {
                if (s.startsWith(args[1])) {
                    temp.add(s);
                }
            }
            return temp;
        }
        return ls;
    }
}
