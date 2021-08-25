package org.functions.Bukkit.Main;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.functions.Bukkit.api.API;
import org.functions.Bukkit.api.serverPing.ServerAddress;
import org.functions.Bukkit.Main.CheckJvm;
import org.functions.Bukkit.Main.Metrics;
import org.functions.Bukkit.runTask.AutoSaveConfiguration;
import org.functions.Bukkit.runTask.BalanceTopAutoRunnable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.*;

public final class Functions extends JavaPlugin {
    public static Functions instance;
    private BalanceTopAutoRunnable runnable = null;
    private Map<String, Integer> suffixes = new HashMap();
    public API getAPI() {
        return new API();
    }
    public void onLoad() {
        if (!(new File(getDataFolder(),"config.yml").exists())) {
            saveResource("config.yml",false);
        }
        instance= this;
    }
    public VaultHook getVault() {
        return new VaultHook();
    }
    public void onEnable() {
        instance = this;
        install();
        //Metrics me= new Metrics(this,11673);
        //me.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
        CheckJvm.checkJvm();
        if (getConfig().getBoolean("check-update")) {
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new Download(), 0, (getSettings().getLong("check-update.minutes") * 60 * 20));
        }
        runnable = new BalanceTopAutoRunnable();
        runnable.start(getSettings().getInt("Money.BalanceTopInterval"));
        getServer().getScheduler().scheduleSyncRepeatingTask(this,new AutoSaveConfiguration(),getConfig().getLong("AutoSaveConfiguration.delay"),getConfig().getLong("AutoSaveConfiguration.period"));
//            for (String s : Functions.instance.getAPI().getConfigurations().getGroup().getConfigurationSection("Groups").getKeys(false)) {
//                Functions.instance.getAPI().getGroup(s).setName("默认组");
//                con.saveAll();
    }
    public void install() {
        onSettings();
        onLanguage();
        onGroup();
        getAPI().install();
    }
    public void saveConfiguration() {
        try {
            getSettings().save(settings_file);
            getLanguage().save(language_file);
            getGroup().save(group_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onDisable() {
        saveConfiguration();
        instance= null;
    }
    public double getAmountFromString(String string) {
        int mult = 0;
        Iterator var3 = suffixes.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, Integer> suffix = (Map.Entry)var3.next();
            if (string.endsWith((String)suffix.getKey())) {
                string = string.substring(0, string.length() - 1);
                mult = (Integer)suffix.getValue();
            }
        }

        double pow = Math.pow(10.0D, (double)mult);
        return (double)Math.round(Double.valueOf(string) * 100.0D * pow) / (100.0D * pow) * pow;
    }
    public String AmountFormat(double amount) {
        Locale locale = Locale.forLanguageTag(getConfig().getString("Locale"));
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        String formatted = numberFormat.format(amount).replace("§", " ").replace("§", " ");
        if (getSettings().getBoolean("Money.customSymbolEnabled")) {
            formatted = formatted.replace(Currency.getInstance(locale).getSymbol(locale), getSettings().getString("Money.customSymbol"));
        }

        return formatted;
    }
    public void setSuffixes(Map<String, Integer> suffixesFromConfig) {
        suffixes = suffixesFromConfig;
    }
    public Map<String, Integer> getSuffixes() {
        return suffixes;
    }
    public BalanceTopAutoRunnable getBalanceTopRunnable() {
        return runnable;
    }
    public void onLoadFile(File file,FileConfiguration config,String name,boolean replace) {
        if (!file.exists()) {
            saveResource(name,replace);
        }
        YamlConfiguration.loadConfiguration(file);
        onLoadConfiguration(file,config,name);
    }
    public void onLoadConfiguration(File file,FileConfiguration config,String name) {
        File Error_Arching = new File(getDataFolder(),name.split("\\.")[0] + "-Error-Arching.yml");
        try {
            config.load(file);
        } catch (FileNotFoundException e) {
            if (Error_Arching.exists()) {
                Error_Arching.deleteOnExit();
                Error_Arching.delete();
            }
            file.renameTo(Error_Arching);
            file.deleteOnExit();
            file.delete();
            onLoadFile(file,config,name,false);
            e.printStackTrace();
        } catch (IOException e) {
            if (Error_Arching.exists()) {
                Error_Arching.deleteOnExit();
                Error_Arching.delete();
            }
            file.renameTo(Error_Arching);
            file.deleteOnExit();
            file.delete();
            onLoadFile(file,config,name,false);
            e.printStackTrace();

        } catch (InvalidConfigurationException e) {
            if (Error_Arching.exists()) {
                Error_Arching.deleteOnExit();
                Error_Arching.delete();
            }
            file.renameTo(Error_Arching);
            file.deleteOnExit();
            file.delete();
            onLoadFile(file,config,name,false);
            e.printStackTrace();

        }
    }
    public static Functions getInstance() {
        return instance;
    }
    public static Functions getMain() {
        return instance;
    }
    private File user_file;
    private File economy_file;
    private File dir = new File(getDataFolder().getAbsolutePath(),"user");
    private Path dirs = Paths.get(Functions.instance.getDataFolder().getAbsolutePath() + "/user/");
    private File dir_data = new File(dir,"data");
    private File dir_economy = new File(dir,"economy");
    public File getEconomyFolder() {
        return new File(dir,"economy");
    }
    public void saveData(UUID uuid) {
        user_file = new File(dir_data,uuid+".yml");
        economy_file = new File(dir_economy,uuid+".yml");
        try {
            data.save(user_file);
            economy.save(economy_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onData(UUID uuid){
        File data = new File(dir,"data");
        File economy = new File(dir,"economy");
        if (!data.exists()) {
            data.mkdirs();
        }
        if (!economy.exists()) {
            economy.mkdirs();
        }
        user_file = new File(data,uuid+".yml");
        try {
            if (!user_file.exists()) {
                InputStream in = getResource("user.yml");
                OutputStream out = new FileOutputStream(user_file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        economy_file = new File(economy,uuid+".yml");
        try {
            if (!economy_file.exists()) {
                InputStream in = getResource("user.yml");
                OutputStream out = new FileOutputStream(economy_file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private FileConfiguration economy = new YamlConfiguration();
    private FileConfiguration data = new YamlConfiguration();
    public FileConfiguration getData_Economy(UUID uuid) {
        File file = new File(dir_economy,uuid+".yml");
        YamlConfiguration.loadConfiguration(file);
        File Error_Arching = new File(dir_economy,uuid+"-Error-Arching.yml");
        try {
            economy.load(file);
        } catch (IOException e) {
            if (Error_Arching.exists()) {
                Error_Arching.deleteOnExit();
                Error_Arching.delete();
            }
            file.renameTo(Error_Arching);
            file.deleteOnExit();
            file.delete();
            onData(uuid);
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return economy;
    }
    public FileConfiguration getData_Data(UUID uuid) {
        File file = new File(dir_data,uuid+".yml");
        YamlConfiguration.loadConfiguration(file);
        File Error_Arching = new File(dir_data,uuid+"-Error-Arching.yml");
        try {
            data.load(file);
        } catch (IOException e) {
            if (Error_Arching.exists()) {
                Error_Arching.deleteOnExit();
                Error_Arching.delete();
            }
            file.renameTo(Error_Arching);
            file.deleteOnExit();
            file.delete();
            onData(uuid);
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return data;
    }
    private File language_file = new File(getDataFolder(),"Language-" + getConfig().getString("Language") + ".yml");
    private FileConfiguration language = new YamlConfiguration();
    private void onLanguage() {
        onLoadFile(language_file,language,"Language-" + getConfig().getString("Language") + ".yml",false);
    }
    public FileConfiguration getLanguage() {
        return language;
    }
    private File settings_file = new File(getDataFolder(),"Settings.yml");
    private FileConfiguration settings = new YamlConfiguration();
    private void onSettings() {
        onLoadFile(settings_file,settings,"Settings.yml",false);
    }
    public FileConfiguration getSettings() {
        return settings;
    }
    private File group_file = new File(getDataFolder(),"Groups.yml");
    private FileConfiguration group = new YamlConfiguration();
    private void onGroup() {
        onLoadFile(settings_file,settings,"Groups.yml",false);
    }
    public FileConfiguration getGroup() {
        return settings;
    }
    private File help_file = new File(getDataFolder(),"Helps.yml");
    private FileConfiguration help = new YamlConfiguration();
    private void onHelp() {
        onLoadFile(help_file,help,"Helps.yml",false);
    }
    public FileConfiguration getHelp() {
        return help;
    }
    private File warp_file = new File(getDataFolder(),"Warps.yml");
    private FileConfiguration warp = new YamlConfiguration();
    private void onWarps() {
        onLoadFile(warp_file,warp,"Warps.yml",false);
    }
    public FileConfiguration getWarps() {
        return warp;
    }
    private File serverTitle_file = new File(getDataFolder(),"ServerTitle.yml");
    private FileConfiguration serverTitle = new YamlConfiguration();
    private void onServerTitle() {
        onLoadFile(serverTitle_file,serverTitle,"ServerTitle.yml",false);
    }
    public FileConfiguration getServerTitle() {
        return serverTitle;
    }
    private File report_file = new File(getDataFolder(),"Report.yml");
    private FileConfiguration report = new YamlConfiguration();
    private void onReport() {
        onLoadFile(report_file,report,"Report.yml",false);
    }
    public FileConfiguration getReport() {
        return report;
    }
    private File op_file = new File(getDataFolder(),"op.yml");
    private FileConfiguration op = new YamlConfiguration();
    private void onOP() {
        onLoadFile(op_file,op,"op.yml",false);
    }
    public FileConfiguration getOP() {
        return op;
    }

}
