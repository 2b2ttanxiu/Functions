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
import org.functions.Bukkit.runTask.ScoreboardRunnable;

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
        instance = this;
    }
    public VaultHook getVault() {
        return new VaultHook();
    }
    public void onEnable() {
        instance = this;
        install();
        getAPI().install();
        AllRegister.run();
        //Metrics me= new Metrics(this,11673);
        //me.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
        CheckJvm.checkJvm();
        if (getConfig().getBoolean("check-update")) {
            //getServer().getScheduler().scheduleSyncRepeatingTask(this, new Download(), 0, (getSettings().getLong("check-update.minutes") * 60 * 20L));
        }
        runnable = new BalanceTopAutoRunnable();
        runnable.start(getSettings().getInt("Money.BalanceTopInterval"));
        getServer().getScheduler().scheduleSyncRepeatingTask(this,new AutoRun(),0L,1L);
        getServer().getScheduler().scheduleSyncRepeatingTask(this,new ScoreboardRunnable(),20,getSettings().getLong("ScoreBoard.interval") / 1000 * 20);
        getServer().getScheduler().scheduleSyncRepeatingTask(this,new AutoSaveConfiguration(),getConfig().getLong("AutoSaveConfiguration.delay") * 20,getConfig().getLong("AutoSaveConfiguration.period") * 20L);
//            for (String s : Functions.instance.getAPI().getConfigurations().getGroup().getConfigurationSection("Groups").getKeys(false)) {
//                Functions.instance.getAPI().getGroup(s).setName("默认组");
//                con.saveAll();
    }
    public void install() {
        onSettings();
        onLanguage();
        onServerTitle();
        onGroup();
        onHelp();
        onOP();
        onWarps();
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
    public File Animation_dir = new File(getDataFolder(),"animations");
    private File animation;
    private FileConfiguration Animation = new YamlConfiguration();
    public List<String> getAnimations() {
        if (!Animation_dir.exists()){
            Animation_dir.mkdir();
        }
        List<String> ls = new ArrayList<>();
        File[] files = Animation_dir.listFiles();
        assert files != null;
        int count = files.length;
        if (count == 0) {
            onAnimation("example");
        }
        for(int i = 0; i < count; ++i) {
            File f = files[i];
            if (f.getName().contains("-Error-Arching.yml")) {
                continue;
            }
            ls.add(f.getName().replace(".yml",""));
        }
        return ls;
    }
    public FileConfiguration getAnimation(String file) {
        File fil = new File(Animation_dir,file+".yml");
        YamlConfiguration.loadConfiguration(fil);
        File Error_Arching = new File(Animation_dir,file+"-Error-Arching.yml");
        try {
            Animation.load(fil);
        } catch (IOException e) {
            if (Error_Arching.exists()) {
                Error_Arching.deleteOnExit();
                Error_Arching.delete();
            }
            fil.renameTo(Error_Arching);
            fil.deleteOnExit();
            fil.delete();
            onAnimation(file);
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            if (Error_Arching.exists()) {
                Error_Arching.deleteOnExit();
                Error_Arching.delete();
            }
            fil.renameTo(Error_Arching);
            fil.deleteOnExit();
            fil.delete();
            onAnimation(file);
            e.printStackTrace();
        }
        return Animation;
    }
    public void saveAnimation(String file) {

    }
    public void onAnimation(String file) {
        if (!Animation_dir.exists()) {
            Animation_dir.mkdir();
        }
        animation = new File(Animation_dir,file+".yml");
        if (!animation.exists()) {
            InputStream in = getResource("Animation.yml");
            OutputStream out = null;
            try {
                out = new FileOutputStream(animation);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration.loadConfiguration(animation);
        try {
            Animation.load(animation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }
    private File user_file;
    private File economy_file;
    private File dir = new File(getDataFolder().getAbsolutePath(),"user");
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
            economy.save(file);
            economy.load(file);
            economy.set("uuid",uuid.toString()+"");
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
            if (Error_Arching.exists()) {
                Error_Arching.deleteOnExit();
                Error_Arching.delete();
            }
            file.renameTo(Error_Arching);
            file.deleteOnExit();
            file.delete();
            onData(uuid);
            e.printStackTrace();
            e.printStackTrace();
        }
        return economy;
    }
    public FileConfiguration getData_Data(UUID uuid) {
        File file = new File(dir_data,uuid+".yml");
        YamlConfiguration.loadConfiguration(file);
        File Error_Arching = new File(dir_data,uuid+"-Error-Arching.yml");
        try {
            data.save(file);
            data.load(file);
            data.set("uuid",uuid.toString()+"");
            if (data.getString("Group")==null) {
                data.set("Group","Default");
            }
            data.save(file);
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
            if (Error_Arching.exists()) {
                Error_Arching.deleteOnExit();
                Error_Arching.delete();
            }
            file.renameTo(Error_Arching);
            file.deleteOnExit();
            file.delete();
            onData(uuid);
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
    public File group_file = new File(getDataFolder(),"Groups.yml");
    private FileConfiguration group = new YamlConfiguration();
    private void onGroup() {
        onLoadFile(group_file,group,"Groups.yml",false);
    }
    public FileConfiguration getGroup() {
        return group;
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
    private File op_file = new File(getDataFolder(),"ops.yml");
    private FileConfiguration op = new YamlConfiguration();
    private void onOP() {
        onLoadFile(op_file,op,"ops.yml",false);
    }
    public FileConfiguration getOP() {
        return op;
    }
    public List<String> getOperators() {
        return getOP().getStringList("Operators");
    }
    public void saveOperators(List<String> ls) {
        getOP().set("Operators",ls);
        try {
            getOP().save(op_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
