package org.functions.Bukkit.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scoreboard.*;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.api.API;

import java.util.List;

public class PlayerSendScoreboardDisplayNameEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private List<String> line;
    private String displayName;
    private String type;
    private Data data;
    public PlayerSendScoreboardDisplayNameEvent(String displayName, String type, List<String> line) {
        this.display = displayName;
        this.type = type;
        this.line = line;
    }
    public HandlerList getHandlers() {
        return handlers;
    }

    public List<String> getLine() {
        return line;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String type() {
        return type;
    }

    public Data data() {
        return data;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLine(List<String> line) {
        this.line = line;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
    String display;
    List<String> ls = null;
    Scoreboard scoreboard;
    Functions a = Functions.instance;
    API api = a.getAPI();
    public void schedule(PlayerSendScoreboardDisplayNameEvent event) {
                    if (cancel) {
                        Functions.instance.getServer().getPluginManager().callEvent(event);
                        return;
                    }
                    ls = line;
                    int i = 0;
                    for (Player p : api.getOnlinePlayers()) {
                        ScoreboardManager scoreboardManager = api.getServer().getScoreboardManager();
                        scoreboard = scoreboardManager.getNewScoreboard();
                        Objective objective = scoreboard.registerNewObjective("Functions", type);
                        objective.setDisplayName(api.replace(p,display));
                        Team team = scoreboard.registerNewTeam(p.getName());
                        data = new Data(p.getUniqueId());
                        if (!data.enableScoreBoard() || data.enableDisplay()) {
                            team.setPrefix(data.getPrefixes().getPrefix());
                            team.setSuffix(data.getSuffixes().getSuffix());
                            team.addEntry(p.getName());
                        }
                        if (data.enableScoreBoard() || !data.enableDisplay()) {
                            i = ls.size();
                            for (int s = 0; s < i; ++s) {
                                Score score = objective.getScore(
                                        api.replace(p, ls.get(s))
                                                .replace("&", "§")
                                                .replace("%group%", data.getGroup().getDisplayName())
                                                .replace("%prefix%", data.getPrefixes().getPrefix())
                                                .replace("%suffix%", data.getSuffixes().getSuffix())
                                                .replace("%ping%", data.getPing()));
                                score.setScore(i - s - 1);
                            }
                        }
                        scoreboard.clearSlot(DisplaySlot.SIDEBAR);
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                        p.setScoreboard(scoreboard);
                    }
    }
}
