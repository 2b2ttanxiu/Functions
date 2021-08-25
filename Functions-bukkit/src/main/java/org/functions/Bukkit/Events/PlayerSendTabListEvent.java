package org.functions.Bukkit.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.api.Tab;

import java.util.Collection;

public class PlayerSendTabListEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private String footer = null;
    private String header = null;
    private String listName = null;
    private Collection<? extends Player> players;
    public PlayerSendTabListEvent(Collection<? extends Player> players,String header,String footer,String listName) {
        this.footer = footer;
        this.header = header;
        this.listName = listName;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
    public boolean isCancelled() {
        return cancel;
    }
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public String getFooter() {
        return footer;
    }
    public void setFooter(String footer) {
        this.footer = footer;
    }
    public String getListName() {
        return listName;
    }
    public void setListName() {
        this.listName = listName;
    }
    public void schedule(PlayerSendTabListEvent event) {
        if (cancel) {
            Functions.instance.getAPI().getPlugins().callEvent(event);
            return;
        }
        synchronized (Functions.instance.getServer()) {
            Functions.instance.getServer().getScheduler().scheduleSyncDelayedTask(Functions.instance, new Runnable() {
                public void run() {
                    for (Player p : Functions.instance.getAPI().getOnlinePlayers()) {
                        header = Functions.instance.getAPI().replace(p,header);
                        footer = Functions.instance.getAPI().replace(p,footer);
                        listName = Functions.instance.getAPI().replace(p,listName);
                        new Tab(p,header,footer,listName);
                    }
                }
            },10);
        }
    }
}
