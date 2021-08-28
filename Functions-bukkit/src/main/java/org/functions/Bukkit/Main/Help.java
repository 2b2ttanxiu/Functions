package org.functions.Bukkit.Main;

import org.functions.Bukkit.api.HelpManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Help implements HelpManager {
    public String getMain() {
        return Functions.instance.getHelp().getString("Main");
    }

    public List<String> getSubMain() {
        Set<String> key = Functions.instance.getHelp().getConfigurationSection("Pages").getKeys(false);
        List<String> ls = new ArrayList<>(key);
        Collections.sort(ls);
        return ls;

    }

    public List<String> getSubHas(String name) {
        List<String> ls = new ArrayList<>();
        for (String s : getSubMain()) {
            if (s.startsWith(name)) {
                ls.add(s);
            }
        }
        return ls;
    }

    public List<String> getSubMessage(String name) {
        if (Functions.instance.getHelp().getString("Pages." + name).startsWith("[") || Functions.instance.getHelp().getString("Pages." + name).endsWith("]")) {
            return Functions.instance.getHelp().getStringList("Pages." + name);
        }
        List<String> ls = new ArrayList<>();
        ls.add(Functions.instance.getHelp().getString("Pages." + name));
        return ls;
    }
    public List<String> getSubs(String name) {
        Set<String> key = Functions.instance.getHelp().getConfigurationSection("Pages").getKeys(false);
        return new ArrayList<>(key);
    }
}
