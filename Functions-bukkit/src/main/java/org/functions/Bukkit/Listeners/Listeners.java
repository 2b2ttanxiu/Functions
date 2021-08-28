package org.functions.Bukkit.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.Main.Group;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.ClickPerSecondsManager;

import java.io.IOException;

public class Listeners implements Listener {
    Data data;
    @EventHandler
    public void Join(PlayerJoinEvent event) {
        data = new Data(event.getPlayer().getUniqueId());
        event.setJoinMessage(Functions.instance.getAPI().replace(event.getPlayer(),new Group("Default").getJoinFormat()));
    }
    @EventHandler
    public void Quit(PlayerQuitEvent event) {
        data = new Data(event.getPlayer().getUniqueId());
        event.setQuitMessage(Functions.instance.getAPI().replace(event.getPlayer(),new Group("Default").getQuitFormat()));
    }
    @EventHandler
    public void build(BlockPlaceEvent event) {
        data = new Data(event.getPlayer().getUniqueId());
        if (!data.getBukkitPermissions().has(event.getPlayer().getUniqueId(), "functions.event.build")) {
            event.getPlayer().sendMessage(data.getBukkitPermissions().noPerms("functions.event.build"));
            event.setCancelled(true);
            event.setBuild(false);
        }
    }
    @EventHandler
    public void Break(BlockBreakEvent event) {
        data = new Data(event.getPlayer().getUniqueId());
        if (!data.getBukkitPermissions().has(event.getPlayer().getUniqueId(), "functions.event.break")) {
            event.getPlayer().sendMessage(data.getBukkitPermissions().noPerms("functions.event.break"));
            event.setCancelled(true);
            event.setDropItems(false);
        }
    }
    API api = Functions.instance.getAPI();
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        data = new Data(event.getPlayer().getUniqueId());
        event.setCancelled(true);
        event.setFormat("");
        event.setMessage("");
        if (data.getMute().exists()) {
            event.getPlayer().sendMessage(api.putLanguage("Muting","&c你正在被禁言，无法发送信息！"));
            return;
        }
        if (data.existsCanChat()) {
            String format = data.getGroup().getChatFormat();
            String msg = message;
            if (data.getGroup().getAnti_Word().hasAntiWord(msg)) {
                if (data.getGroup().getAnti_Word().hasReplaceAntiWord(msg)) {
                    msg = data.getGroup().getAnti_Word().replace(msg);
                } else {
                    return;
                }
            }
            api.getServer().broadcastMessage(api.replace(event.getPlayer(), format.replace("%message%",msg)));
            data.addChatTime();
        }
    }
    @EventHandler
    public void onCheck(AsyncPlayerPreLoginEvent event) {
        data = new Data(event.getUniqueId());
        if (data.getBanned().exists()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED,data.getBanned().getReason());
        }
    }
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        data = new Data(event.getPlayer().getUniqueId());
        ClickPerSecondsManager click = data.getClick_Per_Seconds();
        click.countCPS();
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        data = new Data(event.getEntity().getUniqueId());
        if (data.getMainClass().getSettings().getBoolean("GameRules.KeepInventory")) {
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.setDroppedExp(0);
            event.getDrops().clear();
        }
        data.getBacks().create(event.getEntity().getLocation());
    }
}
