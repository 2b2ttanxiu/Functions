package org.functions.Bukkit.Main;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
public class Configurations {
    private static String folder = Functions.instance.getDataFolder()+"\\";
    private static File config_file = new File(folder+"config.yml");
    private static FileConfiguration config = new YamlConfiguration();
    public static void save(String file,boolean replace) {
        Functions.instance.saveResource(file,replace);
    }
    public static void install() {
        try {
            onConfig();
            onSettings();
            onGroup();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
//        try {
//            onBanned();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidConfigurationException e) {
//            e.printStackTrace();
//        }
//        try {
//            onGroup();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidConfigurationException e) {
//            e.printStackTrace();
//        }
//        try {
//            onHelp();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidConfigurationException e) {
//            e.printStackTrace();
//        }
//        try {
//            onLanguage();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidConfigurationException e) {
//            e.printStackTrace();
//        }
//        try {
//            onOps();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidConfigurationException e) {
//            e.printStackTrace();
//        }
//        try {
//            onServerTitle();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidConfigurationException e) {
//            e.printStackTrace();
//        }
    }
    public static void saveAll() {
        try {
            getConfig().save(config_file);
            getGroup().save(group_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void onConfig() throws IOException, InvalidConfigurationException {
        if (!config_file.exists()) {
            save("config.yml", false);
        }
        YamlConfiguration.loadConfiguration(config_file);
        try {
            config.load(config_file);
        } catch (IOException e) {
            e.printStackTrace();
            File file = config_file;
            File files = new File(folder+"config-Error-Arching.yml");
            if (files.exists()) {
                files.deleteOnExit();
            }
            file.renameTo(files);
            file.deleteOnExit();
            onConfig();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            File file = config_file;
            File files = new File(folder+"config-Error-Arching.yml");
            if (files.exists()) {
                files.deleteOnExit();
            }
            file.renameTo(files);
            file.deleteOnExit();
            onConfig();
        }
    }
    public static FileConfiguration getConfig() {
        return config;
    }
    private static File language_file = new File(folder+"Language_" + config.getString("Language"));
    private static FileConfiguration language = new YamlConfiguration();
    public static void onLanguage() throws IOException, InvalidConfigurationException {
        if (!language_file.exists()) {
            save("Language-"+config.getString("Language")+".yml",false);
        }
        YamlConfiguration.loadConfiguration(language_file);
        language.load(language_file);
    }
    public static FileConfiguration getLanguage() {
        return language;
    }
    private static File serverTitle_file = new File(folder+"ServerTitle.yml");
    private static FileConfiguration serverTitle = new YamlConfiguration();
    public void onServerTitle() throws IOException, InvalidConfigurationException {
        if (!serverTitle_file.exists()) {
            save("ServerTitle.yml",false);
        }
        YamlConfiguration.loadConfiguration(serverTitle_file);
        serverTitle.load(serverTitle_file);
    }
    public FileConfiguration getServerTitle() {
        return serverTitle;
    }
    private static File ops_file = new File(folder+"ops.yml");
    private static FileConfiguration ops = new YamlConfiguration();
    public void onOps() throws IOException, InvalidConfigurationException {
        if (!ops_file.exists()) {
            save("ops.yml",false);
        }
        YamlConfiguration.loadConfiguration(ops_file);
        ops.load(ops_file);
    }
    public FileConfiguration getOps() {
        return ops;
    }
    private static File group_file = new File(folder+"Groups.yml");
    private static FileConfiguration group = new YamlConfiguration();
    public static void onGroup() throws IOException, InvalidConfigurationException {
        File file = group_file;
        File files = new File(folder+"Groups-Error-Arching.yml");
        if (!group_file.exists()) {
            save("Groups.yml",false);
        }
        YamlConfiguration.loadConfiguration(group_file);
        try {
            group.load(group_file);
        } catch (IOException e) {
            e.printStackTrace();
            if (files.exists()) {
                files.deleteOnExit();
            }
            file.renameTo(files);
            file.deleteOnExit();
            onGroup();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            if (files.exists()) {
                files.deleteOnExit();
            }
            file.renameTo(files);
            file.deleteOnExit();
            onGroup();
        }
    }
    public static FileConfiguration getGroup() {
        return group;
    }
    private static File banned_file = new File(folder+"Banned.yml");
    private static FileConfiguration banned = new YamlConfiguration();
    public static void onBanned() throws IOException, InvalidConfigurationException {
        if (!banned_file.exists()) {
            save("Banned.yml",false);
        }
        YamlConfiguration.loadConfiguration(banned_file);
        banned.load(banned_file);
    }
    public static FileConfiguration getBanned() {
        return banned;
    }
    private static File help_file = new File(folder+"Help.yml");
    private static FileConfiguration help = new YamlConfiguration();
    public static void onHelp() throws IOException, InvalidConfigurationException {
        if (!help_file.exists()) {
            save("Help.yml",false);
        }
        YamlConfiguration.loadConfiguration(help_file);
        help.load(help_file);
    }
    public FileConfiguration getHelp() {
        return help;
    }
    private static File report_file = new File(folder+"Report.yml");
    private static FileConfiguration report = new YamlConfiguration();
    public static void onReport() throws IOException, InvalidConfigurationException {
        if (!report_file.exists()) {
            save("Report.yml",false);
        }
        YamlConfiguration.loadConfiguration(report_file);
        report.load(report_file);
    }
    public static FileConfiguration getReport() {
        return report;
    }
    private static File settings_file = new File(folder+"Settings.yml");
    private static FileConfiguration settings = new YamlConfiguration();
    public static void onSettings() {
        File file = settings_file;
        File files = new File(folder+"Settings-Error-Arching.yml");
        if (!settings_file.exists()) {
            save("Settings.yml",false);
        }
        YamlConfiguration.loadConfiguration(settings_file);
        try {
            settings.load(settings_file);
        } catch (IOException e) {
            e.printStackTrace();
            if (files.exists()) {
                files.deleteOnExit();
            }
            file.renameTo(files);
            file.deleteOnExit();
            onSettings();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            if (files.exists()) {
                files.deleteOnExit();
            }
            file.renameTo(files);
            file.deleteOnExit();
            onSettings();
        }
    }
    public static FileConfiguration getSettings() {
        return settings;
    }

}
