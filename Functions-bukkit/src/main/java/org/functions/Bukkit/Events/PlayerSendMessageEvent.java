package org.functions.Bukkit.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.functions.Bukkit.Main.Functions;

public class PlayerSendMessageEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private String msg;
    private Player from;
    private Player to;
    public PlayerSendMessageEvent(Player from,Player to,String Message) {
        this.from = from;
        this.to = to;
        msg = Message;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
    public String getMessage() {
        return msg;
    }
    public void setMessage(String message) {
        msg = message;
    }
    public Player getFrom() {
        return from;
    }
    public Player getTo() {
        return to;
    }
    public boolean isCancelled() {
        return cancel;
    }
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
    public void schedule(PlayerSendMessageEvent event) {
        if (cancel) {
            Functions.instance.getServer().getPluginManager().callEvent(event);
            return;
        }
        synchronized (Functions.instance.getServer()) {
            Functions.instance.getServer().getScheduler().scheduleSyncDelayedTask(Functions.instance, new Runnable() {
                public void run() {
                    Functions.instance.getServer().getPluginManager().callEvent(event);
                    String f = Functions.instance.getAPI().replace(from,msg);
                    String t = Functions.instance.getAPI().replace(to,msg);
                    from.sendMessage(t);
                    to.sendMessage(f);
                }
            },10);
        }
    }
}
