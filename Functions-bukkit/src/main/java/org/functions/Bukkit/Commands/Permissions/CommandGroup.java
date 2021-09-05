package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.Main.Group;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.Permissions.BukkitPermission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandGroup implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    public static void run() {
        Functions.instance.getAPI().getCommand("group", new CommandGroup());
    }
    Data data;
    Group group;
    boolean has;
    String g;
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 2) {
                if ("mute".equalsIgnoreCase(args[0])) {
                    Player p = (Player)sender;
                    if (!BukkitPermission.has(p.getUniqueId(),"functions.command.group.*")) {
                        if (!BukkitPermission.has(p.getUniqueId(),"functions.command.group.mute")) {
                            sender.sendMessage(BukkitPermission.noPerms("functions.command.group.mute"));
                            return true;
                        }
                    }
                    for (String v : api.getGroups()) {
                        if (v.equalsIgnoreCase(args[1])) {
                            group = new Group(v);
                        }
                    }
                    if (!group.getMute().exists()) {
                        group.getMute().setMute(true);
                        sender.sendMessage(api.putLanguage("Muting-Group","&a成功禁言 %group% 用户组！").replace("%group%",args[1]));
                        return true;
                    } else {
                        group.getMute().setUnTempMute();
                        group.getMute().setMute(false);
                        sender.sendMessage(api.putLanguage("unMuting-Group","&a已解除 %group% 的禁言").replace("%group%",args[1]));
                        return true;
                    }
                }
            }
            if (args.length == 3) {
                Player p = (Player)sender;
                if ("tempmute".equalsIgnoreCase(args[0])) {
                    if (!BukkitPermission.has(p.getUniqueId(),"functions.command.group.*")) {
                        if (!BukkitPermission.has(p.getUniqueId(),"functions.command.group.tempmute")) {
                            sender.sendMessage(BukkitPermission.noPerms("functions.command.group.tmpmute"));
                            return true;
                        }
                    }
                    long minute = 0;
                    try {
                        minute = Long.parseLong(args[2]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    for (String v : api.getGroups()) {
                        if (v.equalsIgnoreCase(args[1])) {
                            group = new Group(v);
                        }
                    }
                    if (!group.getMute().exists()) {
                        group.getMute().setTempMute(System.currentTimeMillis(),System.currentTimeMillis() + (minute * 1000));
                        sender.sendMessage(api.putLanguage("Muting-Group","&a成功禁言 %group% 用户组！").replace("%group%",args[1]));
                        return true;
                    } else {
                        group.getMute().setUnTempMute();
                        group.getMute().setMute(false);
                        sender.sendMessage(api.putLanguage("unMuting-Group","&a已解除 %group% 的禁言").replace("%group%",args[1]));
                        return true;
                    }
                }
                Player target = api.getServer().getPlayer(args[1]);
                if (target.isOnline()) {
                    if ("set".equalsIgnoreCase(args[0])) {
                        if (!BukkitPermission.has(p.getUniqueId(),"functions.command.group.*")) {
                            if (!BukkitPermission.has(p.getUniqueId(),"functions.command.group.set")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.group.set"));
                                return true;
                            }
                        }
                        Data data = new Data(target.getUniqueId());
                        if (data.hasGroup(args[2])) {
                            sender.sendMessage(api.putLanguage("hasGroup","&c这名 %target%的玩家已经在 %group% 用户组了！").replace("%target%",target.getName()).replace("%player%",sender.getName()).replace("%group%",args[2]));
                            return true;
                        }
                        for (String group : api.getGroups()) {
                            if (group.equalsIgnoreCase(args[2])) {
                                has = true;
                                g = group;
                            }
                        }
                        if (!has) {
                            sender.sendMessage(api.putLanguage("NotFoundGroup","&c找不到 %group% 用户组！").replace("%target%",target.getName()).replace("%player%",sender.getName()).replace("%group%",args[2]));
                            return true;
                        }
                        data.setGroup(g);
                        data.save();
                        sender.sendMessage(api.putLanguage("setGroup","&a成功设置 %target% 的用户组！").replace("%target%",target.getName()).replace("%player%",sender.getName()).replace("%group%",args[2]));
                        return true;
                    }
                } else {
                    sender.sendMessage(api.putLanguage("PlayerNoOnline","&c玩家 %target% 不在线！").replace("%target%",target.getName()).replace("%player%",sender.getName()).replace("%group%",args[2]));
                    return true;
                }
            }
        } else {
            if (args.length == 2) {
                if ("mute".equalsIgnoreCase(args[0])) {
                    for (String v : api.getGroups()) {
                        if (v.equalsIgnoreCase(args[1])) {
                            group = new Group(v);
                        }
                    }
                    if (!group.getMute().exists()) {
                        group.getMute().setMute(true);
                        sender.sendMessage(api.putLanguage("Muting-Group","&a成功禁言 %group% 用户组！").replace("%group%",args[1]));
                        return true;
                    } else {
                        group.getMute().setUnTempMute();
                        group.getMute().setMute(false);
                        sender.sendMessage(api.putLanguage("unMuting-Group","&a已解除 %group% 的禁言").replace("%group%",args[1]));
                        return true;
                    }
                }
            }
            if (args.length == 3) {
                if ("tempmute".equalsIgnoreCase(args[0])) {
                    long minute = 0;
                    try {
                        minute = Long.parseLong(args[2]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    for (String v : api.getGroups()) {
                        if (v.equalsIgnoreCase(args[1])) {
                            group = new Group(v);
                        }
                    }
                    if (!group.getMute().exists()) {
                        group.getMute().setTempMute(System.currentTimeMillis(),System.currentTimeMillis() + (minute * 1000));
                        sender.sendMessage(api.putLanguage("Muting-Group","&a成功禁言 %group% 用户组！").replace("%group%",args[1]));
                        return true;
                    } else {
                        group.getMute().setUnTempMute();
                        group.getMute().setMute(false);
                        sender.sendMessage(api.putLanguage("unMuting-Group","&a已解除 %group% 的禁言").replace("%group%",args[1]));
                        return true;
                    }
                }
                Player target = api.getServer().getPlayer(args[1]);
                if (target.isOnline()) {
                    if ("set".equalsIgnoreCase(args[0])) {
                        Data data = new Data(target.getUniqueId());
                        if (data.hasGroup(args[2])) {
                            sender.sendMessage(api.putLanguage("hasGroup","&c这名 %target%的玩家已经在 %group% 用户组了！").replace("%target%",target.getName()).replace("%player%",sender.getName()).replace("%group%",args[2]));
                            return true;
                        }
                        for (String group : api.getGroups()) {
                            if (group.equalsIgnoreCase(args[2])) {
                                has = true;
                                g = group;
                            }
                        }
                        if (!has) {
                            sender.sendMessage(api.putLanguage("NotFoundGroup","&c找不到 %group% 用户组！").replace("%target%",target.getName()).replace("%player%",sender.getName()).replace("%group%",args[2]));
                            return true;
                        }
                        data.setGroup(g);
                        data.save();
                        sender.sendMessage(api.putLanguage("setGroup","&a成功设置 %target% 的用户组！").replace("%target%",target.getName()).replace("%player%",sender.getName()).replace("%group%",args[2]));
                        return true;
                    }
                } else {
                    sender.sendMessage(api.putLanguage("PlayerNoOnline","&c玩家 %target% 不在线！").replace("%target%",target.getName()).replace("%player%",sender.getName()).replace("%group%",args[2]));
                    return true;
                }
            }
        }
        return true;
    }
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> ls1 = new ArrayList<>();
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.group.*")) {
                return ls1;
            }
        }
        ls1.add("set");
        if (args.length == 1) {
            Collections.sort(ls1);
            return ls1;
        }
        List<String> playerlist = new ArrayList<>();
        List<String> pl = new ArrayList<>();
        if (args.length == 2) {
            for (Player p : api.getOnlinePlayers()) {
                playerlist.add(p.getName());
            }
            Collections.sort(playerlist);
            for (String l : playerlist) {
                if (l.startsWith(args[1])) {
                    pl.add(l);
                }
            }
            Collections.sort(pl);
            return pl;
        }
        List<String> groups = new ArrayList<>();
        if (args.length == 3) {
            for (String group : api.getGroups()) {
                if (group.startsWith(args[2])) {
                    groups.add(group);
                }
            }
            Collections.sort(groups);
            return groups;
        }
        return ls1;
    }
}
