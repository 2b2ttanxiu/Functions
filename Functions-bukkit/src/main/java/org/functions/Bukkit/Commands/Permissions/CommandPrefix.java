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

public class CommandPrefix implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("prefix",new CommandPrefix());
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
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.*")) {
                                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.user.*")) {
                                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.user.use")) {
                                        sender.sendMessage(BukkitPermission.noPerms("functions.command.prefix.user.use"));
                                        return true;
                                    }
                                }
                            }
                        }
                        data = new Data(other.getUniqueId());
                        List<String> prefixes = data.getPrefixes().getPrefixes();
                        int i = 0;
                        try {
                            i = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            return true;
                        }
                        if (i > prefixes.size()) {
                            return true;
                        }
                        data.getPrefixes().setPrefix(prefixes.get(i));
                        sender.sendMessage(api.putLanguage("useUserPrefix", "&a成功使用头街"));
                        return true;
                    }
                    if ("set".equalsIgnoreCase(args[2])) {
                        if (sender instanceof Player) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.*")) {
                                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.user.*")) {
                                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.user.set")) {
                                        sender.sendMessage(BukkitPermission.noPerms("functions.command.prefix.user.set"));
                                        return true;
                                    }
                                }
                            }
                        }
                        StringBuilder prefix = new StringBuilder();
                        for (int i = 3; i < args.length; i++) {
                            prefix.append(args[i]).append(" ");
                        }
                        data = new Data(other.getUniqueId());
                        if (prefix.toString().startsWith("'") || prefix.toString().endsWith("'")) {
                            data.getPrefixes().setPrefix(prefix.toString().replace("'", ""));
                            sender.sendMessage(api.putLanguage("SetUserPrefix", "&a成功设置玩家头街"));
                            return true;
                        }
                        data.getPrefixes().setPrefix(prefix.toString());
                        sender.sendMessage(api.putLanguage("SetUserPrefix", "&a成功设置玩家头街"));
                        return true;
                    } else if ("reset".equalsIgnoreCase(args[2])) {
                        if (sender instanceof Player) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.*")) {
                                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.user.*")) {
                                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.user.reset")) {
                                        sender.sendMessage(BukkitPermission.noPerms("functions.command.prefix.user.reset"));
                                        return true;
                                    }
                                }
                            }
                        }
                        data = new Data(other.getUniqueId());
                        data.getPrefixes().setPrefix("");
                        sender.sendMessage(api.putLanguage("reSetUserPrefix", "&a成功重置玩家头街"));
                        return true;
                    } else if ("add".equalsIgnoreCase(args[2])) {
                        if (sender instanceof Player) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.*")) {
                                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.user.*")) {
                                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.user.add")) {
                                        sender.sendMessage(BukkitPermission.noPerms("functions.command.prefix.user.add"));
                                        return true;
                                    }
                                }
                            }
                        }
                        StringBuilder prefix = new StringBuilder();
                        String temp = "";
                        for (int i = 3; i < args.length; i++) {
                            prefix.append(args[i]).append(" ");
                        }
                        data = new Data(other.getUniqueId());
                        List<String> prefixes = data.getPrefixes().getPrefixes();
                        if (prefix.toString().startsWith("'") || prefix.toString().endsWith("'")) {
                            temp = prefix.toString().replace("'", "");
                        }
                        prefixes.add(temp);
                        data.getPrefixes().setPrefixes(prefixes);
                        sender.sendMessage(api.putLanguage("AddUserPrefix", "&a成功添加玩家头街"));
                        return true;
                    } else if ("remove".equalsIgnoreCase(args[2])) {
                        if (sender instanceof Player) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.*")) {
                                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.user.*")) {
                                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.user.remove")) {
                                        sender.sendMessage(BukkitPermission.noPerms("functions.command.prefix.user.remove"));
                                        return true;
                                    }
                                }
                            }
                        }
                        data = new Data(other.getUniqueId());
                        List<String> prefixes = data.getPrefixes().getPrefixes();
                        int i = 0;
                        try {
                            i = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            return true;
                        }
                        if (i > prefixes.size()) {
                            return true;
                        }
                        prefixes.remove(i);
                        data.getPrefixes().setPrefixes(prefixes);
                        sender.sendMessage(api.putLanguage("removeUserPrefix", "&a成功移除玩家头街"));
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
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.group.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.group.use")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.prefix.group.use"));
                                return true;
                            }
                        }
                    }
                }
                List<String> prefixes = g.getPrefix().getPrefixes();
                int i = 0;
                try {
                    i = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return true;
                }
                if (i > prefixes.size()) {
                    return true;
                }
                data.getPrefixes().setPrefix(prefixes.get(i));
                sender.sendMessage(api.putLanguage("useGroupPrefix", "&a成功使用头街"));
                return true;
            }
            if ("set".equalsIgnoreCase(args[2])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.group.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.group.set")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.prefix.group.set"));
                                return true;
                            }
                        }
                    }
                }
                StringBuilder prefix = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    prefix.append(args[i]).append(" ");
                }
                if (prefix.toString().startsWith("'") || prefix.toString().endsWith("'")) {
                    g.getPrefix().setPrefix(prefix.toString().replace("'", ""));
                    sender.sendMessage(api.putLanguage("SetGroupPrefix", "&a成功设置用户组头街"));
                    return true;
                }
                g.getPrefix().setPrefix(prefix.toString());
                sender.sendMessage(api.putLanguage("SetGroupPrefix", "&a成功设置用户组头街"));
                return true;
            } else if ("reset".equalsIgnoreCase(args[2])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.group.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.group.rest")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.prefix.group.reset"));
                                return true;
                            }
                        }
                    }
                }
                g.getPrefix().setPrefix("");
                sender.sendMessage(api.putLanguage("ResetGroupPrefix", "&a成功重置用户组头街"));
                return true;
            } else if ("add".equalsIgnoreCase(args[2])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.group.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.group.add")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.prefix.group.add"));
                                return true;
                            }
                        }
                    }
                }
                StringBuilder prefix = new StringBuilder();
                String temp = "";
                for (int i = 3; i < args.length; i++) {
                    prefix.append(args[i]).append(" ");
                }
                List<String> prefixes = g.getPrefix().getPrefixes();
                if (prefix.toString().startsWith("'") || prefix.toString().endsWith("'")) {
                    temp = prefix.toString().replace("'", "");
                }
                prefixes.add(temp);
                g.getPrefix().setPrefixes(prefixes);
                sender.sendMessage(api.putLanguage("AddGroupPrefix", "&a成功添加用户组头街"));
                return true;
            } else if ("remove".equalsIgnoreCase(args[2])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.group.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.group.remove")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.prefix.group.remove"));
                                return true;
                            }
                        }
                    }
                }
                List<String> prefixes = g.getPrefix().getPrefixes();
                int i = 0;
                try {
                    i = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return true;
                }
                if (i > prefixes.size()) {
                    return true;
                }
                prefixes.remove(i);
                g.getPrefix().setPrefixes(prefixes);
                sender.sendMessage(api.putLanguage("removeGroupPrefix", "&a成功移除用户组头街"));
                return true;
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.prefix.*")) {
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
