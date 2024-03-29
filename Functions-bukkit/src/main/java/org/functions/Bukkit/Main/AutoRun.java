package org.functions.Bukkit.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.functions.Bukkit.Events.PlayerSendTabListEvent;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.Animation.Animations;
import org.functions.Bukkit.api.Permissions.BukkitPermission;
import org.functions.Bukkit.api.ScoreBoard;
import org.functions.Bukkit.api.Tab;
import org.functions.Bukkit.runTask.ServerTitleRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AutoRun implements Runnable{
    public void run() {
        new ServerTitleRunnable().run();
        BukkitPermission.run();
        new Animations().run();
        boolean tab_enable = false;
        FileConfiguration set = Functions.instance.getSettings();
        //Functions.instance.getAPI().sendActionBar(set.getString("ActionBar"));
//        tab_enable = set.getBoolean("Tab.Enable");
//        String path = "Tab.";
//        String header = null;
//        String footer = null;
//        if (!set.getString(path + "Header").equals("none") && !set.getString(path + "Header").equals("")) {
//            for(int i = 0; i < set.getStringList(path + "Header").size(); ++i) {
//                if (i == set.getStringList(path + "Header").size() - 1) {
//                    header = header + set.getStringList(path + "Header").get(i);
//                } else {
//                    header = header + set.getStringList(path + "Header").get(i) + "\n";
//                }
//            }
//        } else {
//            header = "";
//        }
//        if (!set.getString(path + "Footer").equals("none") && !set.getString(path + "Footer").equals("")) {
//            for(int i = 0; i < set.getStringList(path + "Footer").size(); ++i) {
//                if (i == set.getStringList(path + "Footer").size() - 1) {
//                    footer = footer + set.getStringList(path + "Footer").get(i);
//                } else {
//                    footer = footer + set.getStringList(path + "Footer").get(i) + "\n";
//                }
//            }
//        } else {
//            footer = "";
//        }
//        String list = null;
//        if (!set.getString(path + "List").equals("none") && !set.getString(path + "List").equals("")) {
//            list = set.getString(path + "List");
//
//        }
//        if (tab_enable) {
//            PlayerSendTabListEvent event = new PlayerSendTabListEvent(Functions.instance.getAPI().getOnlinePlayers(), header, footer, list);
//            event.schedule(event);
//        }
        API api = Functions.instance.getAPI();
        List<String> names = Functions.instance.getOP().getStringList("Operators");
        List<String> pnames = new ArrayList<>();
        // get all player
        Data data;
        boolean is = false;
        for (Player p : api.getOnlinePlayers()) {
            data = new Data(p.getUniqueId());
            if (data.hasOperator()) {
                if (names.size() == 0) {
                    is = false;
                }
                for (String s : names) {
                    if (!s.equals(p.getName())) {
                        is = false;
                    }
                }
            }
            if (!data.hasOperator()) {
                if (names.size() == 0) {
                    is = false;
                }
                for (String s : names) {
                    if (s.equals(p.getName())) {
                        is = true;
                    }
                }
            }
            data.setOperator(is);
            p.setOp(data.hasOperator());
            data.getReport().check();
        }

    }
}
