package org.functions.Bukkit.api;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.functions.Bukkit.Events.PlayerSendScoreboardDisplayNameEvent;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;

import java.util.List;

public class ScoreBoard {
    public ScoreBoard(String displayName, String type, List<String> line) {
        PlayerSendScoreboardDisplayNameEvent event = new PlayerSendScoreboardDisplayNameEvent(displayName,type,line);
        event.schedule(event);
    }
}
