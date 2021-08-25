package org.functions.Bukkit.api;

import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.functions.Bukkit.Events.PlayerSendActionBarEvent;
import org.functions.Bukkit.Listeners.Listeners;
import org.functions.Bukkit.Main.*;
import org.functions.Bukkit.api.Permissions.BukkitPermissions;
import org.functions.Bukkit.api.serverPing.PingResponse;
import org.functions.Bukkit.api.serverPing.ServerAddress;
import org.functions.Bukkit.api.serverPing.ServerPinger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class API {
    public API() {

    }
    public String getGameDay(long time) {
        return (time / 18000L + "");
    }
    public String getGameTime(long time) {
        time = (time / 20 * 1200 + (60000 * 6));
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(Functions.instance.getSettings().getString("Date_GameTime"));
        String[] space = sdf.format(date).split(Functions.instance.getSettings().getString("Date_GameTime_split"));
        int h = Integer.parseInt(space[0]);
        int m = Integer.parseInt(space[1]);
        String s = "none";
        if (h > 23) {
            h = h - 24;
            s = h + Functions.instance.getSettings().getString("Date_GameTime_split") + m;
        }
        return s;
    }
    public String getDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Functions.instance.getSettings().getString("Date"));
        return sdf.format(date);
    }
    public String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Functions.instance.getSettings().getString("Time"));
        return sdf.format(date);
    }
    public String getPluginPrefix() {
        return Functions.instance.getConfig().getString("Prefix");
    }
    public String putLanguage(String path,Object Message) {
        if (Functions.instance.getLanguage().getString(path)==null) {
            Functions.instance.getLanguage().addDefault(path, Message);
            Functions.instance.getLanguage().options().copyDefaults(true);
            Functions.instance.getLanguage().options().copyHeader();
        }
        Message = getPluginPrefix() + Message + "";
        return Message+"";
    }
    public BukkitPermissions getBukkitPermissions() {
        return new BukkitPermissions(Functions.instance);
    }
    @Deprecated
    public Player getPlayer(String name) {
        return getPlayer(name);
    }
    public Player getPlayer(UUID uuid) {
        return getServer().getPlayer(uuid);
    }
    public void install() {
        if (getPlugin("Vault") !=null) {
            VaultHook.hasVault();
        }
        if (getPlugin("PlaceholderAPI") !=null) {
            new PlaceholderAPIHook().register();
        }
        getPlugins().registerEvents(new Listeners(), Functions.instance);
    }
    public void addPermission(Player p,String name) {
        //p.hasPermission(Permission.)
    }
    public List<String> getGroups() {
        Set<String> key = Functions.instance.getGroup().getConfigurationSection("Groups").getKeys(false);
        return new ArrayList<>(key);
    }
    public boolean EmptyGroup(String Group) {
        for (String s : getGroups()) {
            if (s.equalsIgnoreCase(Group)) {
                return true;
            }
        }
        return false;
    }
    public Collection<? extends Player> getOnlinePlayers() {
        return getServer().getOnlinePlayers();
    }
    public GroupManager getGroup(String group) {
        return new Group(group);
    }
	public String nms = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    public Plugin getPlugin(String name) {
        return getPlugins().getPlugin(name);
    }
    public PlaceholderAPIHook getPlaceHolderAPI() {
        return new PlaceholderAPIHook();
    }
    public PluginManager getPlugins() {
        return getServer().getPluginManager();
    }
    public String replace(Object p,Object Message) {
        String msg = Message+"";
        if (getPlugin("PlaceholderAPI") != null) {
            msg = PlaceholderAPI.setPlaceholders(getPlayer(p),msg);
        }
        msg = msg.replace("&","§");
        return msg+"";
    }
    public boolean BungeeCord() {
        File dir = new File(Functions.instance.getDataFolder()+"".replace("plugins\\Functions",""));
        File file = new File(dir,"spigot.yml");
        YamlConfiguration.loadConfiguration(file);
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return config.getBoolean("settings.bungeecord");

    }
    public PingResponse getServerList(ServerAddress address, int timeout) {
        return ServerPinger.fetchData(address, timeout);
    }
    public double[] recentTPS() {
        double[] d = new double[3];
        try {
            Class MinecraftServerClass = Class.forName("net.minecraft.server."+ nms + ".MinecraftServer");
            Object obj = MinecraftServerClass.getMethod("getServer").invoke((Object)null);
            Field f = MinecraftServerClass.getDeclaredField("recentTps");
            return (double[])obj.getClass().getField("recentTps").get(obj);
        } catch (ClassNotFoundException var6) {
            var6.printStackTrace();
        } catch (NoSuchFieldException var7) {
            var7.printStackTrace();
        } catch (IllegalAccessException var8) {
            var8.printStackTrace();
        } catch (NoSuchMethodException var9) {
            var9.printStackTrace();
        } catch (InvocationTargetException var10) {
            var10.printStackTrace();
        }

        return new double[]{-1.0D, -2.0D, -3.0D};
    }
    public String tps() {
        double[] tps = this.recentTPS();
        String[] tpsAvg = new String[tps.length];

        for(int i = 0; i < tps.length; ++i) {
            tpsAvg[i] = this.format(tps[i]);
        }
        return StringUtils.join(tpsAvg, ", ");
    }
    public String getTPS() {
        return tps().split(", ")[0];
    }

    public String format(double tps) {
        return (tps > 18.0D ? ChatColor.GREEN : (tps > 16.0D ? ChatColor.YELLOW : ChatColor.RED)).toString() + (tps > 21.0D ? "*" : "") + Math.min((double)Math.round(tps * 100.0D) / 100.0D, 20.0D);
    }
    public void onChangePlayerHat(PlayerInventory inventory, ItemStack Hand, ItemStack Head) {
        inventory.setHelmet(Hand);
        inventory.setItemInMainHand(Head);
    }
    public void sendConsole(Object Message) {
        getServer().getConsoleSender().sendMessage(replace(null,Message));
    }
    public Player getPlayer(Object player) {
        Player p = (Player)player;
        return getServer().getPlayer(p.getUniqueId());
    }
	public int getPing(Player p) {
		if (!p.getClass().getName().equals("org.bukkit.craftbukkit." + nms + ".entity.CraftPlayer")) {
            p = Bukkit.getPlayer(p.getUniqueId());
        }

        try {
            Class<?> CraftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nms + ".entity.CraftPlayer");
            Object CraftPlayer = CraftPlayerClass.cast(p);
            Method getHandle = CraftPlayer.getClass().getMethod("getHandle");
            Object EntityPlayer = getHandle.invoke(CraftPlayer);
            Field ping = EntityPlayer.getClass().getDeclaredField("ping");
            return ping.getInt(EntityPlayer);
        } catch (Exception var7) {
            var7.printStackTrace();
            return -2;
        }
	}
	public void sendActionBar(Player p,Object Message) {
        PlayerSendActionBarEvent event = new PlayerSendActionBarEvent(p,Message+"");
        event.schedule(event);
    }
    public Server getServer() {
        return Bukkit.getServer();
    }
    public String getNMS() {
        return getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }
