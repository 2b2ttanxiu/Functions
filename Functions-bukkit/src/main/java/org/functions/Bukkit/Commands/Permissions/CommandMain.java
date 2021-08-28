package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Download;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.Permissions.BukkitPermission;
import org.functions.Bukkit.api.serverPing.PingResponse;
import org.functions.Bukkit.api.serverPing.ServerAddress;
import org.functions.Bukkit.api.serverPing.ServerPinger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandMain implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    public static void run() {
        Functions.instance.getCommand("functions").setExecutor(new CommandMain());
        Functions.instance.getCommand("functions").setTabCompleter(new CommandMain());
    }
    public String replace(String name,long time) {
        return name.replace("%time-ms%",((double)System.currentTimeMillis() - (double)time) / 1000.0D + "");
    }
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.main.*")) {
                if (!sender.isOp()) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.main.*"));
                    return true;
                }
            }
        }
        if (args.length == 0) {
            List<String> sf = new ArrayList<>();
            sf.add("&e作者: 2b2ttianxiu");
            sf.add("&aGitee: https://gitee.com/tianxiu2b2t/Functions");
            sf.add("&aGithub: https://2b2ttanxiu.github.io/");
            sf.add("&e团队:Functions");
            sf.add("&d邮箱: 794609509@qq.com");
            sf.add("&b插件版本: %version%");
            sf.add("&b感谢你使用: Functions!");
            if (sender instanceof Player) {
                for (String s1 : sf) {
                    sender.sendMessage(api.replace(sender, s1));
                }
            } else {
                for (String s1 : sf) {
                    sender.sendMessage(api.replace(s1,false));
                }
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
                start = System.currentTimeMillis();
                sender.sendMessage(api.putLanguage("saving-Plugin","&a正在保存..."));
                a.saveConfiguration();
                sender.sendMessage(replace(api.putLanguage("saved-Plugin","&a成功保存！(%time-ms%毫秒)"),start));
                return true;
            }
            if ("confirm".equalsIgnoreCase(args[0])) {
                start = System.currentTimeMillis();
                sender.sendMessage(api.putLanguage("Reloading-Plugin","&a正在重载..."));
                a.getServer().reload();
                sender.sendMessage(replace(api.putLanguage("Reloaded-Plugin","&a成功重载！(%time-ms%毫秒)"),start));
                return true;
            }
            if ("reloadData".equalsIgnoreCase(args[0])) {
                start = System.currentTimeMillis();
                sender.sendMessage(api.putLanguage("Reloading-Plugin","&a正在重载..."));
                a.getServer().reloadData();
                sender.sendMessage(replace(api.putLanguage("Reloaded-Plugin","&a成功重载！(%time-ms%毫秒)"),start));
                return true;
            }
            if ("copy".equalsIgnoreCase(args[0])) {
                start = System.currentTimeMillis();
                sender.sendMessage(api.putLanguage("Copy-plugin_Check","&a正在检测下载文件夹里有新版本"));
                File dir = new File(Functions.instance.getDataFolder().getAbsolutePath(), "Releases");
                if (!dir.exists()) {
                    sender.sendMessage(api.putLanguage("Copy-plugin_Check_NotFound","&c没有可用版本！"));
                    return true;
                } else {
                    sender.sendMessage(api.putLanguage("Copy-plugin-readVersion","&a正在读取文件版本！"));
                }
                sender.sendMessage(api.putLanguage("Copy-plugin-ing","&a正在复制中！"));
                return true;
            }
            if ("bungeecord".equalsIgnoreCase(args[0]) || "bungee".equalsIgnoreCase(args[0])) {
                sender.sendMessage(api.putLanguage("QueryServerBungeeCord","&a你的服务器的跨服状态为：%bungeecord%"));
                return true;
            }
        }
        if ("QueryServer".equalsIgnoreCase(args[0])) {
            sender.sendMessage(api.putLanguage("QueryServer", "&a正在查询..."));
            PingResponse ping = null;
            if (args.length == 2) {
                if (args[1].contains(":")) {
                    String[] split = args[1].split(":");
                    ping = ServerPinger.fetchData(new ServerAddress(split[2], Integer.parseInt(split[1])), 3000);
                    assert ping != null;
                    sender.sendMessage(ping.getOnlinePlayers() + "/" + ping.getMaxPlayers());
                    sender.sendMessage(ping.getMotd());
                    sender.sendMessage(ping.getProtocol() + " " + ping.getName());
                    sender.sendMessage(api.putLanguage("QueryServer-from","&a查询服务器地址: %address%").replace("%address%",args[1]));
                    sender.sendMessage(api.putLanguage("QueryServer-Successfully", "&a查询成功！"));
                    return true;
                }
                ping = ServerPinger.fetchData(new ServerAddress(args[1], 25565), 3000);
                sender.sendMessage(ping.getOnlinePlayers() + "/" + ping.getMaxPlayers());
                sender.sendMessage(ping.getMotd());
                sender.sendMessage(ping.getProtocol() + " " + ping.getName());
                sender.sendMessage(api.putLanguage("QueryServer-from","&a查询服务器地址: %address%").replace("%address%",args[1] + ":25565"));
                sender.sendMessage(api.putLanguage("QueryServer-Successfully", "&a查询成功！"));
                return true;
            }
            if (args.length == 3) {
                ping = ServerPinger.fetchData(new ServerAddress(args[1], Integer.parseInt(args[2])), 3000);
                sender.sendMessage(ping.getOnlinePlayers() + "/" + ping.getMaxPlayers());
                sender.sendMessage(ping.getMotd());
                sender.sendMessage(ping.getProtocol() + " " + ping.getName());
                sender.sendMessage(api.putLanguage("QueryServer-from","&啊查询服务器地址: %address%").replace("%address%",args[1]));
                sender.sendMessage(api.putLanguage("QueryServer-Successfully", "&啊查询成功！"));
                return true;
            }
        }
        return true;
    }
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        List<String> list = new ArrayList<>();
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.main.*")) {
                if (!sender.isOp()) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.main.*"));
                    return ls;
                }
            }
        }
        ls.add("reloadData");
        ls.add("confirm");
        ls.add("reload");
        ls.add("save");
        ls.add("queryServer");
        ls.add("copy");
        ls.add("bungee");
        Collections.sort(ls);
        if (args.length == 1) {
            for (String s : ls) {
                if (s.startsWith(args[0])) {
                    list.add(s);
                }
            }
            Collections.sort(list);
            return list;
        }
        return ls;
   }
}
