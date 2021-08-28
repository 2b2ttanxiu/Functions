package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.Permissions.BukkitPermission;

import java.util.List;

public class CommandHat implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("hat",new CommandHat());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(api.putLanguage("NoPlayer","&c这条指令只能由玩家来执行"));
            return true;
        }
        if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.hat")) {
            sender.sendMessage(BukkitPermission.noPerms("functions.command.hat"));
            return true;
        }
        Player p = (Player)sender;
        ItemStack hand = p.getInventory().getItemInMainHand();
        ItemStack head = p.getInventory().getHelmet();
        api.onChangePlayerHat(p.getInventory(),hand,head);
        sender.sendMessage(api.putLanguage("ChangeHat","&a成功切换！").replace("%player%",sender.getName()));
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
