package org.functions.Bukkit.api;

import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.functions.Bukkit.Events.PlayerSendActionBarEvent;
import org.functions.Bukkit.Main.*;
import org.functions.Bukkit.api.Animation.Animations;
import org.functions.Bukkit.api.Permissions.BukkitPermission;
import org.functions.Bukkit.api.serverPing.PingResponse;
import org.functions.Bukkit.api.serverPing.ServerAddress;
import org.functions.Bukkit.api.serverPing.ServerPinger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class API {
    public API() {

    }
    public String message(String msg) {
        return msg.replace("&","§");
    }
    public String msg(String msg) {
        return getPluginPrefix() + msg.replace("&","§");
    }
    public void registerEvents(Listener listener) {
        getPlugins().registerEvents(listener,Functions.instance);
    }
    public void getCommand(String name, TabExecutor tabExecutor) {
        Functions.instance.getCommand(name).setExecutor(tabExecutor);
        Functions.instance.getCommand(name).setTabCompleter(tabExecutor);
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
        return getPluginPrefix() + replace(Message,false);
    }
    public BukkitPermission getBukkitPermissions() {
        return new BukkitPermission();
    }
    public Location formatLocation(String path) {
        String[] l = path.split(",");
        return new Location(Bukkit.getWorld(l[0]),Double.parseDouble(l[1]),Double.parseDouble(l[2]),Double.parseDouble(l[3]),Float.parseFloat(l[4]),Float.parseFloat(l[5]));
    }
    public Location changeStringToLocation(String position) {
        String[] location = position.split(",");
        return new Location(Bukkit.getWorld(location[0]),Double.parseDouble(location[1]),Double.parseDouble(location[2]),Double.parseDouble(location[3]),Float.parseFloat(location[4]),Float.parseFloat(location[5]));
    }
    public String[] changeLocationToListString(Location loc) {
        return (loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch()).split(",");
    }
    public String changeLocationToString(Location loc) {
        return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
    }
    public String toLocation(Location loc) {
        String x = Functions.instance.getSettings().getString("Location");
        x = x.replace("%world%", loc.getWorld().getName());
        x = x.replace("%x%", loc.getX() / 1000.0D + "");
        x = x.replace("%y%", loc.getY() / 1000.0D + "");
        x = x.replace("%z%", loc.getZ() / 1000.0D + "");
        x = x.replace("%yaw%", loc.getYaw() / 1000.0F + "");
        x = x.replace("%pitch%", loc.getPitch() / 1000.0F + "");
        return x;
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
    public String getPlayerList() {
        String format = Functions.instance.getSettings().getString("Players");
        StringBuilder l = new StringBuilder();
        int i = 0;
        for (Player p : getOnlinePlayers()) {
            i++;
            l.append(format.replace("%player%", p.getName()).replace("%count%", i + ""));
        }
        return putLanguage("PlayerList","玩家(%size%): %players%(%count%)").replace("%size%",i+"");
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
    public String getVersion() {
        if (Functions.instance.getDescription().getVersion()==null) {
            return "0.0.0.0";
        }
        return Functions.instance.getDescription().getVersion();
    }
    public String replace(Object Message,boolean server) {
        String msg = Message+"";
        msg = msg.replace("&","§");
        msg = msg.replace("%server_tps%",tps());
        msg = msg.replace("%tps%",getTPS());
        msg = msg.replace("%server_name%",Functions.instance.getConfig().getString("Server-Name","Unknown"));
        return msg+"";
    }
    public String TrueOrFalse(boolean tf) {
        return tf ? putLanguage("True","&a是") : putLanguage("False","&c否");
    }
    public String TrueOrFalseString(String msg,boolean tf) {
        return tf ? "§a" + msg : "§c" + msg;
    }
    public String replace(Object p,Object Message) {
        String msg = Message+"";
        Data data = new Data(getPlayer(p).getUniqueId());
        for (String s : Functions.instance.getAnimations()) {
            msg = msg.replace("%animation:" + s + "%", Animations.getString(s));
        }
        msg = msg.replace("%prefix%",data.getPrefixes().getPrefix());
        msg = msg.replace("%suffix%",data.getSuffixes().getSuffix());
        msg = msg.replace("&","§").replace("%server_tps%",tps()).replace("%tps%",getTPS()).replace("%server_name%",Functions.instance.getConfig().getString("Server-Name")).replace("none","").replace("%display_name%",getPlayer(p).getDisplayName());
        msg = msg.replace("%player%",getPlayer(p).getName()).replace("%date%",getDate()).replace("%time%",getTime());
        return msg;
    }
    public boolean BungeeCord() {
        String dir_String = Functions.instance.getDataFolder().getAbsolutePath().replace('\\','/');
        int lastindex = dir_String.lastIndexOf('/');
        return false;

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
        getServer().getConsoleSender().sendMessage(replace(Message,true));
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
	public void sendActionBar(Object Message) {
        PlayerSendActionBarEvent event = new PlayerSendActionBarEvent(Message+"");
        event.schedule(event);
    }
    public Server getServer() {
        return Bukkit.getServer();
    }
    public String getNMS() {
        return getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }
    public int randomTeleport(Player p, World world, double x, double z, int min, int max) {
        Random r = new Random();
        double randX = r.nextInt(max) - min;
        double randZ = r.nextInt(max) - min;
        return 0;
        //Location offset = new Location(world, randX, 0, randZ).toHighestLocation(); // 获得最高的非空气方块
        //p.teleport(player.getLocation().add(offset)); // add 加算距离
        //player.sendMessage(nms.nms.String(1,"SpreadPlayers-Successfully","Successfully players spread players to location.");
    }
    public int randomTeleport(Player p,double x,double z,int min,int max) {
        Random r = new Random();
        double randX = r.nextInt(max) - min;
        double randZ = r.nextInt(max) - min;
        return 0;
        //Location offset = new Location(playerWorld, randX, 0, randZ).toHighestLocation(); // 获得最高的非空气方块
        //p.teleport(player.getLocation().add(offset)); // add 加算距离
        //player.sendMessage(nms.nms.String(1,"SpreadPlayers-Successfully","Successfully players spread players to location.");
    }
//    public int randomTeleport(Player p,int min,int max) {
//		double x = p.getLocation().getX();
//		double z = p.getLocation().getY();
//
//    }
}
