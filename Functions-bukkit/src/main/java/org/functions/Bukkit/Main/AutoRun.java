package org.functions.Bukkit.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Events.PlayerSendTabListEvent;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.Tab;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AutoRun implements Runnable{
    public void run() {
        Functions.instance.getAPI().getBukkitPermissions().collectPermissions();
        Functions.instance.getAPI().getBukkitPermissions().updateAllPlayers();
        boolean tab_enable = false;
        FileConfiguration set = Functions.instance.getSettings();
        tab_enable = set.getBoolean("Tab.Enable");
        String path = "Tab.";
        String header = null;
        String footer = null;
        if (!set.getString(path + "Header").equals("none") && !set.getString(path + "Header").equals("")) {
            for(int i = 0; i < set.getStringList(path + "Header").size(); ++i) {
                if (i == set.getStringList(path + "Header").size() - 1) {
                    header = header + set.getStringList(path + "Header").get(i);
                } else {
                    header = header + set.getStringList(path + "Header").get(i) + "\n";
                }
            }
        } else {
            header = "";
        }
        if (!set.getString(path + "Footer").equals("none") && !set.getString(path + "Footer").equals("")) {
            for(int i = 0; i < set.getStringList(path + "Footer").size(); ++i) {
                if (i == set.getStringList(path + "Footer").size() - 1) {
                    footer = footer + set.getStringList(path + "Footer").get(i);
                } else {
                    footer = footer + set.getStringList(path + "Footer").get(i) + "\n";
                }
            }
        } else {
            footer = "";
        }
        String list = null;
        if (!set.getString(path + "List").equals("none") && !set.getString(path + "List").equals("")) {
            list = set.getString(path + "List");

        }
        if (tab_enable) {
            PlayerSendTabListEvent event = new PlayerSendTabListEvent(Functions.instance.getAPI().getOnlinePlayers(), header, footer, list);
            event.schedule(event);
        }
        API api = Functions.instance.getAPI();
        List<String> names = Functions.instance.getOP().getStringList("Operators");
        List<String> pnames = new ArrayList<>();
        // get all player
        Data data;
        for (Player p : api.getOnlinePlayers()) {
            data = new Data(p.getUniqueId());
            if (p.isOp()) {
                if (names.size() == 0) {
                    data.setOperator(false);
                }
                for (String s : names) {
                    if (!s.equals(p.getName())) {
                        data.setOperator(false);
                    }
                }
            }
            if (!p.isOp()) {
                for (String s : names) {
                    if (s.equals(p.getName())) {
                        data.setOperator(true);
                    }
                }
            }

        }

    }
}
