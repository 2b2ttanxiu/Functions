package org.functions.Bukkit.api.Animation;

import org.functions.Bukkit.Main.Functions;

import java.util.List;

public class Animations implements Runnable {
    private Functions a = Functions.instance;
    String x;

    public Animations(String Animations) {
        this.x = Animations;
    }

    public void run() {
        List<String> ls = this.p.getSettings().getStringList("Animations." + this.x + ".Texts");
        int i = this.p.getSettings().getInt("Animations." + this.x + ".Line");
        if (ls.size() != 1) {
            if (ls.size() != i) {
                ++i;
            }

            if (ls.size() == i) {
                i = 0;
            }

            this.p.getSettings().set("Animations." + this.x + ".Line", i);
        }
    }
}
