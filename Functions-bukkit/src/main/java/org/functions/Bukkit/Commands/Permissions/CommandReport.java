package org.functions.Bukkit.Commands.Permissions;

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

public class CommandReport implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("report",new CommandReport());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(api.putLanguage("NoPlayer","&c这条指令只能由玩家来执行"));
            return true;
        }
        Player p = (Player)sender;
        data = new Data(p.getUniqueId());
        if (!BukkitPermission.has(data.getUUID(),"functions.command.report")) {
            sender.sendMessage(BukkitPermission.noPerms("functions.command.report"));
                    return true;
        }
        if (args.length == 1) {
            Player target = a.getServer().getPlayer(args[0]);
            data = new Data(target.getUniqueId());
            data.getReport().report(p.getName());
            sender.sendMessage(api.putLanguage("Report","&a谢谢你的举报！").replace("%player%",p.getName()).replace("%target%",target.getName()));
            return true;
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ls = new ArrayList<>();
        for (Player p : api.getOnlinePlayers()) {
            ls.add(p.getName());
        }
        Collections.sort(ls);
        return ls;
    }
}
