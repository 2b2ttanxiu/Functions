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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CommandOperator implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("op",new CommandOperator());
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            data = new Data(p.getUniqueId());
            if (!BukkitPermission.has(data.getUUID(),"functions.command.op")) {
                sender.sendMessage(BukkitPermission.noPerms("functions.command.op"));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (args.length == 1) {
                for (String v : a.getOperators()) {
                    if (!v.equalsIgnoreCase(target.getName())) {
                        List<String> ls = a.getOperators();
                        ls.add(args[0]);
                        a.saveOperators(ls);
                        sender.sendMessage(api.putLanguage("SetOperator","&a成功设置 %target% 为管理员！").replace("%target%",target.getName()));
                        return true;
                    }
                }
                sender.sendMessage(api.putLanguage("IsOperator","&c%target% 是管理员？").replace("%target%",target.getName()));
                return true;
            }
            sender.sendMessage(api.putLanguage("NotFoundOperators","&c没有找到这名玩家！"));
            return true;
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (args.length == 1) {
                for (String v : a.getOperators()) {
                    if (!v.equalsIgnoreCase(target.getName())) {
                        List<String> ls = a.getOperators();
                        ls.add(args[0]);
                        a.saveOperators(ls);
                        sender.sendMessage(api.putLanguage("SetOperator","&a成功设置 %target% 为管理员！").replace("%target%",target.getName()));
                        return true;
                    }
                }
                sender.sendMessage(api.putLanguage("IsOperator","&c%target% 是管理员？").replace("%target%",target.getName()));
                return true;
            }
            sender.sendMessage(api.putLanguage("NotFoundOperators","&c没有找到这名玩家！"));
            return true;
        }
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String s,  String[] args) {
        List<String> ls = new ArrayList<>();
        if (sender instanceof Player) {
            if (!BukkitPermission.has(data.getUUID(),"functions.command.op")) {
                return ls;
            }
        }
        ls.addAll(a.getOperators());
        Collections.sort(ls);
        List<String> ls1 = new ArrayList<>();
        if (args.length == 1) {
            for (String v : ls) {
                if (v.startsWith(args[0])) {
                    ls1.add(v);
                }
            }
            return ls1;
        }
        return ls;
    }
}
