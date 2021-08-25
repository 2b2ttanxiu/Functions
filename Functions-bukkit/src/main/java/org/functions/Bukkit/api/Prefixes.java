package org.functions.Bukkit.api;

import java.util.List;

public interface Prefixes {
    void setPrefix(String prefix);
    String getPrefix();
    void setPrefixes(List<String> prefixes);
    List<String> getPrefixes();
}
