package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.Permissions.BukkitPermission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandGameMode implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("gamemode",new CommandGameMode());
    }
    public GameMode setGameMode(int i) {
        if (i == 0) {
            return GameMode.SURVIVAL;
        } else if (i == 1) {
            return GameMode.CREATIVE;
        } else if (i == 2) {
            return GameMode.ADVENTURE;
        } else if (i == 3) {
            return GameMode.SPECTATOR;
        }
        return null;
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (sender instanceof Player) {
                data = new Data(((Player) sender).getUniqueId());
                if ("0".equalsIgnoreCase(args[0]) || "survival".equalsIgnoreCase(args[0]) || "s".equalsIgnoreCase(args[0])) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.survival.*")) {
                                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.survival.other")) {
                                    sender.sendMessage(BukkitPermission.noPerms("functions.command.gamemode.survival.other"));
                                    return true;
                                }
                            }
                        }
                    if (data.getPlayer().getGameMode()==GameMode.SURVIVAL) {
                        return true;
                    }
                    data.getPlayer().setGameMode(setGameMode(0));
                    sender.sendMessage(api.putLanguage("SetGameMode-Survival","&a成功设置 %target% 为生存模式！").replace("%target%",sender.getName()).replace("%player%",sender.getName()));
                    return true;
                }
                if ("1".equalsIgnoreCase(args[0]) || "creative".equalsIgnoreCase(args[0]) || "c".equalsIgnoreCase(args[0])) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.creator.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.creator.other")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.gamemode.creator.other"));
                                return true;
                            }
                        }
                    }
                    if (data.getPlayer().getGameMode()==GameMode.CREATIVE) {
                        return true;
                    }
                    data.getPlayer().setGameMode(setGameMode(1));
                    sender.sendMessage(api.putLanguage("SetGameMode-Creative","&a成功设置 %target% 为创造模式！").replace("%target%",sender.getName()).replace("%player%",sender.getName()));
                    return true;
                }
                if ("2".equalsIgnoreCase(args[0]) || "adventure".equalsIgnoreCase(args[0]) || "a".equalsIgnoreCase(args[0])) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.adventure.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.adventure.other")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.gamemode.adventure.other"));
                                return true;
                            }
                        }
                    }
                    if (data.getPlayer().getGameMode()==GameMode.ADVENTURE) {
                        return true;
                    }
                    data.getPlayer().setGameMode(setGameMode(2));
                    sender.sendMessage(api.putLanguage("SetGameMode-Adventure","&a成功设置 %target% 为冒险模式！").replace("%target%",sender.getName()).replace("%player%",sender.getName()));
                    return true;
                }
                if ("3".equalsIgnoreCase(args[0]) || "spectator".equalsIgnoreCase(args[0]) || "sp".equalsIgnoreCase(args[0])) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.spectator.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.spectator.other")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.gamemode.spectator.other"));
                                return true;
                            }
                        }
                    }
                    if (data.getPlayer().getGameMode()==GameMode.SPECTATOR) {
                        return true;
                    }
                    data.getPlayer().setGameMode(setGameMode(3));
                    sender.sendMessage(api.putLanguage("SetGameMode-Spectator","&a成功设置 %target% 为旁观者模式！").replace("%target%",sender.getName()).replace("%player%",sender.getName()));
                    return true;
                }


            }
        }
        if (args.length == 2) {
            Player p = Bukkit.getPlayer(args[0]);
            data = new Data(p.getUniqueId());
            if ("0".equalsIgnoreCase(args[0]) || "survival".equalsIgnoreCase(args[0]) || "s".equalsIgnoreCase(args[0])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.survival.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.survival.target")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.gamemode.survival.target"));
                                return true;
                            }
                        }
                    }
                }
                if (data.getPlayer().getGameMode()==GameMode.SURVIVAL) {
                    return true;
                }
                data.getPlayer().setGameMode(setGameMode(0));
                sender.sendMessage(api.putLanguage("SetGameMode-Survival","&a成功设置 %target% 为生存模式！").replace("%target%",p.getName()).replace("%player%",sender.getName()));
                return true;
            }
            if ("1".equalsIgnoreCase(args[0]) || "creative".equalsIgnoreCase(args[0]) || "c".equalsIgnoreCase(args[0])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.creative.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.creative.target")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.gamemode.creative.target"));
                                return true;
                            }
                        }
                    }
                }
                if (data.getPlayer().getGameMode()==GameMode.CREATIVE) {
                    return true;
                }
                data.getPlayer().setGameMode(setGameMode(1));
                sender.sendMessage(api.putLanguage("SetGameMode-Creative","&a成功设置 %target% 为创造模式！").replace("%target%",p.getName()).replace("%player%",sender.getName()));
                return true;
            }
            if ("2".equalsIgnoreCase(args[0]) || "adventure".equalsIgnoreCase(args[0]) || "a".equalsIgnoreCase(args[0])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.adventure.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.adventure.target")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.gamemode.adventure.target"));
                                return true;
                            }
                        }
                    }
                }
                if (data.getPlayer().getGameMode()==GameMode.ADVENTURE) {
                    return true;
                }
                data.getPlayer().setGameMode(setGameMode(2));
                sender.sendMessage(api.putLanguage("SetGameMode-Adventure","&a成功设置 %target% 为冒险模式！").replace("%target%",p.getName()).replace("%player%",sender.getName()));
                return true;
            }
            if ("3".equalsIgnoreCase(args[0]) || "spectator".equalsIgnoreCase(args[0]) || "sp".equalsIgnoreCase(args[0])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.spectator.*")) {
                            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.spectator.target")) {
                                sender.sendMessage(BukkitPermission.noPerms("functions.command.gamemode.spectator.target"));
                                return true;
                            }
                        }
                    }
                }
                if (data.getPlayer().getGameMode()==GameMode.SPECTATOR) {
                    return true;
                }
                data.getPlayer().setGameMode(setGameMode(3));
                sender.sendMessage(api.putLanguage("SetGameMode-Spectator","&a成功设置 %target% 为旁观者模式！").replace("%target%",p.getName()).replace("%player%",sender.getName()));
                return true;
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.gamemode.*")) {
                return ls;
            }
        }
        ls.add("survival");
        ls.add("creator");
        ls.add("adventure");
        ls.add("spectator");
        Collections.sort(ls);
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
        List<String> players = new ArrayList<>();
        if (args.length == 2) {
            for (Player p : api.getOnlinePlayers()) {
                players.add(p.getName());
            }
            if (args[1]==null) {
                return players;
            }
            for (String s : players) {
                if (s.startsWith(args[1])) {
                    temp.add(s);
                }
            }
            return temp;
        }
        return null;
    }
}
