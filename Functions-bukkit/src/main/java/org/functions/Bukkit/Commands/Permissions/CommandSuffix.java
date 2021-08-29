package org.functions.Bukkit.Commands.Permissions;

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

public class CommandSuffix implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("suffix",new CommandSuffix());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0] == null || args[1] == null || args[2] == null) {
            return true;
        }
        if ("user".equalsIgnoreCase(args[0])) {
            Player other = Bukkit.getServer().getPlayer(args[1]);
            if (other.isOnline()) {
                if ("use".equalsIgnoreCase(args[2])) {
                    if (sender instanceof Player) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.user.*")) {
                                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.user.use")) {
                                    sender.sendMessage(BukkitPermission.noPerms("functions.command.suffix.user.use"));
                                    return true;
                                }
                            }
                        }
                    }
                    data = new Data(other.getUniqueId());
                    List<String> suffixes = data.getSuffixes().getSuffixes();
                    int i = 0;
                    try {
                        i = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return true;
                    }
                    if (i > suffixes.size()) {
                        return true;
                    }
                    data.getSuffixes().setSuffix(suffixes.get(i));
                    sender.sendMessage(api.putLanguage("useUserSuffix", "&a成功使用尾街"));
                    return true;
                }
                if ("set".equalsIgnoreCase(args[2])) {
                    if (sender instanceof Player) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.user.*")) {
                                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.user.set")) {
                                    sender.sendMessage(BukkitPermission.noPerms("functions.command.suffix.user.set"));
                                    return true;
                                }
                            }
                        }
                    }
                    StringBuilder suffix = new StringBuilder();
                    for (int i = 3; i < args.length; i++) {
                        suffix.append(args[i]).append(" ");
                    }
                    data = new Data(other.getUniqueId());
                    if (suffix.toString().startsWith("'") || suffix.toString().endsWith("'")) {
                        data.getSuffixes().setSuffix(suffix.toString().replace("'", ""));
                        sender.sendMessage(api.putLanguage("SetUserSuffix", "&a成功设置玩家尾街"));
                        return true;
                    }
                    data.getSuffixes().setSuffix(suffix.toString());
                    sender.sendMessage(api.putLanguage("SetUserSuffix", "&a成功设置玩家尾街"));
                    return true;
                } else if ("reset".equalsIgnoreCase(args[2])) {
                    if (sender instanceof Player) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.user.*")) {
                                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.user.reset")) {
                                    sender.sendMessage(BukkitPermission.noPerms("functions.command.suffix.user.reset"));
                                    return true;
                                }
                            }
                        }
                    }
                    data = new Data(other.getUniqueId());
                    data.getSuffixes().setSuffix("");
                    sender.sendMessage(api.putLanguage("reSetUserSuffix", "&a成功重置玩家尾街"));
                    return true;
                } else if ("add".equalsIgnoreCase(args[2])) {
                    if (sender instanceof Player) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.user.*")) {
                                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.user.add")) {
                                    sender.sendMessage(BukkitPermission.noPerms("functions.command.suffix.user.add"));
                                    return true;
                                }
                            }
                        }
                    }
                    StringBuilder suffix = new StringBuilder();
                    String temp = "";
                    for (int i = 3; i < args.length; i++) {
                        suffix.append(args[i]).append(" ");
                    }
                    data = new Data(other.getUniqueId());
                    List<String> suffixes = data.getSuffixes().getSuffixes();
                    if (suffix.toString().startsWith("'") || suffix.toString().endsWith("'")) {
                        temp = suffix.toString().replace("'", "");
                    }
                    suffixes.add(temp);
                    data.getSuffixes().setSuffixes(suffixes);
                    sender.sendMessage(api.putLanguage("AddUserSuffix", "&a成功添加玩家尾街"));
                    return true;
                } else if ("remove".equalsIgnoreCase(args[2])) {
                    if (sender instanceof Player) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.user.*")) {
                                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.user.remove")) {
                                    sender.sendMessage(BukkitPermission.noPerms("functions.command.suffix.user.remove"));
                                    return true;
                                }
                            }
                        }
                    }
                    data = new Data(other.getUniqueId());
                    List<String> suffixes = data.getSuffixes().getSuffixes();
                    int i = 0;
                    try {
                        i = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return true;
                    }
                    if (i > suffixes.size()) {
                        return true;
                    }
                    suffixes.remove(i);
                    data.getSuffixes().setSuffixes(suffixes);
                    sender.sendMessage(api.putLanguage("removeUserSuffix", "&a成功添加玩家尾街"));
                    return true;
                }
            }
        }
        if ("group".equalsIgnoreCase(args[0])) {
            Group g = null;
            for (String s : api.getGroups()) {
                if (s.equalsIgnoreCase(args[1])) {
                    g = new Group(s);
                }
            }
            if (g==null) {
                return true;
            }
            if ("use".equalsIgnoreCase(args[2])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.group.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.group.use")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.suffix.group.use"));
                                return true;
                            }
                        }
                    }
                }
                List<String> suffixes = g.getSuffix().getSuffixes();
                int i = 0;
                try {
                    i = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return true;
                }
                if (i > suffixes.size()) {
                    return true;
                }
                data.getSuffixes().setSuffix(suffixes.get(i));
                sender.sendMessage(api.putLanguage("useGroupSuffix", "&a成功使用尾街"));
                return true;
            }
            if ("set".equalsIgnoreCase(args[2])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.group.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.group.set")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.suffix.group.set"));
                                return true;
                            }
                        }
                    }
                }
                StringBuilder suffix = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    suffix.append(args[i]).append(" ");
                }
                if (suffix.toString().startsWith("'") || suffix.toString().endsWith("'")) {
                    g.getSuffix().setSuffix(suffix.toString().replace("'", ""));
                    sender.sendMessage(api.putLanguage("SetGroupSuffix", "&a成功设置用户组尾街"));
                    return true;
                }
                g.getSuffix().setSuffix(suffix.toString());
                sender.sendMessage(api.putLanguage("SetGroupSuffix", "&a成功设置用户组尾街"));
                return true;
            } else if ("reset".equalsIgnoreCase(args[2])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.group.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.group.rest")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.suffix.group.reset"));
                                return true;
                            }
                        }
                    }
                }
                g.getSuffix().setSuffix("");
                sender.sendMessage(api.putLanguage("ResetGroupSuffix", "&a成功重置用户组尾街"));
                return true;
            } else if ("add".equalsIgnoreCase(args[2])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.group.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.group.add")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.suffix.group.add"));
                                return true;
                            }
                        }
                    }
                }
                StringBuilder suffix = new StringBuilder();
                String temp = "";
                for (int i = 3; i < args.length; i++) {
                    suffix.append(args[i]).append(" ");
                }
                List<String> suffixes = g.getSuffix().getSuffixes();
                if (suffix.toString().startsWith("'") || suffix.toString().endsWith("'")) {
                    temp = suffix.toString().replace("'", "");
                }
                suffixes.add(temp);
                g.getSuffix().setSuffixes(suffixes);
                sender.sendMessage(api.putLanguage("AddGroupSuffix", "&a成功添加用户组尾街"));
                return true;
            } else if ("remove".equalsIgnoreCase(args[2])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.group.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.group.remove")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.suffix.group.remove"));
                                return true;
                            }
                        }
                    }
                }
                List<String> suffixes = g.getSuffix().getSuffixes();
                int i = 0;
                try {
                    i = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return true;
                }
                if (i > suffixes.size()) {
                    return true;
                }
                suffixes.remove(i);
                g.getSuffix().setSuffixes(suffixes);
                sender.sendMessage(api.putLanguage("removeGroupSuffix", "&a成功移除用户组尾街尾街"));
                return true;
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.suffix.*")) {
                return null;
            }
        }
        if (args.length == 1) {
            ls.add("user");
            ls.add("group");
            Collections.sort(ls);
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
        if (args.length == 2) {
            if (args[0]==null) {
                return null;
            }
            if (args[0].equalsIgnoreCase("user")) {
                for (Player p : api.getOnlinePlayers()) {
                    ls.add(p.getName());
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
            if (args[0].equalsIgnoreCase("group")) {
                ls.addAll(api.getGroups());
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
        }
        if (args.length == 3) {
            ls.add("set");
            ls.add("add");
            ls.add("reset");
            ls.add("remove");
            Collections.sort(ls);
            return ls;
        }
        return null;
    }
}
