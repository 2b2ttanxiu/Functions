package org.functions.Bukkit.api.Economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.functions.Bukkit.Main.Functions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class YamlEconomy implements Economy {
    public static class YamlData {
        private FileConfiguration dataConfig = null;
        private File configFile = null;
        private String fileName = null;
        private String path = null;

        public YamlData(String fileName, String path) {
            this.fileName = fileName;
            this.path = path;
            this.saveDefaultConfig();
        }

        public void reloadConfig() {
            if (this.configFile == null) {
                this.configFile = new File(this.path, this.fileName);
            }

            this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
            InputStream defaultStream = Functions.instance.getResource(this.fileName);
            if (defaultStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
                this.dataConfig.setDefaults(defaultConfig);
            }

        }

        public FileConfiguration getConfig() {
            if (this.dataConfig == null) {
                this.reloadConfig();
            }

            return this.dataConfig;
        }

        public void saveConfig() {
            if (this.dataConfig != null && this.configFile != null) {
                try {
                    this.getConfig().save(this.configFile);
                } catch (IOException var2) {
                    var2.printStackTrace();
                }

            }
        }

        public void saveDefaultConfig() {
            if (this.configFile == null) {
                this.configFile = new File(this.path, this.fileName);
            }

            try {
                this.configFile.createNewFile();
            } catch (IOException var2) {
                var2.printStackTrace();
            }

            if (!this.configFile.exists()) {
                Functions.instance.saveResource(this.path + "/" + this.fileName, false);
            }

        }
    }
    public static String getPaths() {
        return Functions.instance.getDataFolder().getAbsolutePath();
    }
    public String getPath() {
        return Functions.instance.getDataFolder().getAbsolutePath();
    }
    public YamlEconomy() {
        Path dataDir = Paths.get(Functions.instance.getDataFolder().getAbsolutePath() + "/user/economy/");
        if (!Files.exists(dataDir, new LinkOption[0])) {
            try {
                Files.createDirectory(dataDir);
            } catch (IOException var3) {
                return;
            }
        }

    }

    public boolean createAccount(UUID uuid) {
        this.set(uuid, Functions.instance.getSettings().getDouble("Money.InitPlayerBalance"));
        return true;
    }

    public boolean hasAccount(UUID uuid) {
        Iterator var3 = this.getPlayers().iterator();

        while(var3.hasNext()) {
            PlayerBalance pb = (PlayerBalance)var3.next();
            if (pb.getUUID().equals(uuid)) {
                return true;
            }
        }

        return false;
    }

    public boolean delete(UUID uuid) {
        File islandFile = new File(Functions.instance.getEconomyFolder(),uuid + ".yml");
        islandFile.delete();
        return true;
    }

    public boolean withdraw(UUID uuid, double amount) {
        return this.set(uuid, this.getBalance(uuid).getBalance() - amount);
    }

    public boolean deposit(UUID uuid, double amount) {
        return this.set(uuid, this.getBalance(uuid).getBalance() + amount);
    }

    public boolean set(UUID uuid, double amount) {
        if (amount < 0.0D) {
            return false;
        } else {
            FileConfiguration data = Functions.instance.getData_Economy(uuid);
            data.set("uuid", uuid);
            data.set("Balance", amount);
            try {
                data.save(new File(Functions.instance.getEconomyFolder(), uuid+".yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public boolean has(UUID uuid, double amount) {
        return this.getBalance(uuid).getBalance() >= amount;
    }

    public PlayerBalance getBalance(UUID uuid) {
        try {
            FileConfiguration data = Functions.instance.getData_Economy(uuid);
            double balance = data.getDouble("Balance");
            return new PlayerBalance(uuid, balance);
        } catch (Exception var5) {
            return new PlayerBalance(uuid, 0.0D);
        }
    }
    public static List<PlayerBalance> getFilePlayers() {
        List<PlayerBalance> playerData = new ArrayList();
        File[] files = (new File(getPaths() + "/user/economy")).listFiles();
        File[] var6 = files;
        int var5 = files.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            File file = var6[var4];
            YamlData data = new YamlData(file.getName(), getPaths() + "/user/economy");
            UUID uuid = UUID.fromString(data.getConfig().getString("uuid"));
            double balance = data.getConfig().getDouble("Balance");
            playerData.add(new PlayerBalance(uuid, balance));
        }

        return playerData;
    }
    public List<PlayerBalance> getPlayers() {
        List<PlayerBalance> playerData = new ArrayList();
        File[] files = (new File(getPath() + "/user/economy")).listFiles();
        File[] var6 = files;
        int var5 = files.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            File file = var6[var4];
            YamlData data = new YamlData(file.getName(), getPath() + "/user/economy");
            UUID uuid = UUID.fromString(data.getConfig().getString("uuid"));
            double balance = data.getConfig().getDouble("Balance");
            playerData.add(new PlayerBalance(uuid, balance));
        }

        return playerData;
    }
}
