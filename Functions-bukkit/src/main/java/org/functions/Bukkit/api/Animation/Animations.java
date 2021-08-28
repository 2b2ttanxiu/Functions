package org.functions.Bukkit.api.Animation;

import org.bukkit.configuration.file.FileConfiguration;
import org.functions.Bukkit.Main.Functions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animations {
    private Functions a = Functions.instance;
    String x;
    FileConfiguration set;
    public void run() {
        List<String> ls = new ArrayList<>();
        if (a.getAnimations().size()==0) {
            return;
        }
        for (String s : a.getAnimations()) {
            set = a.getAnimation(s);
            if (set.getBoolean("random")) {
                Random random = new Random();
                set.set("count",random.nextInt(ls.size()+1));
                return;
            }
            int i = set.getInt("count",0);
            ls = set.getStringList("line");
            if (ls.size() != 1) {
                if (ls.size() != i) {
                    ++i;
                }

                if (ls.size() == i) {
                    i = 0;
                }
            }
            set.set("count",i);
            try {
                set.save(new File(a.Animation_dir,s+".yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String getString(String animation) {
        Functions.instance.onAnimation(animation);
        FileConfiguration set = Functions.instance.getAnimation(animation);
        return set.getStringList("line").get(set.getInt("count"));
    }
}
