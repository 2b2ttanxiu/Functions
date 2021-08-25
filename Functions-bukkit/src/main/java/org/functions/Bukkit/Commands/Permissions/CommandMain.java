package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.api.API;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandMain implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    public static void run() {
        Functions.instance.getCommand("").setExecutor(new CommandMain());
        Functions.instance.getCommand("").setTabCompleter(new CommandMain());
    }
    public String replace(String name,long time) {
        return name.replace("%time-ms%",((double)System.currentTimeMillis() - (double)time) / 1000.0D + "");
    }
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (args.length == 0) {
            List<String> sf = new ArrayList<>();
            sf.add("&e作者: 2b2ttianxiu");
            sf.add("&aGitee: https://gitee.com/tianxiu2b2t/Functions");
            sf.add("&aGithub: https://2b2ttanxiu.github.io/");
            sf.add("&e团队:Functions");
            sf.add("&d邮箱: 794609509@qq.com");
            sf.add("&b插件版本: %version%");
            sf.add("&b感谢你使用: Functions!");
            for (String s1 : sf) {
                sender.sendMessage(api.replace(sender,s1));
                return true;
            }
            return true;
        }
        if (args.length == 1) {
            long start = 0;
            if ("reload".equalsIgnoreCase(args[0])) {
                start = System.currentTimeMillis();
                sender.sendMessage(api.putLanguage("Reloading-Plugin","&a正在重载..."));
                a.install();
                sender.sendMessage(replace(api.putLanguage("Reloaded-Plugin","&a成功重载！(%time-ms%毫秒)"),start));
                return true;
            }
            if ("save".equalsIgnoreCase(args[0])) {
                sender.sendMessage(api.putLanguage("Reloading-Plugin","&a正在重载..."));
                a.saveConfiguration();
                sender.sendMessage(replace(api.putLanguage("Reloaded-Plugin","&a成功重载！(%time-ms%毫秒)"),start));
                return true;
            }
            if ("confirm".equalsIgnoreCase(args[0])) {
                sender.sendMessage(api.putLanguage("Reloading-Plugin","&a正在重载..."));
                a.getServer().reload();
                sender.sendMessage(replace(api.putLanguage("Reloaded-Plugin","&a成功重载！(%time-ms%毫秒)"),start));
                return true;
            }
            if ("reloadData".equalsIgnoreCase(args[0])) {
                sender.sendMessage(api.putLanguage("Reloading-Plugin","&a正在重载..."));
                a.getServer().reloadData();
                sender.sendMessage(replace(api.putLanguage("Reloaded-Plugin","&a成功重载！(%time-ms%毫秒)"),start));
                return true;
            }
        }
        return true;
    }
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        ls.add("reloadData");
        ls.add("confirm");
        ls.add("reload");
        ls.add("save");
        Collections.sort(ls);
        List<String> list = new ArrayList<>();
        if (args.length > 0) {
            if (args[0]==null) {
                return ls;
            }
            for (String s : ls) {
                if (args[0].startsWith(s)) {
                    list.add(s);
                }
            }
            Collections.sort(list);
            return list;
        }
        return ls;
    }
}
