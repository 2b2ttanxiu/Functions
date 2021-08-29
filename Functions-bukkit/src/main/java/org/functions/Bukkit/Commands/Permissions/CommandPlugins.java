package org.functions.Bukkit.Commands.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.Permissions.BukkitPermission;

import java.util.List;

public class CommandPlugins implements TabExecutor {
    Functions a = Functions.instance;
    API api = a.getAPI();
    PluginManager pm;
    Plugin[] plugins = pm.getPlugins();
    Data data;
    public static void run() {
        Functions.instance.getAPI().getCommand("plugins",new CommandPlugins());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!BukkitPermission.has(((Player) sender).getUniqueId(),"functions.command.plugins")) {
                sender.sendMessage(BukkitPermission.noPerms("functions.command.plugins"));
                return true;
            }
        }
        if (args.length == 0) {
            int i = 0;
            StringBuilder list = new StringBuilder();
            String Plugin = a.getSettings().getString("Plugins");
            String Description;
            String IsEnabled;
            for(Plugin p : plugins) {
                Description = p.getDescription().getName();
                IsEnabled = api.TrueOrFalseString(Description, p.isEnabled());
                list.append(Plugin.replace("%plugin%", IsEnabled).replace("%count%", i + ""));
            }
            sender.sendMessage(api.putLanguage("ListPlugins", "插件： (%count%): %plugins%").replace("%plugins%", list.toString()).replace("%count%", i + ""));
            return true;
        }
        String Name;
        String Version;
        List<String> author;
        String main;
        String Description = "";
        String IsEnabled;
        if (args.length == 1) {
            for (Plugin p : plugins) {
                if (p.getDescription().getName().equalsIgnoreCase(args[0])) {
                    Name = p.getDescription().getName();
                    Version = p.getDescription().getVersion();
                    author = p.getDescription().getAuthors();
                    if (p.getDescription().getDescription() != null) {
                        Description = p.getDescription().getDescription();
                    }

                    IsEnabled = api.TrueOrFalse(p.isEnabled());
                    main = p.getDescription().getMain();
                    sender.sendMessage(api.putLanguage("InfoPlugin", "插件名字: %name%\n插件版本: %version%\n插件作者: %author%\n插件描述信息: %description%\nThe plugin main: %main%\n插件是否开启: %enabled%").replace("%name%", Name).replace("%version%", Version).replace("%author%", author.toString().replace("[", "").replace("]", "")).replace("%description%", Description).replace("%main%", main).replace("%enabled%", IsEnabled));
                    return true;
                }
            }
            sender.sendMessage(api.putLanguage("NotFoundPlugin","&c插件是否装载？"));
            return true;
        }
        if (args.length == 2) {
            for (Plugin p : plugins) {
                if (p.getDescription().getName().equalsIgnoreCase(args[0])) {
                    Name = p.getDescription().getName();
                    Version = p.getDescription().getVersion();
                    author = p.getDescription().getAuthors();
                    Description = "";
                    if (p.getDescription().getDescription() != null) {
                        Description = p.getDescription().getDescription();
                    }

                    IsEnabled = api.TrueOrFalse(p.isEnabled());
                    main = p.getDescription().getMain();
                    if ("reload".equalsIgnoreCase(args[1])) {
                    }
                    if ("name".equalsIgnoreCase(args[1])) {
                        sender.sendMessage(api.putLanguage("InfoPluginName", "插件名字: {0}").replace("{0}", Name));
                        return true;
                    }

                    if ("version".equalsIgnoreCase(args[1])) {
                        sender.sendMessage(api.putLanguage("InfoPluginVersion", "插件版本: {0}").replace("{0}", Version));
                        return true;
                    }

                    if ("author".equalsIgnoreCase(args[1])) {
                        sender.sendMessage(api.putLanguage("InfoPluginAuthor", "插件作者: {0}").replace("{0}", author.toString().replace("[", "").replace("]", "")));
                        return true;
                    }

                    if ("description".equalsIgnoreCase(args[1])) {
                        sender.sendMessage(api.putLanguage("InfoPluginDescription", "插件信息: {0}").replace("{0}", Description));
                        return true;
                    }

                    if ("enabled".equalsIgnoreCase(args[1])) {
                        sender.sendMessage(api.putLanguage("InfoPluginEnabled", "插件启用: {0}").replace("{0}", IsEnabled));
                        return true;
                    }

                    if ("main".equalsIgnoreCase(args[1])) {
                        sender.sendMessage(api.putLanguage("InfoPluginMain", "插件加载路径: {0}").replace("{0}", main));
                        return true;
                    }

                    if ("all".equalsIgnoreCase(args[1])) {
                        sender.sendMessage(api.putLanguage("InfoPlugin", "插件名字: %name%\n插件版本: %version%\n插件作者: %author%\n插件描述信息: %description%\nThe plugin main: %main%\n插件是否开启: %enabled%").replace("%name%", Name).replace("%version%", Version).replace("%author%", author.toString().replace("[", "").replace("]", "")).replace("%description%", Description).replace("%main%", main).replace("%enabled%", IsEnabled));
                        return true;
                    }

                    sender.sendMessage(api.putLanguage("InfoPlugin", "插件名字: %name%\n插件版本: %version%\n插件作者: %author%\n插件描述信息: %description%\nThe plugin main: %main%\n插件是否开启: %enabled%").replace("%name%", Name).replace("%version%", Version).replace("%author%", author.toString().replace("[", "").replace("]", "")).replace("%description%", Description).replace("%main%", main).replace("%enabled%", IsEnabled));
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
