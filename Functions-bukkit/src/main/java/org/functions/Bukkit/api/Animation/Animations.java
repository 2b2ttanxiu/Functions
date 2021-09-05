package org.functions.Bukkit.api.Animation;

import org.bukkit.configuration.file.FileConfiguration;
import org.functions.Bukkit.Main.Functions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animations implements Runnable{
    private Functions a = Functions.instance;
    String s;
    FileConfiguration set;
    public Animations(String animation) {
        this.s = animation;
    }
    public void run() {
        List<String> ls = new ArrayList<>();
        if (a.getAnimations().size()==0) {
            return;
        }
        if (a.getAPI().getOnlinePlayers().size()==0) {
            Functions.instance.animation_hashmap.clear();
        }
            int i = 0;
            if (Functions.instance.animation_hashmap.containsKey(s)) {
                i = Functions.instance.animation_hashmap.get(s);
            } else {
                Functions.instance.animation_hashmap.put(s, 0);
                i = Functions.instance.animation_hashmap.get(s);
            }
            Functions.instance.animation_hashmap.remove(s);
            set = a.getAnimation(s);
            ls = set.getStringList("line");
            if (set.getBoolean("random")) {
                if (ls.size()==0) return;
                Random random = new Random();
                    Functions.instance.animation_hashmap.remove(s);
                    Functions.instance.animation_hashmap.put(s, random.nextInt(ls.size()));
                    return;
            }
            if (ls.size() != 0) {
                if (ls.size() != i) {
                    ++i;
                }
                if (ls.size() == i) {
                    i = 0;
                }
            }
            Functions.instance.animation_hashmap.remove(s);
            Functions.instance.animation_hashmap.put(s,i);
    }
    public static String getString(String animation) {
        Functions.instance.onAnimation(animation);
        FileConfiguration set = Functions.instance.getAnimation(animation);
        if (Functions.instance.animation_hashmap.containsKey(animation)) {
            return set.getStringList("line").get(Functions.instance.animation_hashmap.get(animation));
        }
        return "no animation run it?";
    }
}
