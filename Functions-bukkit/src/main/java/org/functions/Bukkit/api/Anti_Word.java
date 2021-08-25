package org.functions.Bukkit.api;

import org.bukkit.entity.Player;

import java.util.List;

public interface Anti_Word {
    List<String> getAntiWord();
    boolean hasAntiWord(String word);
    boolean hasReplaceAntiWord(String word);
    String replace(String Message);
}
