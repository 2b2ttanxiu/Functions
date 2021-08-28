package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.Permissions.BukkitPermission;

import java.util.List;

public class CommandSuicide implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("suicide",new CommandSuicide());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(api.putLanguage("NoPlayer","&c这条指令只能由玩家来执行"));
            return true;
        }
        Player player = (Player)sender;
        data = new Data(player.getUniqueId());
        if (!BukkitPermission.has(data.getUUID(),"functions.command.suicide")) {
            sender.sendMessage(BukkitPermission.noPerms("functions.command.suicide"));
            return true;
        }
        data.kill();
        sender.sendMessage(api.putLanguage("Suicide","&a成功自杀！"));
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
