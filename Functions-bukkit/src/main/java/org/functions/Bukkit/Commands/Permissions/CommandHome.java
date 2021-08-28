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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CommandHome implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("home",new CommandHome());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(api.putLanguage("NoPlayer","&c这条指令只能由玩家来执行"));
            return true;
        }
        Player p = (Player)sender;
        if (!BukkitPermission.has(data.getUUID(),"functions.command.home")) {
            sender.sendMessage(BukkitPermission.noPerms("functions.command.home"));
            return true;
        }
        data = new Data(p.getUniqueId());
        if (args.length == 0) {
            sender.sendMessage(api.putLanguage("ListHomes","家："));
            for (String s : data.getHomes().getLists()) {
                sender.sendMessage(data.getMainClass().getSettings().getString("Format.Home").replace("%home%",s).replace("%position%",api.toLocation(data.getHomes().getLocation(s))));
            }
            return true;
        }
        if (args.length == 1) {
            Location loc;
            boolean is = false;
            String home = "";
            for (String s : data.getHomes().getLists()) {
                if (s.equalsIgnoreCase(args[0])) {
                    home = s;
                    is = true;
                }
            }
            if (is) {
                loc = data.getHomes().getLocation(home);
                p.teleport(loc);
                sender.sendMessage(api.putLanguage("TeleportHome", "&a成功传送到 %home% 的家 (%position%)").replace("%home%", args[0]).replace("%position%", api.toLocation(loc)));
                return true;
            }
            sender.sendMessage(api.putLanguage("NotFoundHome", "&c没有找到 %home% 的家").replace("%home%", args[0]));
            return true;
        }
        if (args.length == 2) {
            if ("teleport".equalsIgnoreCase(args[0])) {
                Location loc;
                boolean is = false;
                String home = "";
                for (String s : data.getHomes().getLists()) {
                    if (s.equalsIgnoreCase(args[1])) {
                        home = s;
                        is = true;
                    }
                }
                if (is) {
                    loc = data.getHomes().getLocation(home);
                    p.teleport(loc);
                    sender.sendMessage(api.putLanguage("TeleportHome", "&a成功传送到 %home% 的家 (%position%)").replace("%home%", home).replace("%position%", api.toLocation(loc)));
                    return true;
                }
                sender.sendMessage(api.putLanguage("NotFoundHome", "&c没有找到 %home% 的家").replace("%home%", args[1]));
                return true;
            }
            if ("create".equalsIgnoreCase(args[0])) {
                if (!data.getHomes().exists(args[1])) {
                    data.getHomes().create(args[1],p.getLocation());
                    sender.sendMessage(api.putLanguage("CreateHome","&a创建 %home% 成功！").replace("%home%",args[1]));
                    return true;
                }
                sender.sendMessage(api.putLanguage("NotCreateHome","&c创建 %home% 失败！是否有了此家？").replace("%home%",args[1]));
                return true;
            }
            if ("remove".equalsIgnoreCase(args[0]) || "delete".equalsIgnoreCase(args[0])) {
                if (data.getHomes().exists(args[1])) {
                    data.getHomes().delete(args[1]);
                    sender.sendMessage(api.putLanguage("DeleteHome","&a删除 %home% 成功！").replace("%home%",args[1]));
                    return true;
                }
                sender.sendMessage(api.putLanguage("NotDeleteHome","&c删除 %home% 失败！是否有了此家？").replace("%home%",args[1]));
                return true;
            }
            if ("reset".equalsIgnoreCase(args[0])) {
                if (data.getHomes().exists(args[1])) {
                    data.getHomes().delete(args[1]);
                    data.getHomes().create(args[1],p.getLocation());
                    sender.sendMessage(api.putLanguage("ResetHome","&c重置 %home% 坐标成功！").replace("%home%",args[1]));
                    return true;
                }
                sender.sendMessage(api.putLanguage("NotResetHome","&c重置 %home% 坐标失败！是否有了此家？").replace("%home%",args[1]));
                return true;
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        if (sender instanceof Player) {

            List<String> arg1 = new ArrayList<>();
            data = new Data(((Player) sender).getUniqueId());
            if (!BukkitPermission.has(data.getUUID(),"functions.command.home")) {
                return ls;
            }
            ls = data.getHomes().getLists();
            if (args.length == 1) {
                ls = data.getHomes().getLists();
                Collections.sort(ls);
                ls.add("create");
                ls.add("reset");
                ls.add("remove");
                ls.add("teleport");
                return ls;
            }
            if (args.length == 2) {
                Collections.sort(ls);
                for (String s : ls) {
                    if (s.startsWith(args[1])) {
                        arg1.add(s);
                    }
                }
                return arg1;
            }
        }
        return ls;
    }
}
