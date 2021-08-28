package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.Economy.YamlEconomy;
import org.functions.Bukkit.api.Permissions.BukkitPermission;

import java.util.*;

public class CommandEconomy implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public String replace(Player player,String msg) {
        data = new Data(player.getUniqueId());
        return msg.replace("%account_economy%",data.getEconomy().getBalance(data.getUUID()).getBalance()+"");
    }
    YamlEconomy yaml = new YamlEconomy();
    public static void run() {
        Functions.instance.getAPI().getCommand("economy",new CommandEconomy());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return true;
        }
        if (args.length == 3) {
            OfflinePlayer other = Bukkit.getOfflinePlayer(args[1]);
            if (other == null) {
                sender.sendMessage(api.putLanguage("EconomyIsHasAccount","&c这名玩家是否有了经济账号？"));
                return true;
            } else if (!yaml.hasAccount(other.getUniqueId())){
                sender.sendMessage(api.putLanguage("EconomyIsHasAccount","&c这名玩家是否有了经济账号？"));
                return true;
            }
            data = new Data(other.getUniqueId());
            double count = 0.0D;
            try {
                count = a.getAmountFromString(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(api.putLanguage("FormatEconomyNumber","&c数字格式化失败，是否写错？"));
                e.printStackTrace();
                return true;
            }
            if (count <= 0.0D) {
                sender.sendMessage(api.putLanguage("NumberError","&c数字有错误！"));
                return true;
            }
            if ("take".equalsIgnoreCase(args[0])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.economy.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.economy.take")) {
                            sender.sendMessage(BukkitPermission.noPerms("functions.command.economy.take"));
                            return true;
                        }
                    }
                }
                if (data.getEconomy().hasAccount(data.getUUID())) {
                    if (!data.getEconomy().has(data.getUUID(),count)) {
                        sender.sendMessage(api.putLanguage("IsEconomy","&c%target% 的经济不足").replace("%target%",other.getName()).replace("%player%",sender.getName()).replace("%economy%",count+"").replace("%account_economy%",data.getEconomy().getBalance(data.getUUID()).getBalance()+""));
                        return true;
                    }
                    data.getEconomy().withdraw(data.getUUID(),count);
                    sender.sendMessage(api.putLanguage("takeEconomy","&a成功拿走 %target% 的 %economy%").replace("%target%",other.getName()).replace("%player%",sender.getName()).replace("%economy%",count+"").replace("%account_economy%",data.getEconomy().getBalance(data.getUUID()).getBalance()+""));
                    return true;
                }
                sender.sendMessage(api.putLanguage("EconomyIsHasAccount","&c这名玩家是否有了经济账号？"));
                return true;
            }
            if ("give".equalsIgnoreCase(args[0])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.economy.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.economy.give")) {
                            sender.sendMessage(BukkitPermission.noPerms("functions.command.economy.give"));
                            return true;
                        }
                    }
                }
                if (data.getEconomy().hasAccount(data.getUUID())) {
                    data.getEconomy().deposit(data.getUUID(), count);
                    sender.sendMessage(api.putLanguage("giveEconomy", "&a成功给予 %target% 的经济").replace("%target%", other.getName()).replace("%player%", sender.getName()).replace("%economy%", count + "").replace("%account_economy%", data.getEconomy().getBalance(data.getUUID()).getBalance() + ""));
                    return true;
                }
                sender.sendMessage(api.putLanguage("EconomyIsHasAccount","&c这名玩家是否有了经济账号？"));
                return true;
            }
            if ("set".equalsIgnoreCase(args[0])) {
                if (sender instanceof Player) {
                    if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.economy.*")) {
                        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.economy.set")) {
                            sender.sendMessage(BukkitPermission.noPerms("functions.command.economy.set"));
                            return true;
                        }
                    }
                }
                if (data.getEconomy().hasAccount(data.getUUID())) {
                    data.getEconomy().set(data.getUUID(), count);
                    sender.sendMessage(api.putLanguage("setEconomy", "&a成功设置 %target% 的经济").replace("%target%", other.getName()).replace("%player%", sender.getName()).replace("%economy%", count + "").replace("%account_economy%", data.getEconomy().getBalance(data.getUUID()).getBalance() + ""));
                    return true;
                }
                sender.sendMessage(api.putLanguage("EconomyIsHasAccount","&c这名玩家是否有了经济账号？"));
                return true;
            }
        }
        if (args.length == 4) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(api.putLanguage("NoPlayer","&c这条指令只能由玩家来执行"));
                return true;
            }
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.economy.*")) {
                if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.economy.remove")) {
                    sender.sendMessage(BukkitPermission.noPerms("functions.command.economy.remove"));
                    return true;
                }
            }
            if ("pay".equalsIgnoreCase(args[0])) {
                OfflinePlayer other = Bukkit.getOfflinePlayer(args[1]);
                if (other == null) {
                    sender.sendMessage(api.putLanguage("EconomyIsHasAccount","&c这名玩家是否有了经济账号？"));
                    return true;
                } else if (!yaml.hasAccount(other.getUniqueId())){
                    sender.sendMessage(api.putLanguage("EconomyIsHasAccount","&c这名玩家是否有了经济账号？"));
                    return true;
                }
                Player p = (Player)sender;
                if (!yaml.hasAccount(p.getUniqueId())) {
                    sender.sendMessage(api.putLanguage("EconomyIsHasAccount","&c这名玩家是否有了经济账号？"));
                    return true;
                }
                data = new Data(p.getUniqueId());
                Data target = new Data(other.getUniqueId());
                //.replace("%target%",other.getName()).replace("%player%",sender.getName()).replace("%economy%",count+"").replace("%account_economy%",data.getEconomy().getBalance(data.getUUID()).getBalance()+"")
                double count = 0.0D;
                try {
                    count = a.getAmountFromString(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(api.putLanguage("FormatEconomyNumber","&c数字格式化失败，是否写错？"));
                    e.printStackTrace();
                    return true;
                }
                if (count <= 0.0D) {
                    sender.sendMessage(api.putLanguage("NumberError","&c数字有错误！"));
                    return true;
                }
                data.getEconomy().withdraw(data.getUUID(),count);
                sender.sendMessage(api.putLanguage("me-payEconomy","&a成功转账给 %target% 的 %economy%").replace("%target%",other.getName()).replace("%player%",sender.getName()).replace("%economy%",count+"").replace("%account_economy%",data.getEconomy().getBalance(data.getUUID()).getBalance()+""));
                target.getEconomy().deposit(target.getUUID(),count);
                if (other.isOnline()) {
                    other.getPlayer().sendMessage(api.putLanguage("other-copyEconomy","").replace("%target%",other.getName()).replace("%player%",sender.getName()).replace("%economy%",count+"").replace("%account_economy%",target.getEconomy().getBalance(target.getUUID()).getBalance()+""));
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.economy.*")) {
                return ls;
            }
        }
        List<String> temp = new ArrayList<>();
        ls.add("give");
        ls.add("take");
        ls.add("pay");
        ls.add("set");
        Collections.sort(ls);
        if (args.length == 1) {
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
            ls.clear();
            List<String> players = new ArrayList<>();
            for (String uid : YamlEconomy.getPlayersUUID()) {
                players.add(Bukkit.getOfflinePlayer(UUID.fromString(uid)).getName());
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
