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

public class CommandBack implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("back",new CommandBack());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(api.putLanguage("NoPlayer","&c这条指令只能由玩家来执行"));
            return true;
        }
        Player player = (Player)sender;
        data = new Data(player.getUniqueId());
        if (!BukkitPermission.has(data.getUUID(),"functions.command.back")) {
            sender.sendMessage(BukkitPermission.noPerms("functions.command.back"));
            return true;
        }
        if (!data.getBacks().exists()) {
            sender.sendMessage(api.putLanguage("BackNoEmpty","&c你没有死亡记录点！"));
            return true;
        }
        data.getBacks().teleport();
        sender.sendMessage(api.putLanguage("TeleportBack","&c成功回到死亡记录点 %position%").replace("%position%",data.getBacks().toLocation()).replace("%player%",sender.getName()));
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