//    public int randomTeleport(Player p,double x,double z,int min,int max) {
//    PlayerNMS nms = new PlayerNMS();
//    public boolean onCommand(CommandSender sender,Command cmd,String s,String[] args) {
//        if (!args[1].matches("[0-9]*") || !args[1].matches("[0-9]*")) {
//            if (!args[1].matches("[0-9]*.[0-9]*") || !args[1].matches("[0-9]*.[0-9]*")) {
//                sender.sendMessage(nms.String("NoLocation-number","You location is string,Try 0.0 or 0"))
//
//            }
//        }
//
//        double minloc = Double.parseDouble(args[1]);
//        double maxloc = Double.parseDouble(args[2]);
//        Player p = nms.getPlayer(false,args[0]);
//        Random r = new Random();
//        layerWorldp = Bukkit.getWorlds().get(0); // 获得主世界
//        double randX = r.nextInt(maxloc) - minloc;
//        double randZ = r.nextInt(maxloc) - minloc;
//        Location offset = new Location(playerWorld, randX, 0, randZ).toHighestLocation(); // 获得最高的非空气方块
//        p.teleport(player.getLocation().add(offset)); // add 加算距离
//        player.sendMessage(nms.nms.String(1,"SpreadPlayers-Successfully","Successfully players spread players to location.");
//        return true;
//    }
//    public int randomTeleport(Player p,int min,int max) {
//		double x = p.getLocation().getX();
//		double z = p.getLocation().getY();
//
//    }
}
