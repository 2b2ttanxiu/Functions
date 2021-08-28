package org.functions.Bukkit.Commands.Permissions;

import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import org.bukkit.Bukkit;
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

public class CommandPermission implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("permission",new CommandPermission());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.permission")) {
                sender.sendMessage(BukkitPermission.noPerms("functions.command.permission"));
                return true;
            }
            Group group;
            if (args.length == 4) {
                if ("group".equalsIgnoreCase(args[0])) {
                    for (String s : api.getGroups()) {
                        if (s.equalsIgnoreCase(args[1])) {
                            group = new Group(s);
                            if ("add".equalsIgnoreCase(args[2])) {
                                List<String> ls = group.getPermissions();
                                for (String v : ls) {
                                    if (v.equalsIgnoreCase(args[3])) {
                                        ls.add(args[3]);
                                        group.setPermissions(ls);
                                        group.save();
                                        sender.sendMessage(api.putLanguage("addPermission-group","&a成功添加 %group% 用户组的权限！").replace("%group%",group.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                                        return true;
                                    }
                                }
                                sender.sendMessage(api.putLanguage("isPermission-group","&c%group% 用户组的权限是否在权限列表里？").replace("%group%",group.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                                return true;
                            }
                            if ("remove".equalsIgnoreCase(args[2])) {
                                List<String> ls = group.getPermissions();
                                for (String v : ls) {
                                    if (!v.equalsIgnoreCase(args[3])) {
                                        ls.remove(args[3]);
                                        group.setPermissions(ls);
                                        group.save();
                                        sender.sendMessage(api.putLanguage("removePermission-group","&a成功移除 %group% 用户组的权限！").replace("%group%",group.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                                        return true;
                                    }
                                }
                                sender.sendMessage(api.putLanguage("isPermission-group","&c%group% 用户组的权限是否在权限列表里？").replace("%group%",group.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                                return true;
                            }
                            List<String> ls = group.getPermissions();
                            StringBuilder format = new StringBuilder();
                            for (String v : ls) {
                                format.append(v).append(", ");
                            }
                            sender.sendMessage(api.putLanguage("ShowPermission-Group","&a%group%用户组权限如下： %permissions%").replace("%permissions%", format.toString()).replace("%group%",group.getName()));
                            return true;
                        }
                    }
                    sender.sendMessage(api.putLanguage("NotFoundGroup","&c用户组是否存在？"));
                    return true;
                }
                if ("user".equalsIgnoreCase(args[0])) {
                    Player user = Bukkit.getPlayer(args[1]);
                    if (user.isOnline()) {
                        data = new Data(user.getUniqueId());
                        if ("add".equalsIgnoreCase(args[2])) {
                            List<String> ls = data.getPermissionManager().getPermissions();
                            for (String v : ls) {
                                if (!v.equalsIgnoreCase(args[3])) {
                                    ls.add(args[3]);
                                    data.getPermissionManager().setPermissions(ls);
                                    data.save();
                                    sender.sendMessage(api.putLanguage("addPermission-user","&a成功添加 %target% 用户的权限！").replace("%target%",user.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                                    return true;
                                }
                            }
                            sender.sendMessage(api.putLanguage("isPermission-user","&c%target% 用户的权限是否在权限列表里？").replace("%target%",user.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                            return true;
                        }
                        if ("remove".equalsIgnoreCase(args[2])) {
                            List<String> ls = data.getPermissionManager().getPermissions();
                            for (String v : ls) {
                                if (v.equalsIgnoreCase(args[3])) {
                                    ls.remove(args[3]);
                                    data.getPermissionManager().setPermissions(ls);
                                    data.save();
                                    sender.sendMessage(api.putLanguage("removePermission-user","&a成功移除 %target% 用户的权限！").replace("%target%",user.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                                    return true;
                                }
                            }
                            sender.sendMessage(api.putLanguage("isPermission-user","&c%target% 用户的权限是否在权限列表里？").replace("%target%",user.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                            return true;
                        }
                        List<String> ls = data.getPermissionManager().getPermissions();
                        StringBuilder format = new StringBuilder();
                        for (String v : ls) {
                            format.append(v).append(", ");
                        }
                        sender.sendMessage(api.putLanguage("ShowPermission-User","&a%user%用户权限如下： %permissions%").replace("%permissions%", format.toString()).replace("%user%",user.getName()));
                        return true;
                    }
                    sender.sendMessage(api.putLanguage("isOnlinePlayer","&c玩家是否在线？"));
                    return true;
                }
            }
        } else {
            Group group;
            if (args.length == 4) {
                if ("group".equalsIgnoreCase(args[0])) {
                    for (String s : api.getGroups()) {
                        if (s.equalsIgnoreCase(args[1])) {
                            group = new Group(s);
                            if ("add".equalsIgnoreCase(args[2])) {
                                List<String> ls = group.getPermissions();
                                for (String v : ls) {
                                    if (v.equalsIgnoreCase(args[3])) {
                                        ls.add(args[3]);
                                        group.setPermissions(ls);
                                        group.save();
                                        sender.sendMessage(api.putLanguage("addPermission-group","&a成功添加 %group% 用户组的权限！").replace("%group%",group.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                                        return true;
                                    }
                                }
                                sender.sendMessage(api.putLanguage("isPermission-group","&c%group% 用户组的权限是否在权限列表里？").replace("%group%",group.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                                return true;
                            }
                            if ("remove".equalsIgnoreCase(args[2])) {
                                List<String> ls = group.getPermissions();
                                for (String v : ls) {
                                    if (!v.equalsIgnoreCase(args[3])) {
                                        ls.remove(args[3]);
                                        group.setPermissions(ls);
                                        group.save();
                                        sender.sendMessage(api.putLanguage("removePermission-group","&a成功移除 %group% 用户组的权限！").replace("%group%",group.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                                        return true;
                                    }
                                }
                                sender.sendMessage(api.putLanguage("isPermission-group","&c%group% 用户组的权限是否在权限列表里？").replace("%group%",group.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                                return true;
                            }
                            List<String> ls = group.getPermissions();
                            StringBuilder format = new StringBuilder();
                            for (String v : ls) {
                                format.append(v).append(", ");
                            }
                            sender.sendMessage(api.putLanguage("ShowPermission-Group","&a%group%用户组权限如下： %permissions%").replace("%permissions%", format.toString()).replace("%group%",group.getName()));
                            return true;
                        }
                    }
                    sender.sendMessage(api.putLanguage("NotFoundGroup","&c用户组是否存在？"));
                    return true;
                }
                if ("user".equalsIgnoreCase(args[0])) {
                    Player user = Bukkit.getPlayer(args[1]);
                    if (user.isOnline()) {
                        data = new Data(user.getUniqueId());
                        if ("add".equalsIgnoreCase(args[2])) {
                            List<String> ls = data.getPermissionManager().getPermissions();
                            for (String v : ls) {
                                if (!v.equalsIgnoreCase(args[3])) {
                                    ls.add(args[3]);
                                    data.getPermissionManager().setPermissions(ls);
                                    data.save();
                                    sender.sendMessage(api.putLanguage("addPermission-user","&a成功添加 %target% 用户的权限！").replace("%target%",user.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                                    return true;
                                }
                            }
                            sender.sendMessage(api.putLanguage("isPermission-user","&c%target% 用户的权限是否在权限列表里？").replace("%target%",user.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                            return true;
                        }
                        if ("remove".equalsIgnoreCase(args[2])) {
                            List<String> ls = data.getPermissionManager().getPermissions();
                            for (String v : ls) {
                                if (v.equalsIgnoreCase(args[3])) {
                                    ls.remove(args[3]);
                                    data.getPermissionManager().setPermissions(ls);
                                    data.save();
                                    sender.sendMessage(api.putLanguage("removePermission-user","&a成功移除 %target% 用户的权限！").replace("%target%",user.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                                    return true;
                                }
                            }
                            sender.sendMessage(api.putLanguage("isPermission-user","&c%target% 用户的权限是否在权限列表里？").replace("%target%",user.getName()).replace("%player%",sender.getName()).replace("%permission%",args[3]));
                            return true;
                        }
                        List<String> ls = data.getPermissionManager().getPermissions();
                        StringBuilder format = new StringBuilder();
                        for (String v : ls) {
                            format.append(v).append(", ");
                        }
                        sender.sendMessage(api.putLanguage("ShowPermission-User","&a%user%用户权限如下： %permissions%").replace("%permissions%", format.toString()).replace("%user%",user.getName()));
                        return true;
                    }
                    sender.sendMessage(api.putLanguage("isOnlinePlayer","&c玩家是否在线？"));
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> args0 = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        List<String> args1 = new ArrayList<>();
        List<String> args2 = new ArrayList<>();
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.permission")) {
                return null;
            }
        }
        args0.add("group");
        args0.add("user");
        Collections.sort(args0);
        if (args.length == 0) {
            return args0;
        }
        if (args.length == 1) {
            if (args[0]==null) {
                return args0;
            }
            for (String s : args0) {
                if (s.startsWith(args[0])) {
                    temp.add(s);
                }
            }
            return temp;
        }
        if (args.length == 2) {
            args1.add("remove");
            args1.add("add");
            Collections.sort(args1);
            if (args[1]==null) {
                return args1;
            }
            for (String s : args1) {
                if (s.startsWith(args[1])) {
                    temp.add(s);
                }
            }
            return temp;
        }
        return null;
    }
}
