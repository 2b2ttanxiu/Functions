package org.functions.Bukkit.api;

import org.bukkit.entity.Player;

public interface Message {
    String getFormatFrom();
    String getFormatTo();
    int send(Player from, Player to, String message);
}
