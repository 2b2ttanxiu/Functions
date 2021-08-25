package org.functions.Bukkit.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.Main.Group;

import java.io.IOException;

public class Listeners implements Listener {
    Data data;
    @EventHandler
    public void Join(PlayerJoinEvent event) {
        data = new Data(event.getPlayer().getUniqueId());
        event.setJoinMessage(Functions.instance.getAPI().replace(event.getPlayer(),new Group("Default").getJoinFormat()));
    }
    public void Quit(PlayerQuitEvent event) {

    }
}
