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
import java.util.List;

public class CommandDeleteOperator implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("deop",new CommandDeleteOperator());
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            data = new Data(p.getUniqueId());
            if (!BukkitPermission.has(data.getUUID(),"functions.command.deop")) {
                sender.sendMessage(BukkitPermission.noPerms("functions.command.deop"));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (args.length == 1) {
                for (String v : a.getOperators()) {
                    if (v.equalsIgnoreCase(target.getName())) {
                        List<String> ls = a.getOperators();
                        ls.remove(args[0]);
                        a.saveOperators(ls);
                        sender.sendMessage(api.putLanguage("DeleteOperator","&a成功删除 %target% 的管理员！").replace("%target%",target.getName()));
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
                    if (v.equalsIgnoreCase(target.getName())) {
                        List<String> ls = a.getOperators();
                        ls.remove(args[0]);
                        a.saveOperators(ls);
                        sender.sendMessage(api.putLanguage("DeleteOperator","&a成功删除 %target% 的管理员！").replace("%target%",target.getName()));
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
            if (!BukkitPermission.has(data.getUUID(),"functions.command.deop")) {
                return ls;
            }
        }
        ls.addAll(a.getOperators());
        return ls;
    }
}
