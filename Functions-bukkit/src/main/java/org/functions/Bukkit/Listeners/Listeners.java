package org.functions.Bukkit.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;
import org.functions.Bukkit.Main.Data;
import org.functions.Bukkit.Main.Functions;
import org.functions.Bukkit.Main.Group;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.ClickPerSecondsManager;
import org.functions.Bukkit.api.Permissions.BukkitPermission;
import org.functions.Bukkit.runTask.ServerTitleRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;

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
        if (!BukkitPermission.has(event.getPlayer().getUniqueId(), "functions.event.build")) {
            event.getPlayer().sendMessage(BukkitPermission.noPerms("functions.event.build"));
            event.setCancelled(true);
            event.setBuild(false);
        }
    }
    @EventHandler
    public void Break(BlockBreakEvent event) {
        data = new Data(event.getPlayer().getUniqueId());
        if (!BukkitPermission.has(event.getPlayer().getUniqueId(), "functions.event.break")) {
            event.getPlayer().sendMessage(BukkitPermission.noPerms("functions.event.break"));
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
    @EventHandler
    public void run(BlockDispenseEvent b) {
        if ((b.getBlock().getLocation().getY() == (double)(b.getBlock().getWorld().getMaxHeight() - 1) || b.getBlock().getLocation().getY() == 0.0D || b.getBlock().getLocation().getY() == -64.0D) && b.getItem().getType().name().endsWith("SHULKER_BOX")) {
            b.setCancelled(true);
            Location loc = b.getBlock().getLocation();
            Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc,256,256,256);
            Player player = null;
            for (Entity e : entities) {
                if (!(e instanceof Player)) {
                    continue;
                }
                player = (Player)e;
            }
            assert player != null;
            api.sendOperators(api.getPluginPrefix() + "§c[ERROR] One of the players tried to crash the server! Player name: " + player.getName() + " Position: " + api.changeLocationToString(loc));
            api.sendConsole("§c[ERROR] One of the players tried to crash the server! Player name: " + player.getName() + " Position: " + api.changeLocationToString(loc));
        }

    }
    @EventHandler
    public void ServerTitle(ServerListPingEvent event) {
        event.setMotd(ServerTitleRunnable.getString());
        event.setMaxPlayers(ServerTitleRunnable.getMax());
        for (File file : Objects.requireNonNull(Functions.instance.getServerIcons())) {
            if (Functions.instance.getSettings().getBoolean("Maintenance")) {
                if (!file.getName().contains("serviceModeIcon.png")) {
                    continue;
                }
                try {
                    event.setServerIcon(Functions.instance.getServer().loadServerIcon(file));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (file.getName().contains("serviceModeIcon.png")) {
                    continue;
                }
                Random random = new Random();
                int i = random.nextInt(Functions.instance.getServerIcons().size());
                if (Functions.instance.getServerIcons().get(i).getName().contains("serviceModeIcon.png")) {
                    continue;
                }
                try {
                    event.setServerIcon(Functions.instance.getServer().loadServerIcon(Objects.requireNonNull(Functions.instance.getServerIcons().get(i))));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
