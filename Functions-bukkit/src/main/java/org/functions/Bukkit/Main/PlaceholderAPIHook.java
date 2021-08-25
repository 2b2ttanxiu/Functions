package org.functions.Bukkit.Main;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderAPIHook extends PlaceholderExpansion {
    public String getIdentifier() {
        return "functions";
    }
    public String getAuthor() {
        return Functions.instance.getDescription().getAuthors().get(0);
    }
    public String getVersion() {
        return Functions.instance.getDescription().getVersion();
    }
    public String onPlaceholderRequest(Player player, String params) {
        return null;
    }
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("server_tps")) {
            return Functions.instance.getAPI().tps();
        }
        if (params.equalsIgnoreCase("tps")) {
            return Functions.instance.getAPI().getTPS();
        }
		if (params.equalsIgnoreCase("server_name")) {
			return Functions.instance.getConfig().getString("Server-Name");
		}
		if (params.equalsIgnoreCase("version")) {
			return Functions.instance.getDescription().getVersion();
		}
		if (params.equalsIgnoreCase("new_version")) {
		    return Download.getVersion();
        }
        return "Hello fuck you!:D";
    }
}
