package org.functions.Bukkit.Main;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Download implements Runnable {
    String url = "https://";
    String github = "github.com/";
    String my = url + "2b2ttanxiu.github.io/";
    String profile = url + github + "2b2ttanxiu/";
    String version = null;
    String msg = null;
    public static String getVersion() {
        URL url = null;
        String version = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            url = new URL("https://2b2ttanxiu.github.io/Version");
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            version = br.readLine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return version;
    }
    public void run() {
        new Download();
    }
    public Download() {
        URL url = null;
        try {
            url = new URL(my + "Info");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        InputStream is = null;
        try {
            is = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        try {
            msg = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            url = new URL(my+"Version");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        is = null;
        try {
            is = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        try {
            version = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (version.equals(Functions.instance.getDescription().getVersion())) {
            return;
        }
        if (Functions.instance.getSettings().getBoolean("check-update.Download")) {
            File temp = new File(Functions.instance.getDataFolder(),"Releases");
            if (!temp.exists()) {
                temp.mkdir();
            }
            File jar = new File(temp, "Functions.jar");
            if (jar.exists()) {
                return;
            }
            try {
                Functions.instance.getAPI().sendConsole("Downloading...");
                URL download = new URL(profile + "Functions/releases/download/" + version + "/Functions.jar");
                InputStream in = null;
                in = download.openStream();
                Files.copy(in, jar.toPath(), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                in.close();
                Functions.instance.getAPI().sendConsole("Download successfully!");
            } catch (IOException var4) {
                var4.printStackTrace();
                Functions.instance.getAPI().sendConsole("Download not successfully! Wating for " + Functions.instance.getSettings().getInt("check-update.minutes") + " minutes download one.");
                Functions.instance.getAPI().sendConsole("If you in the " + Functions.instance.getSettings().getInt("check-update.minutes") + " try again");
                Functions.instance.getAPI().sendConsole("URL: " + profile + "Functions/releases/download/" + version + "/Functions.jar");
            }
            Functions.instance.getAPI().sendConsole(msg);
        } else {
            Functions.instance.getAPI().sendConsole(msg);
        }
    }
}
