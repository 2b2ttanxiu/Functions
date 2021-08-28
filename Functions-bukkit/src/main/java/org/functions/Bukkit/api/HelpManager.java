package org.functions.Bukkit.api;

import java.util.List;

public interface HelpManager {
    String getMain();
    List<String> getSubMain();
    List<String> getSubHas(String name);
    List<String> getSubs(String name);
    List<String> getSubMessage(String name);
}
