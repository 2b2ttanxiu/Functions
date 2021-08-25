package org.functions.Bukkit.api;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.functions.Bukkit.Main.Functions;

public class WorldTime {
    Player p;
    World world;
    public WorldTime(Player player) {
        p = player;
        world = p.getWorld();
    }
    public String formatTime() {
        return Functions.instance.getAPI().getGameTime(world.getTime());
    }
    public String formatDay() {
        return Functions.instance.getAPI().getGameDay(world.getFullTime());
    }
    public void setTime(long add) {
        long full = world.getFullTime();
    }
}
